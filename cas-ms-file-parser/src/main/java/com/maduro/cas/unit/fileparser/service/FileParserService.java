package com.maduro.cas.unit.fileparser.service;

import java.lang.reflect.Field;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.maduro.cas.unit.fileparser.domain.HandDataModel;
import com.maduro.cas.unit.fileparser.dto.FileParserDTO;
import com.maduro.cas.unit.fileparser.dto.StorageDTO;

@Service
public class FileParserService {

	public FileParserDTO processStorage(StorageDTO storageDTO) throws Exception {
		
		FileParserDTO fileParserDTO = new FileParserDTO();

		String fileContent = new String(loadFromStorage(storageDTO.getFileReference()));
		
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
		
		byte[] response =  WebClient
				  .builder()
				  .baseUrl("http://localhost:20005")
				  .build()
				  .method(HttpMethod.GET)
				  .uri("/file-content/"+idReference)
				  .retrieve()
				  .bodyToMono(byte[].class)
				  .block()
				  ;
		return response;
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
