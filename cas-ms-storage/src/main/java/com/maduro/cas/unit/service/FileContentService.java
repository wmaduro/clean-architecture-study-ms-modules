package com.maduro.cas.unit.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maduro.cas.unit.domain.FileContent;
import com.maduro.cas.unit.repository.FileContentRepository;

@Service
public class FileContentService {

	@Autowired
	private FileContentRepository fileContentRepository;

	public Long save(byte[] fileContent) {

		try {
			return fileContentRepository.save(new FileContent(null, fileContent)).getId();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<FileContent> findById(Long id) {
		return fileContentRepository.findById(id);
	}

}
