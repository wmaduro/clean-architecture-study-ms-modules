package com.maduro.cas.core.network;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.core.network.base.BaseNetwork;
import com.maduro.cas.unit.dto.StorageDTO;

public class StorageNetwork extends BaseNetwork {

	public StorageNetwork(ExternalServiceEnum externalServiceEnum, Builder webClientBuilder) {
		super(externalServiceEnum, webClientBuilder);
	}

	@Override
	@Value(value = "${cas-ms.service.storage.port}")
	public void setPort(Integer port) {
		super.setPort(port);
	}
	
	@Override
	@Value(value = "${cas-ms.service.storage.host}")
	public void setHost(String host) {
		super.setHost(host);
	}
	
	public byte[] loadFromStorage(StorageDTO storageDTO) {

		final String fullPath = "/storage/" + storageDTO.getFileReference();

		Optional<byte[]> oResult = this.sendBlockRequest(byte[].class, storageDTO, fullPath, HttpMethod.GET);

		return oResult.isPresent() ? oResult.get() : null;

	}

}
