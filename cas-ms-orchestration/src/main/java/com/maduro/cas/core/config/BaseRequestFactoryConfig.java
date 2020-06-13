package com.maduro.cas.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.core.network.FileParseRequest;
import com.maduro.cas.core.network.HandEvaluatorRequest;
import com.maduro.cas.core.network.HandMapperRequest;
import com.maduro.cas.core.network.StorageRequest;

@Configuration
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class BaseRequestFactoryConfig {

	@Bean
	public StorageRequest storageRequest() {
		return new StorageRequest(ExternalServiceEnum.STORAGE);
	}
	
	@Bean
	public FileParseRequest fileParserRequest() {
		return new FileParseRequest(ExternalServiceEnum.FILE_PARSER);
	}
	
	@Bean
	public HandEvaluatorRequest handEvaluatorRequest() {
		return new HandEvaluatorRequest(ExternalServiceEnum.HAND_EVALUATOR);
	}
	
	
	@Bean
	public HandMapperRequest handMapperRequest() {
		return new HandMapperRequest(ExternalServiceEnum.HAND_MAPPER);
	}
}
