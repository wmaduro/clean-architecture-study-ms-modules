package com.maduro.cas.service;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.maduro.cas.domain.CriticalHandOutcomeEnum;
import com.maduro.cas.domain.HandDataModel;
import com.maduro.cas.dto.HandEvaluatorDTO;
import com.maduro.cas.dto.HandMapperDTO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HandEvaluatorServiceIT {
	private static final String ACTION_PRE_FLOP = "PRE_FLOP";
	@LocalServerPort
	private int port;

	@Test
	public void must_ProcessFileParserDTO_Successfuly() throws Exception {

		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured
			.given()
				.port(port)
				.basePath("/hand-evaluator")
				.accept(ContentType.JSON)
				.contentType("application/json")
				.body(prepareTestData())
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("gameCrititalHandDataModelList", Matchers.hasSize(1));

	}

	private HandMapperDTO prepareTestData() {

		HandMapperDTO handMapperDTO = new HandMapperDTO();

		HandDataModel handDataModelWmaduro = HandDataModel.builder().game("1").hand("1").user_name("wmaduro")
				.card_sequence("AcAs").value_won("100").all_in_action_street(ACTION_PRE_FLOP).build();
		HandDataModel handDataModelOpponent = HandDataModel.builder().game("1").hand("1").user_name("wmaduro")
				.card_sequence("7c2s").value_won("0").all_in_action_street(ACTION_PRE_FLOP).build();

		handMapperDTO.mapHandDataModel(handDataModelWmaduro);
		handMapperDTO.mapHandDataModel(handDataModelOpponent);

		return handMapperDTO;

	}

	private boolean validateOutcome(HandEvaluatorDTO handEvaluatorDTO) {
		return handEvaluatorDTO.getGameCrititalHandDataModelList().size() == 1 && handEvaluatorDTO.getGameCrititalHandDataModelList().get(0).getCriticalHandOutcomeEnum() == CriticalHandOutcomeEnum.BEST_WIN;
	}

}
