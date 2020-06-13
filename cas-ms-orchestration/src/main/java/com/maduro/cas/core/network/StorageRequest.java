package com.maduro.cas.core.network;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;

import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.core.network.base.BaseRequest;


public class StorageRequest extends BaseRequest {
	
	public StorageRequest(ExternalServiceEnum externalServiceEnum) {
		super(externalServiceEnum);
	}

	@Value(value = "${cas-ms.service.storage.port}")
	public void setPort(String port) {
		super.setPort(port);
	}

	public Long saveStorage(byte[] content) {
		
		Optional<Number> oResult = this.sendBlockRequest(Number.class, content, "/file-content", HttpMethod.POST);
		
		return oResult.isPresent() ? oResult.get().longValue() : null;
	
	}

}
