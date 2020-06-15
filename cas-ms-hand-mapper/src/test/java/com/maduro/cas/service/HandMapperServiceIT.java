package com.maduro.cas.service;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.maduro.cas.domain.HandDataModel;
import com.maduro.cas.dto.FileParserDTO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HandMapperServiceIT {

	@LocalServerPort
	private int port;
	
	@BeforeEach
	void setUp() throws Exception {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		RestAssured.port = port;
		RestAssured.basePath ="/hand-mapper";
	}
	
	@Test
	void must_ContainValid_WhenProcessFileParser() throws Exception {
		
		List<HandDataModel> handDataModels = List.of(HandDataModel.builder().game("1").hand("1").build(),
				HandDataModel.builder().game("1").hand("1").build()); 
		
		FileParserDTO fileParserDTO = new FileParserDTO();
		handDataModels.forEach(handDataModel -> {
			fileParserDTO.addHandDataModel(handDataModel);	
		});
		
		RestAssured
			.given()
				.accept(ContentType.JSON)
				.contentType("application/json")
				.body(fileParserDTO)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("handDataModelMap", Matchers.hasKey("1"));
		
	}
	
}
