package com.maduro.cas.unit.orchestration.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.maduro.cas.core.network.FileParseNetwork;
import com.maduro.cas.core.network.HandEvaluatorNetwork;
import com.maduro.cas.core.network.HandMapperNetwork;
import com.maduro.cas.core.network.StorageNetwork;
import com.maduro.cas.unit.orchestration.domain.Orchestration;
import com.maduro.cas.unit.orchestration.dto.FileParserDTO;
import com.maduro.cas.unit.orchestration.dto.HandEvaluatorDTO;
import com.maduro.cas.unit.orchestration.dto.HandMapperDTO;
import com.maduro.cas.unit.orchestration.dto.OrchestrationDTO;
import com.maduro.cas.unit.orchestration.dto.StorageDTO;
import com.maduro.cas.unit.orchestration.repository.OrchestrationRepository;
import com.maduro.cas.unit.orchestration.service.exception.InsertingOrchestrationException;
import com.maduro.cas.unit.orchestration.service.exception.InvalidParameterException;

class OrchestrationServiceTest {

	private final MultipartFile file = getMultipartFile();
	private final Long idStorageReference = 1L;
	private final Orchestration orchestration = new Orchestration(null, idStorageReference.toString(), null);
	private final Orchestration orchestrationAfterSave = new Orchestration(1L, idStorageReference.toString(), null);
	private final Orchestration orchestrationAfterResult = new Orchestration(1L, idStorageReference.toString(), "");
	private final FileParserDTO fileParserDTO = new FileParserDTO();
	private final HandMapperDTO handMapperDTO = new HandMapperDTO();
	private final HandEvaluatorDTO handEvaluatorDTO = new HandEvaluatorDTO();

	private final StorageNetwork storageNetwork = mock(StorageNetwork.class);
	private final OrchestrationRepository orchestratorRepository = mock(OrchestrationRepository.class);
	private final FileParseNetwork fileParseNetwork = mock(FileParseNetwork.class);
	private final HandMapperNetwork handMapperNetwork = mock(HandMapperNetwork.class);
	private final HandEvaluatorNetwork handEvaluatorNetwork = Mockito.mock(HandEvaluatorNetwork.class);

	@BeforeEach
	private void setMockBaseScenario() throws IOException {
		when(storageNetwork.saveStorage(file.getBytes())).thenReturn(idStorageReference);

		when(orchestratorRepository.save(orchestration)).thenReturn(orchestrationAfterSave);
		when(orchestratorRepository.save(orchestrationAfterSave)).thenReturn(orchestrationAfterResult);

		when(fileParseNetwork.processFileParser(new StorageDTO(idStorageReference.toString())))
				.thenReturn(fileParserDTO);

		when(handMapperNetwork.processHandMapper(fileParserDTO)).thenReturn(handMapperDTO);

		when(handEvaluatorNetwork.processHandEvaluator(handMapperDTO)).thenReturn(handEvaluatorDTO);
	}

	@Test
	void must_ReturnSuccessfuly_WhenProcesssFile() throws IOException {

		OrchestrationDTO orchestrationDTO = new OrchestrationService(orchestratorRepository, storageNetwork,
				fileParseNetwork, handMapperNetwork, handEvaluatorNetwork).processFile(file);

		assertTrue(orchestrationDTO.getId().contentEquals(idStorageReference.toString()));

	}

	@Test
	void must_RaiseInvalidParameterException_WhenProcesssFile() {

		assertThrows(InvalidParameterException.class, () -> {
			new OrchestrationService(orchestratorRepository, storageNetwork, fileParseNetwork, handMapperNetwork,
					handEvaluatorNetwork).processFile(null);
		});
	}

	@Test
	void must_RaiseInsertingOrchestrationException_WhenProcesssFile() throws IOException {

		when(orchestratorRepository.save(orchestration)).thenReturn(null);

		assertThrows(InsertingOrchestrationException.class, () -> {
			new OrchestrationService(orchestratorRepository, storageNetwork, fileParseNetwork, handMapperNetwork,
					handEvaluatorNetwork).processFile(file);
		});

	}

	private MultipartFile getMultipartFile() {

		final String name = "file.txt";
		final String originalFileName = "file.txt";
		final String contentType = "text/plain";
		final byte[] content = "".getBytes();
		return new MockMultipartFile(name, originalFileName, contentType, content);

	}

}
