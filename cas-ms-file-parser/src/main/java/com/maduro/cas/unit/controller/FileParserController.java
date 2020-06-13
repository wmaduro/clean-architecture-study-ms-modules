package com.maduro.cas.unit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maduro.cas.unit.dto.FileParserDTO;
import com.maduro.cas.unit.dto.StorageDTO;
import com.maduro.cas.unit.service.FileParserService;

@RestController
@RequestMapping(path = "/file-parser")
public class FileParserController {

	@Autowired
	private FileParserService fileParserService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public FileParserDTO processFile(@RequestBody StorageDTO storageDTO) throws Exception {
		return fileParserService.processStorage(storageDTO);
	}

}
