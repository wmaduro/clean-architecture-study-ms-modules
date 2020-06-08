package com.maduro.cas.unit.orchestration.service.network;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.maduro.cas.unit.orchestration.dto.FileParserDTO;
import com.maduro.cas.unit.orchestration.dto.HandEvaluatorDTO;
import com.maduro.cas.unit.orchestration.dto.HandMapperDTO;
import com.maduro.cas.unit.orchestration.dto.StorageDTO;

public class RequestHelper {
	
	public static Long saveStorage(byte[] content) {
				
		return  WebClient
				  .builder()
				  .baseUrl("http://localhost:20005")
				  .build()
				  .method(HttpMethod.POST)
				  .uri("/file-content")
				  .body(BodyInserters.fromValue(content))
				  .retrieve()
				  .bodyToMono(Long.class)
				  .block();

	}
	
	public static HandEvaluatorDTO processHandEvaluator(HandMapperDTO handMapperDTO) {
		
		return WebClient
			  .builder()
			  .baseUrl("http://localhost:20007")
			  .build()
			  .method(HttpMethod.POST)
			  .uri("/hand-evaluator")
			  .body(BodyInserters.fromValue(handMapperDTO))
			  .retrieve()
			  .bodyToMono(HandEvaluatorDTO.class)
			  .block();
	}
	
	public static HandMapperDTO processHandMapper(FileParserDTO fileParserDTO) {
		
		return  WebClient
		  .builder()
		  .baseUrl("http://localhost:20006")
		  .build()
		  .method(HttpMethod.POST)
		  .uri("/hand-mapper")
		  .body(BodyInserters.fromValue(fileParserDTO))
		  .retrieve()
		  .bodyToMono(HandMapperDTO.class)
		  .block();
		 
	}
	
	
	public static FileParserDTO processFileParser(String fileReference) {
		
		return  WebClient
		  .builder()
		  .baseUrl("http://localhost:20004")
		  .build()
		  .method(HttpMethod.POST)
		  .uri("/file-parser")
		  .body(BodyInserters.fromValue(new StorageDTO(fileReference)))
		  .retrieve()
		  .bodyToMono(FileParserDTO.class)
		  .block();
		
	}
}
