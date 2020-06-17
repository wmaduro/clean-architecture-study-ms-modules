package com.maduro.cas.unit.service;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.maduro.cas.unit.dto.StorageDTO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileParserServiceIT {

	@LocalServerPort
	private int port;
	private ClientAndServer clientAndServerStorage;
	final String idSimulated = "1";
	
	@BeforeEach
	void postConstruct() {
		startStorageApiMock();
	}

	@AfterEach
	void stopServer() {
		if (clientAndServerStorage != null) {
			clientAndServerStorage.stop();
		}
	}

	void startStorageApiMock() {

		final String STORAGE_PATH = "/storage/";
		
		final String data = "\"game\",\"hand\",\"hand_position\",\"user_name\",\"card_sequence\",\"value_won\",\"board\",\"all_in_action_street\",\"action_pre_flop\",\"value_action_pre_flop\",\"action_flop\",\"value_action_flop\",\"action_turn\",\"value_action_turn\",\"action_river\",\"value_action_river\",\"bb\",\"street_ended\",\"show_down\",\"level\"\n"
				+ "\"2655675429   \",\"202713458879 \",2,zloipitbull2212,\"9c 9s\",,,PRE_FLOP,CALL,4660,,,,,,,61,RIVER,true,5\n"
				+ "\"2655675429   \",\"202713458879 \",2,wmaduro,Ts Tc,10170,,PRE_FLOP,RAISE,4900,,,,,,,61,RIVER,true,5\n";

		clientAndServerStorage = ClientAndServer.startClientAndServer(20005);
		clientAndServerStorage
			.when(request().withMethod("GET").withPath(STORAGE_PATH + idSimulated))
			.respond(response()
						.withStatusCode(200)
						.withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
						.withBody(data.getBytes()));
	}

	@Test
	void must_Return200_WhenProcessStorageDTO() throws Exception {

		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		RestAssured
			.given()
				.port(port)
				.basePath("/file-parser")
				.accept(ContentType.JSON)
				.contentType("application/json")
				.body(new StorageDTO(idSimulated))
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("handDataModelList", Matchers.hasSize(2));
	
	}

}
