package com.maduro.cas.unit.orchestration.network;

import java.net.MalformedURLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maduro.cas.unit.orchestration.dto.FileParserDTO;
import com.maduro.cas.unit.orchestration.dto.HandMapperDTO;

@Component
public class HandMapperRequest extends BaseRequest {

	@Value(value = "${cas-ms.service.hand-mapper.port}")
	public void setPort(String port) {
		super.setPort(port);
	}

	public HandMapperDTO processHandMapper(FileParserDTO fileParserDTO) throws NumberFormatException, MalformedURLException {
		
		Optional<Object> oResult = this.sendBlockRequest(fileParserDTO, "/hand-mapper", HttpMethod.POST);
		
		HandMapperDTO handMapperDTO = null;
		if (oResult.isPresent()) {
			handMapperDTO = new ObjectMapper().convertValue(oResult.get(), HandMapperDTO.class);
		}
		return handMapperDTO;
	}

}
