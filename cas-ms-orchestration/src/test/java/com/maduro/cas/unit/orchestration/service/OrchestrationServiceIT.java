package com.maduro.cas.unit.orchestration.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maduro.cas.unit.orchestration.domain.Orchestration;
import com.maduro.cas.unit.orchestration.dto.FileParserDTO;
import com.maduro.cas.unit.orchestration.dto.HandEvaluatorDTO;
import com.maduro.cas.unit.orchestration.dto.HandMapperDTO;
import com.maduro.cas.unit.orchestration.dto.StorageDTO;
import com.maduro.cas.unit.orchestration.repository.OrchestrationRepository;

import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrchestrationServiceIT {

	@LocalServerPort
	private int port;

	private final String idStorageReference = "1";

	private ClientAndServer clientAndServerStorage;
	private ClientAndServer clientAndServerFileParser;
	private ClientAndServer clientAndServerHandMapper;
	private ClientAndServer clientAndServerHandEvaluator;

	private final OrchestrationRepository orchestratorRepository = mock(OrchestrationRepository.class);
	private final Orchestration orchestration = new Orchestration(null, idStorageReference.toString(), null);
	private final Orchestration orchestrationAfterSave = new Orchestration(1L, idStorageReference.toString(), null);
	private final Orchestration orchestrationAfterResult = new Orchestration(1L, idStorageReference.toString(),
			"result");

	@BeforeEach
	void beforeEach() throws JsonProcessingException {
		
		startOrchestrationRepository();
		
		String storageDTOJson = new ObjectMapper().writeValueAsString(new StorageDTO(idStorageReference));
		String fileParserDTOJson = new ObjectMapper().writeValueAsString(new FileParserDTO());
		String handMapperDTOJson = new ObjectMapper().writeValueAsString(new HandMapperDTO());
		String handEvaluatorDTOJson = new ObjectMapper().writeValueAsString(new HandEvaluatorDTO());
		
		clientAndServerStorage = startClientAndServerApiMock(20005, "POST", "/storage", "test-content".getBytes(), 200, idStorageReference.toString());
		clientAndServerFileParser = startClientAndServerApiMock(20004, "POST", "/file-parser", storageDTOJson.getBytes(), 200, fileParserDTOJson);
		clientAndServerHandMapper = startClientAndServerApiMock(20006, "POST", "/hand-mapper", fileParserDTOJson.getBytes(), 200, handMapperDTOJson.toString());
		clientAndServerHandEvaluator = startClientAndServerApiMock(20007, "POST", "/hand-evaluator", handMapperDTOJson.getBytes(), 200, handEvaluatorDTOJson.toString());
				
	}
	
	@AfterEach
	void afterEach() {
		clientAndServerStorage.stop();
		clientAndServerFileParser.stop();
		clientAndServerHandMapper.stop();
		clientAndServerHandEvaluator.stop();
	}

	@Test
	void must_Return503_WhenStorageIsNotAvailable() throws Exception {

		clientAndServerStorage.stop();
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.given().port(port).basePath("/orchestration").contentType("multipart/form-data")
				.multiPart(getMultiPartSpecification()).when().post().then().statusCode(HttpStatus.SERVICE_UNAVAILABLE.value());

	}

	@Test
	void must_Return503_WhenFileParserIsNotAvailable() throws Exception {

		clientAndServerFileParser.stop();
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.given().port(port).basePath("/orchestration").contentType("multipart/form-data")
				.multiPart(getMultiPartSpecification()).when().post().then().statusCode(HttpStatus.SERVICE_UNAVAILABLE.value());

	}
	
	@Test
	void must_Return503_WhenHandMapperIsNotAvailable() throws Exception {

		clientAndServerHandMapper.stop();
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.given().port(port).basePath("/orchestration").contentType("multipart/form-data")
				.multiPart(getMultiPartSpecification()).when().post().then().statusCode(HttpStatus.SERVICE_UNAVAILABLE.value());

	}

	
	@Test
	void must_Return503_WhenHandEvaluatorIsNotAvailable() throws Exception {

		clientAndServerHandEvaluator.stop();
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.given().port(port).basePath("/orchestration").contentType("multipart/form-data")
				.multiPart(getMultiPartSpecification()).when().post().then().statusCode(HttpStatus.SERVICE_UNAVAILABLE.value());

	}
	
	
	@Test
	void must_Return200_WhenProcessFile() throws Exception {

		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.given().port(port).basePath("/orchestration").contentType("multipart/form-data")
				.multiPart(getMultiPartSpecification()).when().post().then().statusCode(HttpStatus.OK.value());

	}

	private MultiPartSpecification getMultiPartSpecification() {
		return new MultiPartSpecBuilder("test-content".getBytes()).fileName("test.txt").controlName("file")
				.mimeType("text/plain").build();
	}

	
	void startOrchestrationRepository() {
		when(orchestratorRepository.save(orchestration)).thenReturn(orchestrationAfterSave);
		when(orchestratorRepository.save(orchestrationAfterSave)).thenReturn(orchestrationAfterResult);
	}
	
	ClientAndServer startClientAndServerApiMock(int port, String method, String path, byte[] body, int responseStatusCode, String response) {
		ClientAndServer clientAndServer = ClientAndServer.startClientAndServer(port);
		
		clientAndServer
			.when(request()
					.withMethod(method)
					.withPath(path)
					.withBody(body))
			.respond(response()
					.withStatusCode(responseStatusCode)
					.withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
					.withBody(response));
		
		return clientAndServer;
	}

}
