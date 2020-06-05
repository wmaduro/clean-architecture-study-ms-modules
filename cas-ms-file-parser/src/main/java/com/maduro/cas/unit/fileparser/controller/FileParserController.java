package com.maduro.cas.unit.fileparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maduro.cas.unit.fileparser.dto.FileDTO;
import com.maduro.cas.unit.fileparser.service.FileParserService;

@RestController
@RequestMapping(path = "/file-parser")
public class FileParserController {

	@Autowired
	private FileParserService fileParserService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public String processFile(@RequestBody FileDTO fileDTO) {
		String s = fileDTO == null ? "dto null" : fileDTO.getUriFile();
		System.out.println("teste..." + s);
		return "teste....." + s;// fileParserService.processFile(fileDTO);
	}

}
