package com.maduro.cas.unit.orchestration.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import com.maduro.cas.unit.orchestration.domain.Orchestration;
import com.maduro.cas.unit.orchestration.dto.FileParserDTO;
import com.maduro.cas.unit.orchestration.dto.StorageDTO;
import com.maduro.cas.unit.orchestration.repository.OrchestrationRepository;

@Service
public class OrchestrationService {
	

	@Autowired
	private OrchestrationRepository orchestratorRepository;

	public void processFile(MultipartFile file) {

		try {

//			var fileName = UUID.randomUUID().toString() + "_" + file.getName();
//			Path pathFile = Path.of("/home/maduro/lixo/lixo", fileName);
//
//			// save file
//			file.transferTo(pathFile);
			
			
			//Store the file
					
			Long idStorageReference = saveStorage(file.getBytes());
			
			// save db
			orchestratorRepository.save(new Orchestration(null,idStorageReference.toString()));
			
			
			//process File Parser endpoint
			FileParserDTO fileParserDTO = processFileParser(idStorageReference.toString());
			System.out.println(fileParserDTO);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Long saveStorage(byte[] content) {
		
		Long response =  WebClient
				  .builder()
				  .baseUrl("http://localhost:20005")
				  .build()
				  .method(HttpMethod.POST)
				  .uri("/file-content")
				  .body(BodyInserters.fromValue(content))
				  .retrieve()
				  .bodyToMono(Long.class)
				  .block()
				  ;
		return response;
	}
	
	
	
	private FileParserDTO processFileParser(String fileReference) {
		
		FileParserDTO response =  WebClient
		  .builder()
		  .baseUrl("http://localhost:20004")
		  .build()
		  .method(HttpMethod.POST)
		  .uri("/file-parser")
		  .body(BodyInserters.fromValue(new StorageDTO(fileReference)))
		  .retrieve()
		  .bodyToMono(FileParserDTO.class)
		  .block()
		  ;
		 
//		System.out.println("processFileParser: "+response);
		 return response;
	}
	
//	private FileParserDTO processFileParser(String uriFile) {
//		
//		WebClient webClient = WebClient
//				  .builder()
//				   .baseUrl("http://localhost:20004")
//				  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
////				  .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
//				  .build();
//		
//		WebClient.RequestBodySpec uriRoot  = webClient
//			.method(HttpMethod.POST).uri("/file-parser");
//		
//		RequestHeadersSpec<?> requestSpec =
//				uriRoot.body(BodyInserters.fromValue(new FileDTO(uriFile)));
//		
//		String response = 
//				requestSpec.retrieve().bodyToMono(String.class).block();
//		
//		System.out.println("response: "+response);
//		
//		return null;
//	}

	public List<String> getFileList() {
		 
		 return orchestratorRepository
		 	.findAll()
		 	.stream()
		 	.map(orchestration -> orchestration.getFilePath())
		 	.collect(Collectors.toList());
		
	}

}
