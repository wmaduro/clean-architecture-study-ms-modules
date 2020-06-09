package com.maduro.cas.unit.orchestration.service.lixo;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.maduro.cas.unit.orchestration.network.BaseRequest;

public class RequestHelper2<I, O>  {

//	@Value(value = "${cas-ms.protocol}")
	private String protocol="http";
//	@Value(value = "${cas-ms.host}")
	private String host="localhost";
//	@Value(value = "${cas-ms.service.storage.port}")
	private String storagePort= "20005";
//	@Value(value = "${cas-ms.service.hand-evaluator.port}")
//	private String handEvaluatorPort;
//	@Value(value = "${cas-ms.service.hand-mapper.port}")
//	private String handMapperPort;
//	@Value(value = "${cas-ms.service.file-parser.port}")
//	private String fileparserPort;

	public O saveStorage(I content) throws MalformedURLException {

		return (O) WebClient.builder()
				.baseUrl(new URL(protocol, host, Integer.parseInt(storagePort), "").toString())
				.build()
				.method(HttpMethod.POST)
				.uri("/file-content")
				.body(BodyInserters.fromValue(content))
				.retrieve()
				.bodyToMono(Object.class)
				.block();

	}

}
