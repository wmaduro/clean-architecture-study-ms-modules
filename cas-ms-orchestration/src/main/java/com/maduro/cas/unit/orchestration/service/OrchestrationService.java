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
import com.maduro.cas.unit.orchestration.repository.OrchestrationRepository;
import com.maduro.cas.unit.orchestration.service.network.RequestHelper;

@Service
public class OrchestrationService {
	
	@Autowired
	private OrchestrationRepository orchestratorRepository;

	@Autowired
	private RequestHelper requestHelper;
	
	public OrchestrationDTO processFile(MultipartFile file) throws IOException {

							
			Long idStorageReference = requestHelper.saveStorage(file.getBytes());
			
			Orchestration orchestration = orchestratorRepository.save(new Orchestration(null,idStorageReference.toString(), null));
			
			FileParserDTO fileParserDTO = requestHelper.processFileParser(idStorageReference.toString());
			
			HandMapperDTO handMapperDTO = requestHelper.processHandMapper(fileParserDTO);
			
			HandEvaluatorDTO handEvaluatorDTO = requestHelper.processHandEvaluator(handMapperDTO);
			
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
