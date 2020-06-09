package com.maduro.cas.unit.orchestration.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maduro.cas.unit.orchestration.domain.Orchestration;
import com.maduro.cas.unit.orchestration.dto.FileParserDTO;
import com.maduro.cas.unit.orchestration.dto.HandEvaluatorDTO;
import com.maduro.cas.unit.orchestration.dto.HandMapperDTO;
import com.maduro.cas.unit.orchestration.dto.OrchestrationDTO;
import com.maduro.cas.unit.orchestration.dto.StorageDTO;
import com.maduro.cas.unit.orchestration.network.FileParseRequest;
import com.maduro.cas.unit.orchestration.network.HandEvaluatorRequest;
import com.maduro.cas.unit.orchestration.network.HandMapperRequest;
import com.maduro.cas.unit.orchestration.network.StorageRequest;
import com.maduro.cas.unit.orchestration.repository.OrchestrationRepository;

@Service
public class OrchestrationService {

	@Autowired
	private OrchestrationRepository orchestratorRepository;

//	@Autowired
//	private RequestHelper requestHelper;
	
	@Autowired
	private StorageRequest storageRequest;
	
	@Autowired
	private FileParseRequest fileParseRequest;
	
	@Autowired
	private HandMapperRequest handMapperRequest;
	
	@Autowired
	private HandEvaluatorRequest handEvaluatorRequest;

	public OrchestrationDTO processFile(MultipartFile file) throws IOException {
		
		Long idStorageReference = storageRequest.saveStorage(file.getBytes());
		
		Orchestration orchestration = orchestratorRepository
				.save(new Orchestration(null, idStorageReference.toString(), null));

		FileParserDTO fileParserDTO = fileParseRequest.processFileParser(new StorageDTO(idStorageReference.toString()));

		HandMapperDTO handMapperDTO = handMapperRequest.processHandMapper(fileParserDTO);

		HandEvaluatorDTO handEvaluatorDTO = handEvaluatorRequest.processHandEvaluator(handMapperDTO);

		saveResult(orchestration, handEvaluatorDTO);

		return OrchestrationDTO.builder().id(orchestration.getId().toString()).build();

	}

	private void saveResult(Orchestration orchestration, HandEvaluatorDTO handEvaluatorDTO) {

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
