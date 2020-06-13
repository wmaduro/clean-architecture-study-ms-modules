package com.maduro.cas.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.core.network.FileParseNetwork;
import com.maduro.cas.core.network.HandEvaluatorNetwork;
import com.maduro.cas.core.network.HandMapperNetwork;
import com.maduro.cas.core.network.StorageNetwork;

@Configuration
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class BaseRequestFactoryConfig {

	@Bean
	public StorageNetwork storageNetwork() {
		return new StorageNetwork(ExternalServiceEnum.STORAGE);
	}
	
	@Bean
	public FileParseNetwork fileParserRequest() {
		return new FileParseNetwork(ExternalServiceEnum.FILE_PARSER);
	}
	
	@Bean
	public HandEvaluatorNetwork handEvaluatorNetwork() {
		return new HandEvaluatorNetwork(ExternalServiceEnum.HAND_EVALUATOR);
	}
	
	
	@Bean
	public HandMapperNetwork handMapperNetwork() {
		return new HandMapperNetwork(ExternalServiceEnum.HAND_MAPPER);
	}
}
