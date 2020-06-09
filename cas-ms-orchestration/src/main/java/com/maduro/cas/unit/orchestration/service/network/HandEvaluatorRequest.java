package com.maduro.cas.unit.orchestration.service.network;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maduro.cas.unit.orchestration.dto.HandEvaluatorDTO;
import com.maduro.cas.unit.orchestration.dto.HandMapperDTO;

@Component
public class HandEvaluatorRequest extends BaseRequest {

	@Value(value = "${cas-ms.service.hand-evaluator.port}")
	public void setPort(String port) {
		super.setPort(port);
	}

	public HandEvaluatorDTO processHandEvaluator(HandMapperDTO handMapperDTO) {
		if (handMapperDTO == null) {
			return null;
		}
		Optional<Object> oResult = this.sendBlockRequest(handMapperDTO, "/hand-evaluator", HttpMethod.POST);
				
		HandEvaluatorDTO handEvaluatorDTO = null;
		if (oResult.isPresent()) {
			handEvaluatorDTO = new ObjectMapper().convertValue(oResult.get(), HandEvaluatorDTO.class);
		}
		return handEvaluatorDTO;
		
	}

}
