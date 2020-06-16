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
	private final String handReference = "1";
	
	@Test
	void must_Return200_WhenProcessFileParserDTO() throws Exception {
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured
			.given()
				.port(port)
				.basePath("/hand-mapper")
				.accept(ContentType.JSON)
				.contentType("application/json")
				.body(prepareData())
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("handDataModelMap", Matchers.hasKey(handReference));
		
	}
	
	private FileParserDTO prepareData() {

		List<HandDataModel> handDataModels = List.of(HandDataModel.builder().game("1").hand(handReference).build(),
				HandDataModel.builder().game("1").hand(handReference).build()); 
		
		FileParserDTO fileParserDTO = new FileParserDTO();
		handDataModels.forEach(handDataModel -> {
			fileParserDTO.addHandDataModel(handDataModel);	
		});
	
		return fileParserDTO;
	}
}
