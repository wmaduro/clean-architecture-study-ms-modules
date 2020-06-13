package com.maduro.cas.unit.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maduro.cas.unit.domain.FileContent;
import com.maduro.cas.unit.repository.FileContentRepository;
import com.maduro.cas.unit.service.exception.DatabaseNotAvailableException;

@Service
public class StorageService {

	@Autowired
	private FileContentRepository fileContentRepository;

	public Long save(byte[] fileContent) {

		try {
			return fileContentRepository.save(new FileContent(null, fileContent)).getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseNotAvailableException();
		}

	}

	public Optional<FileContent> findById(Long id) {
		
		try {
			return fileContentRepository.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseNotAvailableException();
		}
	}

}
