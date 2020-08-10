package com.maduro.cas.core.network;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.core.network.base.BaseNetwork;


public class StorageNetwork extends BaseNetwork {
	
	public StorageNetwork(ExternalServiceEnum externalServiceEnum, Builder webClientBuilder) {
		super(externalServiceEnum, webClientBuilder);
	}
		
	@Override
	@Value(value = "${cas-ms.service.storage.host}")
	public void setHost(String host) {
		super.setHost(host);
	}

	public Long saveStorage(byte[] content) {
		
		Optional<Number> oResult = this.sendBlockRequest(Number.class, content, "/storage", HttpMethod.POST);
		
		return oResult.isPresent() ? oResult.get().longValue() : null;
	
	}

}
