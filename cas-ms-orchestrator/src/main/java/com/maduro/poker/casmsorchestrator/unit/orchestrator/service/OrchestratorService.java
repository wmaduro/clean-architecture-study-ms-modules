package com.maduro.poker.casmsorchestrator.unit.orchestrator.service;

import java.nio.file.Path;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maduro.poker.casmsorchestrator.unit.orchestrator.domain.Orchestration;
import com.maduro.poker.casmsorchestrator.unit.orchestrator.repository.OrchestrationRepository;

@Service
public class OrchestratorService {

	@Autowired
	private OrchestrationRepository orchestratorRepository;

	public void processFile(MultipartFile file) {

		try {

			var fileName = UUID.randomUUID().toString() + "_" + file.getName();
			Path pathFile = Path.of("/home/maduro/lixo/lixo", fileName);

			// save file
			file.transferTo(pathFile);

			// save db
			orchestratorRepository.save(new Orchestration(null, pathFile.toFile().getAbsolutePath()));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
