package com.maduro.cas.unit.orchestration.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maduro.cas.core.network.FileParseNetwork;
import com.maduro.cas.core.network.HandEvaluatorNetwork;
import com.maduro.cas.core.network.HandMapperNetwork;
import com.maduro.cas.core.network.StorageNetwork;
import com.maduro.cas.unit.orchestration.domain.Orchestration;
import com.maduro.cas.unit.orchestration.dto.FileParserDTO;
import com.maduro.cas.unit.orchestration.dto.HandEvaluatorDTO;
import com.maduro.cas.unit.orchestration.dto.HandMapperDTO;
import com.maduro.cas.unit.orchestration.dto.OrchestrationDTO;
import com.maduro.cas.unit.orchestration.dto.StorageDTO;
import com.maduro.cas.unit.orchestration.repository.OrchestrationRepository;
import com.maduro.cas.unit.orchestration.service.exception.ImportFileInvalidException;
import com.maduro.cas.unit.orchestration.service.exception.InsertingOrchestrationException;
import com.maduro.cas.unit.orchestration.service.exception.InvalidParameterException;

@Service
public class OrchestrationService {

	private OrchestrationRepository orchestratorRepository;
	private StorageNetwork storageNetwork;
	private FileParseNetwork fileParseNetwork;
	private HandMapperNetwork handMapperNetwork;
	private HandEvaluatorNetwork handEvaluatorNetwork;

	public OrchestrationService(OrchestrationRepository orchestratorRepository, StorageNetwork storageNetwork,
			FileParseNetwork fileParseNetwork, HandMapperNetwork handMapperNetwork,
			HandEvaluatorNetwork handEvaluatorNetwork) {
		
		this.orchestratorRepository = orchestratorRepository;
		this.storageNetwork = storageNetwork;
		this.fileParseNetwork = fileParseNetwork;
		this.handMapperNetwork = handMapperNetwork;
		this.handEvaluatorNetwork = handEvaluatorNetwork;
	
	}

	public OrchestrationDTO processFile(final MultipartFile file) {

		if (file == null) {
			throw new InvalidParameterException();
		}

		Long idStorageReference;
		try {
			idStorageReference = storageNetwork.saveStorage(file.getBytes());
		} catch (IOException e) {
			throw new ImportFileInvalidException();
		}

		Orchestration orchestration = orchestratorRepository
				.save(new Orchestration(null, idStorageReference.toString(), null));
		if (orchestration == null) {
			throw new InsertingOrchestrationException();
		}

		FileParserDTO fileParserDTO = fileParseNetwork.processFileParser(new StorageDTO(idStorageReference.toString()));

		HandMapperDTO handMapperDTO = handMapperNetwork.processHandMapper(fileParserDTO);

		HandEvaluatorDTO handEvaluatorDTO = handEvaluatorNetwork.processHandEvaluator(handMapperDTO);

		saveResult(orchestration, handEvaluatorDTO);

		return OrchestrationDTO.builder().id(orchestration.getId().toString()).build();

	}

	private void saveResult(Orchestration orchestration, HandEvaluatorDTO handEvaluatorDTO) {

		if (orchestration == null || handEvaluatorDTO == null
				|| handEvaluatorDTO.getGameCrititalHandDataModelList().isEmpty()) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		handEvaluatorDTO.getGameCrititalHandDataModelList().stream().forEach(gameCrititalHandDataModel -> {
			String data = gameCrititalHandDataModel.getGameCode() + " | "
					+ gameCrititalHandDataModel.getCriticalHandOutcomeEnum();
			data += ", ";
			sb.append(data);
		});

		orchestration.setResult(sb.toString());
		orchestratorRepository.save(orchestration);
	}

}
