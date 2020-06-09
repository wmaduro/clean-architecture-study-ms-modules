package com.maduro.cas.unit.orchestration.network;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Setter;

public abstract class BaseRequest {

	@Value(value = "${cas-ms.protocol}")
	private String protocol="http";
	@Value(value = "${cas-ms.host}")
	private String host="localhost";
//	@Value(value = "${cas-ms.service.storage.port}")
	@Setter
	private String port;
	
	protected Optional<Object> sendBlockRequest(Object content, String path, HttpMethod httpMethod) throws NumberFormatException, MalformedURLException {
	
		if (content == null) {
			return Optional.empty();
		}
		
		return Optional.of(WebClient.builder()
				.baseUrl(new URL(protocol, host, Integer.parseInt(port), "").toString())
				.build()
				.method(httpMethod)
				.uri(path)
				.body(BodyInserters.fromValue(content))
				.retrieve()
				.bodyToMono(Object.class)
				.block());
	}
}
