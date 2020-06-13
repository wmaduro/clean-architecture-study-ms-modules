package com.maduro.cas.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.core.network.StorageRequest;

@Configuration
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class BaseRequestFactoryConfig {

	@Bean
	public StorageRequest storageRequest() {
		return new StorageRequest(ExternalServiceEnum.STORAGE);
	}
	
}
