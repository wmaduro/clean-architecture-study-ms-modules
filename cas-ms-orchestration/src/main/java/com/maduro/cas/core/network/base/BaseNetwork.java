package com.maduro.cas.core.network.base;

import java.net.ConnectException;
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

import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.core.exception.external.ExternalServiceHttpException;
import com.maduro.cas.core.exception.external.ExternalServiceUnavailableException;
import com.maduro.cas.core.exception.internal.PortValueInvalidException;
import com.maduro.cas.core.exception.internal.UrlParseException;

import lombok.Setter;
import reactor.core.publisher.Mono;

public abstract class BaseNetwork {

	@Value(value = "${cas-ms.protocol}")
	private String protocol="http";
	@Setter
	private String host;
	
	
	protected ExternalServiceEnum externalServiceEnum;
	
	protected WebClient.Builder webClientBuilder;
	
	protected Function<ClientResponse, Mono<? extends Throwable>> functionHttpStausIsError;
	protected Consumer<? super Throwable> onError;
	

	public BaseNetwork(ExternalServiceEnum externalServiceEnum, WebClient.Builder webClientBuilder) {
		
		this.webClientBuilder = webClientBuilder;
		
		this.functionHttpStausIsError = error -> {
			throw new ExternalServiceHttpException(error.statusCode().toString(), externalServiceEnum);	};

		this.onError = error -> {
			if (error instanceof ConnectException) {
				throw new ExternalServiceUnavailableException(error.getMessage(), externalServiceEnum);
			}
		};
	}

	protected <T> Optional<T> sendBlockRequest(Class<T> typeReturnObject, 
			Object content, 
			String path, 
			HttpMethod httpMethod) {
	
		if (content == null) {
			return Optional.empty();
		}
	
		try {
			T return_ = 
				webClientBuilder
					.baseUrl(new URL(protocol, host, "").toString())
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
			
//		} catch (NumberFormatException e) {
//			throw new PortValueInvalidException("Invalid port value: "+port);
		} catch (MalformedURLException e) {
			throw new UrlParseException("Invalid URL: " + protocol + "," + host );
		} 
	}
	

}
