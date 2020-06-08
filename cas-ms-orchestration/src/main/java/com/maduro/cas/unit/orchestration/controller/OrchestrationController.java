package com.maduro.cas.unit.orchestration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maduro.cas.unit.orchestration.dto.OrchestrationDTO;
import com.maduro.cas.unit.orchestration.service.OrchestrationService;

@RestController
@RequestMapping(path = "/orchestration")
public class OrchestrationController {

	@Autowired
	private OrchestrationService orchestratorService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public OrchestrationDTO processFoto(MultipartFile file) {
		return orchestratorService.processFile(file);
	}


}
