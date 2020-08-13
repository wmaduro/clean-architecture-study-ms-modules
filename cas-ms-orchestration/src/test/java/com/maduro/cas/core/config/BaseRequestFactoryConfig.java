package com.maduro.cas.core.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.core.network.FileParseNetwork;
import com.maduro.cas.core.network.HandEvaluatorNetwork;
import com.maduro.cas.core.network.HandMapperNetwork;
import com.maduro.cas.core.network.StorageNetwork;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Profile("test")
@Configuration
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class BaseRequestFactoryConfig {

	@Bean
	public StorageNetwork storageNetwork() {
		return new StorageNetwork(ExternalServiceEnum.STORAGE, webClientBuilder());
	}
	
	@Bean
	public FileParseNetwork fileParserRequest() {
		return new FileParseNetwork(ExternalServiceEnum.FILE_PARSER, webClientBuilder());
	}
	
	@Bean
	public HandEvaluatorNetwork handEvaluatorNetwork() {
		return new HandEvaluatorNetwork(ExternalServiceEnum.HAND_EVALUATOR, webClientBuilder());
	}
	
	
	@Bean
	public HandMapperNetwork handMapperNetwork() {
		return new HandMapperNetwork(ExternalServiceEnum.HAND_MAPPER, webClientBuilder());
	}
	
	@Bean
	public WebClient.Builder webClientBuilder() {
		
		TcpClient tcpClient = 
				TcpClient.create()
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
					.doOnConnected(
						conn ->
							conn.addHandlerLast(
									new ReadTimeoutHandler(5,  TimeUnit.SECONDS))
					);
					
		
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)));
	}
}
