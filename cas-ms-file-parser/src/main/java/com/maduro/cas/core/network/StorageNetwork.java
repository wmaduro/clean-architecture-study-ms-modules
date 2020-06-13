package com.maduro.cas.core.network;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;

import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.core.network.base.BaseNetwork;
import com.maduro.cas.unit.dto.StorageDTO;

public class StorageNetwork extends BaseNetwork {

	public StorageNetwork(ExternalServiceEnum externalServiceEnum) {
		super(externalServiceEnum);
	}

	@Value(value = "${cas-ms.service.storage.port}")
	public void setPort(String port) {
		super.setPort(port);
	}

	public byte[] loadFromStorage(StorageDTO storageDTO) {

		final String fullPath = "/storage/" + storageDTO.getFileReference();

		Optional<byte[]> oResult = this.sendBlockRequest(byte[].class, storageDTO, fullPath, HttpMethod.GET);

		return oResult.isPresent() ? oResult.get() : null;

	}

}
