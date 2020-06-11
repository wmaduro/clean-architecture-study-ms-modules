package com.maduro.cas.unit.fileparser.service.network;

import java.net.ConnectException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.maduro.cas.unit.fileparser.dto.StorageDTO;
import com.maduro.cas.unit.fileparser.service.exception.StorageServiceHttpException;
import com.maduro.cas.unit.fileparser.service.exception.StorageServiceUnavailableException;
import com.maduro.cas.unit.fileparser.service.network.base.BaseRequest;

@Component
public class StorageRequest extends BaseRequest {

	public StorageRequest() {
		
		this.functionHttpStausIsError = error -> {
			throw new StorageServiceHttpException(error.statusCode().toString());
		};

		this.onError = error -> {
			if (error instanceof ConnectException) {
				throw new StorageServiceUnavailableException(error.getMessage());
			}
		};
	}

	@Value(value = "${cas-ms.service.storage.port}")
	public void setPort(String port) {
		super.setPort(port);
	}

	public byte[] loadFromStorage(StorageDTO storageDTO) {
		
		final String fullPath = "/file-content/"+storageDTO.getFileReference();
		
		Optional<byte[]> oResult = this.sendBlockRequest(byte[].class, storageDTO, 
				fullPath, 
				HttpMethod.GET);
		
		return oResult.isPresent() ? oResult.get() : null;
	
	}

}
