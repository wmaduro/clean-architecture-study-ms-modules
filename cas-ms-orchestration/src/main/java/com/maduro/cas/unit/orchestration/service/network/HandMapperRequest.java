package com.maduro.cas.unit.orchestration.service.network;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maduro.cas.unit.orchestration.dto.FileParserDTO;
import com.maduro.cas.unit.orchestration.dto.HandMapperDTO;
import com.maduro.cas.unit.orchestration.service.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.unit.orchestration.service.network.base.BaseRequest;

public class HandMapperRequest extends BaseRequest {

	public HandMapperRequest(ExternalServiceEnum externalServiceEnum) {
		super(externalServiceEnum);
	}

	@Value(value = "${cas-ms.service.hand-mapper.port}")
	public void setPort(String port) {
		super.setPort(port);
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
