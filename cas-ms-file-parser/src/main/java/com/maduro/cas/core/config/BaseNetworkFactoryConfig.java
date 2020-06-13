package com.maduro.cas.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.core.network.StorageNetwork;

@Configuration
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class BaseNetworkFactoryConfig {

	@Bean
	public StorageNetwork storageNetwork() {
		return new StorageNetwork(ExternalServiceEnum.STORAGE);
	}
	
}
