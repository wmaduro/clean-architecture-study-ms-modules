package com.maduro.cas.unit.fileparser.service.network.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Setter;
import reactor.core.publisher.Mono;

public abstract class BaseRequest {

	@Value(value = "${cas-ms.protocol}")
	private String protocol="http";
	@Value(value = "${cas-ms.host}")
	private String host="localhost";
	@Setter
	private String port;
	
	protected Function<ClientResponse, Mono<? extends Throwable>> functionHttpStausIsError;
	protected Consumer<? super Throwable> onError;

	protected <T> Optional<T> sendBlockRequest(Class<T> typeReturnObject, 
			Object content, 
			String path, 
			HttpMethod httpMethod) {
	
		if (content == null) {
			return Optional.empty();
		}
	
		try {
			T return_ = 
				WebClient
					.builder()
					.baseUrl(new URL(protocol, host, Integer.parseInt(port), "").toString())
					.build()
					.method(httpMethod)
					.uri(path)
					.body(BodyInserters.fromValue(content))
					.retrieve()
					.onStatus(HttpStatus::isError, functionHttpStausIsError)					
					.bodyToMono(typeReturnObject)
					.doOnError(onError)
					.block();
			
			if (return_ == null) {
				return Optional.empty();
			}
			return Optional.of(return_);
			
		} catch (NumberFormatException e) {
			throw new RuntimeException("Invalid port value: "+port);
		} catch (MalformedURLException e) {
			throw new RuntimeException("Invalid URL: " + protocol + "," + host + "," + port);
		} 
	}
	

}
