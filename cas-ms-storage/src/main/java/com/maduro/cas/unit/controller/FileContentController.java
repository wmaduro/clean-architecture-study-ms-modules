package com.maduro.cas.unit.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maduro.cas.unit.domain.FileContent;
import com.maduro.cas.unit.service.FileContentService;

@RestController
@RequestMapping(path = "/file-content")
public class FileContentController {

	@Autowired
	private FileContentService fileContentService;

	@PostMapping(consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE )
	public Long saveContent(@RequestBody byte[] content) {
		return fileContentService.save(content);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Optional<FileContent> oFileContent = fileContentService.findById(id);
		if (!oFileContent.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(oFileContent.get().getContent());
	}

}
