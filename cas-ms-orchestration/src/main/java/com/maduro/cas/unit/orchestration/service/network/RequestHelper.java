package com.maduro.cas.unit.orchestration.service.network;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.maduro.cas.unit.orchestration.dto.FileParserDTO;
import com.maduro.cas.unit.orchestration.dto.HandEvaluatorDTO;
import com.maduro.cas.unit.orchestration.dto.HandMapperDTO;
import com.maduro.cas.unit.orchestration.dto.StorageDTO;

@Component
public class RequestHelper {
	
	@Value(value = "${cas-ms.protocol}")
	private String protocol;
	@Value(value = "${cas-ms.host}")
	private String host;
	@Value(value = "${cas-ms.service.storage.port}")
	private String storagePort;
	@Value(value = "${cas-ms.service.hand-evaluator.port}")
	private String handEvaluatorPort;
	@Value(value = "${cas-ms.service.hand-mapper.port}")
	private String handMapperPort;
	@Value(value = "${cas-ms.service.file-parser.port}")
	private String fileparserPort;
		
//	public Long saveStorage(byte[] content) throws MalformedURLException {
//		
//		Integer i =(Integer) WebClient
//				  .builder()
//				  .baseUrl(new URL(protocol, host, Integer.parseInt(storagePort), "").toString())
//				  .build()
//				  .method(HttpMethod.POST)
//				  .uri("/file-content")
//				  .body(BodyInserters.fromValue(content))
//				  .retrieve()
//				  .bodyToMono(Object.class)
//				  .block();
//		
//		return  Long.valueOf(i);
//
//	}
	
//	public HandEvaluatorDTO processHandEvaluator(HandMapperDTO handMapperDTO) throws NumberFormatException, MalformedURLException {
//		
//		return WebClient
//			  .builder()
//			  .baseUrl(new URL(protocol, host, Integer.parseInt(handEvaluatorPort), "").toString())
//			  .build()
//			  .method(HttpMethod.POST)
//			  .uri("/hand-evaluator")
//			  .body(BodyInserters.fromValue(handMapperDTO))
//			  .retrieve()
//			  .bodyToMono(HandEvaluatorDTO.class)
//			  .block();
//	}
	
//	public HandMapperDTO processHandMapper(FileParserDTO fileParserDTO) throws NumberFormatException, MalformedURLException {
//		
//		return  WebClient
//		  .builder()
//		  .baseUrl(new URL(protocol, host, Integer.parseInt(handMapperPort), "").toString())
//		  .build()
//		  .method(HttpMethod.POST)
//		  .uri("/hand-mapper")
//		  .body(BodyInserters.fromValue(fileParserDTO))
//		  .retrieve()
//		  .bodyToMono(HandMapperDTO.class)
//		  .block();
//		 
//	}
	
	
	public FileParserDTO processFileParser(String fileReference) throws NumberFormatException, MalformedURLException {
		
		return  WebClient
		  .builder()
		  .baseUrl(new URL(protocol, host, Integer.parseInt(fileparserPort), "").toString())
		  .build()
		  .method(HttpMethod.POST)
		  .uri("/file-parser")
		  .body(BodyInserters.fromValue(new StorageDTO(fileReference)))
		  .retrieve()
		  .bodyToMono(FileParserDTO.class)
		  .block();
		
	}
}
