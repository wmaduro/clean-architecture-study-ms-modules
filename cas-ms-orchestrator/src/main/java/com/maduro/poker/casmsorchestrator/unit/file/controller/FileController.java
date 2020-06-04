package com.maduro.poker.casmsorchestrator.unit.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maduro.poker.casmsorchestrator.unit.orchestrator.service.OrchestratorService;

@RestController
@RequestMapping(path = "/file")
public class FileController {

	@Autowired
	private OrchestratorService orchestratorService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void processFoto(MultipartFile file) {
		orchestratorService.processFile(file);
	}

}
