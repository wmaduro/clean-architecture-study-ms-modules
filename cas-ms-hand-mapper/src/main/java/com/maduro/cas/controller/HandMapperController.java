package com.maduro.cas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maduro.cas.dto.FileParserDTO;
import com.maduro.cas.dto.HandMapperDTO;
import com.maduro.cas.service.HandMapperService;

@RestController
@RequestMapping(path = "/hand-mapper")
public class HandMapperController {

	@Autowired
	private HandMapperService handMapperService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public HandMapperDTO process(@RequestBody FileParserDTO fileParserDTO) throws Exception {
		return handMapperService.process(fileParserDTO);
	}

}
