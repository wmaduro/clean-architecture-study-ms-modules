package com.maduro.cas.unit.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.maduro.cas.unit.domain.FileContent;
import com.maduro.cas.unit.repository.FileContentRepository;


class StorageServiceTest {

	private final FileContentRepository fileContentRepository = mock(FileContentRepository.class);
	final byte[] data = "data".getBytes();
	final Long idReference = 1L;
	
	public StorageServiceTest() {
		when(fileContentRepository
				.save(FileContent.builder().content(data).build()))
				.thenReturn(FileContent.builder().id(idReference).build());
		when(fileContentRepository
					.findById(idReference))
					.thenReturn(Optional.of(FileContent.builder().id(idReference).build()));
	}
	


	@Test
	void must_ReturnId_WhenSaveFileContent() {
		Long id = new StorageService(fileContentRepository).save(data);
		assertTrue(id == idReference);
	}

	@Test
	void must_ReturnFileContent_WhenFindById() {
		Optional<FileContent> oFileContent = new StorageService(fileContentRepository).findById(idReference);
		assertTrue(oFileContent.isPresent() && oFileContent.get().getId() == idReference);
	}

}
