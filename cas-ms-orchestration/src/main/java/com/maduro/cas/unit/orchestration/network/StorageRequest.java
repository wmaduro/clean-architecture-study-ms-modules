package com.maduro.cas.unit.orchestration.network;

import java.net.MalformedURLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class StorageRequest extends BaseRequest {

	@Value(value = "${cas-ms.service.storage.port}")
	public void setPort(String port) {
		super.setPort(port);
	}

	public Long saveStorage(byte[] content) throws NumberFormatException, MalformedURLException {
		
		Optional<Object> oResult = this.sendBlockRequest(content, "/file-content", HttpMethod.POST);
		
		return oResult.isPresent() ? ((Number) oResult.get()).longValue() : null;
	
	}

}
