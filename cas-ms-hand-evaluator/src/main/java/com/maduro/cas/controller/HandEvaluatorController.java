package com.maduro.cas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maduro.cas.dto.HandEvaluatorDTO;
import com.maduro.cas.dto.HandMapperDTO;
import com.maduro.cas.service.HandEvaluatorService;

@RestController
@RequestMapping(path = "/hand-evaluator")
public class HandEvaluatorController {

	@Autowired
	private HandEvaluatorService handEvaluatorService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public HandEvaluatorDTO process(@RequestBody HandMapperDTO handMapperDTO) throws Exception {
		return handEvaluatorService.process(handMapperDTO);
	}

}
