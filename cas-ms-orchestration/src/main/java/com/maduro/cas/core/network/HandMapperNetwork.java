package com.maduro.cas.core.network;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.core.network.base.BaseNetwork;
import com.maduro.cas.unit.orchestration.dto.FileParserDTO;
import com.maduro.cas.unit.orchestration.dto.HandMapperDTO;

public class HandMapperNetwork extends BaseNetwork {

	public HandMapperNetwork(ExternalServiceEnum externalServiceEnum) {
		super(externalServiceEnum);
	}

	@Override
	@Value(value = "${cas-ms.service.hand-mapper.port}")
	public void setPort(String port) {
		super.setPort(port);
	}
	
	@Override
	@Value(value = "${cas-ms.service.hand-mapper.host}")
	public void setHost(String host) {
		super.setHost(host);
	}

	public HandMapperDTO processHandMapper(FileParserDTO fileParserDTO) {
		if (fileParserDTO == null) {
			return null;
		}
		
		Optional<HandMapperDTO> oResult = this.sendBlockRequest(HandMapperDTO.class, fileParserDTO, "/hand-mapper", HttpMethod.POST);
		
		HandMapperDTO handMapperDTO = null;
		if (oResult.isPresent()) {
			handMapperDTO = new ObjectMapper().convertValue(oResult.get(), HandMapperDTO.class);
		}
		return handMapperDTO;
	}

}
