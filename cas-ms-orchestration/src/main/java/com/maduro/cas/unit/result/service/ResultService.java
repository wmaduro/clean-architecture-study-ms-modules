package com.maduro.cas.unit.result.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maduro.cas.unit.orchestration.domain.Orchestration;
import com.maduro.cas.unit.orchestration.repository.OrchestrationRepository;
import com.maduro.cas.unit.result.dto.ResultDTO;

@Service
public class ResultService {
	
	@Autowired
	private OrchestrationRepository orchestratorRepository;

	public ResultDTO resultById(Long id) {
		
		Orchestration orchestration = orchestratorRepository.getOne(id);
		
		return ResultDTO
			.builder()
			.gameCode(orchestration.getId().toString())
			.result(orchestration.getResult()).build();
		
	}

}
