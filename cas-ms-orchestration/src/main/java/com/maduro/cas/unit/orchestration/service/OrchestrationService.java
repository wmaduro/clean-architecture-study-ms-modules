package com.maduro.cas.unit.orchestration.service;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maduro.cas.unit.orchestration.domain.Orchestration;
import com.maduro.cas.unit.orchestration.repository.OrchestrationRepository;

@Service
public class OrchestrationService {

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

	public List<String> getFileList() {
		 
		 return orchestratorRepository
		 	.findAll()
		 	.stream()
		 	.map(orchestration -> orchestration.getFilePath())
		 	.collect(Collectors.toList());
		
	}

}
