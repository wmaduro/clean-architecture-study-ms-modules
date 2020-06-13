package com.maduro.cas.core.network;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.core.network.base.BaseRequest;
import com.maduro.cas.unit.orchestration.dto.HandEvaluatorDTO;
import com.maduro.cas.unit.orchestration.dto.HandMapperDTO;


public class HandEvaluatorRequest extends BaseRequest {

	public HandEvaluatorRequest(ExternalServiceEnum externalServiceEnum) {
		super(externalServiceEnum);	
	}

	@Value(value = "${cas-ms.service.hand-evaluator.port}")
	public void setPort(String port) {
		super.setPort(port);
	}

	public HandEvaluatorDTO processHandEvaluator(HandMapperDTO handMapperDTO) {
		if (handMapperDTO == null) {
			return null;
		}
		Optional<HandEvaluatorDTO> oResult = this.sendBlockRequest(HandEvaluatorDTO.class, handMapperDTO, "/hand-evaluator", HttpMethod.POST);
				
		HandEvaluatorDTO handEvaluatorDTO = null;
		if (oResult.isPresent()) {
			handEvaluatorDTO = new ObjectMapper().convertValue(oResult.get(), HandEvaluatorDTO.class);
		}
		return handEvaluatorDTO;
		
	}

}
