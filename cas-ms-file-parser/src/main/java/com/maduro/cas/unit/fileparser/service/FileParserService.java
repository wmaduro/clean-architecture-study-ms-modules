package com.maduro.cas.unit.fileparser.service;

import java.lang.reflect.Field;
import java.net.ConnectException;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.maduro.cas.unit.fileparser.domain.HandDataModel;
import com.maduro.cas.unit.fileparser.dto.FileParserDTO;
import com.maduro.cas.unit.fileparser.dto.StorageDTO;
import com.maduro.cas.unit.fileparser.service.exception.StorageServiceHttpException;
import com.maduro.cas.unit.fileparser.service.exception.StorageServiceUnavailableException;

@Service
public class FileParserService {

	public FileParserDTO processStorage(StorageDTO storageDTO) throws Exception {
		
		FileParserDTO fileParserDTO = new FileParserDTO();

		byte[] bytes = loadFromStorage(storageDTO.getFileReference());	
		
		if (bytes == null) {
			return fileParserDTO;
		}
		
		String fileContent = new String(bytes);
		
		final String HEAD = "\"game\"";
		
		String[] lines = fileContent.split("\n");
		for (String line : lines) {
			if (line.startsWith(HEAD)) {
				continue;
			}
			HandDataModel handDataModel = parseLine(line);
			if (handDataModel.hasAllValidFields()) {
				fileParserDTO.addHandDataModel(handDataModel);
			}
		}
		
		
		return fileParserDTO;
	}

	private byte[] loadFromStorage(String idReference) {
		
		return  WebClient
				  .builder()
				  .baseUrl("http://localhost:20005")
				  .build()
				  .method(HttpMethod.GET)
				  .uri("/file-content/"+idReference)
				  .retrieve()
				  
//				  .onStatus(HttpStatus::is4xxClientError, response -> {
//					  System.out.println("erooo 400 "+response.statusCode());
//			             throw new StorageServiceException("4000 testttttttt");
//			         })
//				  .onStatus(HttpStatus::is5xxServerError, response -> {
//					  System.out.println("erooo 500 "+response.statusCode());
//			            throw new StorageServiceException("5000 testttttttt");
//			         })
				  
				  .onStatus(HttpStatus::isError, error -> {
					  System.out.println("----Http error: "+error);
			         throw new StorageServiceHttpException("Http error: "+error.rawStatusCode());
			       })
				  
				  .bodyToMono(byte[].class)
				  .doOnError(error->{
					  
					  if (error instanceof ConnectException) {
						  throw new StorageServiceUnavailableException(error.getMessage());  
					  }
					  
					  
				  })
				  .block();
		
	}

	private HandDataModel parseLine(String line) throws Exception {
		String[] fieldsFromFile = line.split(",");
		return (HandDataModel) setObjectFieldFromArray(HandDataModel.class, fieldsFromFile);
	};

	private <T> Object setObjectFieldFromArray(Class<T> clazz, String[] array) throws Exception {

		Object object = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {

			if (array.length > i) {

				Field field = fields[i];
				boolean accessible = field.isAccessible();
				field.setAccessible(true);

				String value = array[i];
				if (!field.getName().equals("user_name")) {
					value = value.replace("\"", "").replace(" ", "");
				}
				field.set(object, value);

				field.setAccessible(accessible);
			}
		}
		return object;

	}

}
