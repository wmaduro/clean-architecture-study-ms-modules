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
import com.maduro.cas.unit.service.StorageService;

@RestController
@RequestMapping(path = "/storage")
public class StorageController {

	@Autowired
	private StorageService storageService;

	@PostMapping(consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE )
	public Long saveContent(@RequestBody byte[] content) {
		return storageService.save(content);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Optional<FileContent> oFileContent = storageService.findById(id);
		if (!oFileContent.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(oFileContent.get().getContent());
	}

}
