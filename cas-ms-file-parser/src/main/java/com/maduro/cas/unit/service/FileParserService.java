package com.maduro.cas.unit.service;

import java.lang.reflect.Field;

import org.springframework.stereotype.Service;

import com.maduro.cas.core.network.StorageNetwork;
import com.maduro.cas.unit.domain.HandDataModel;
import com.maduro.cas.unit.dto.FileParserDTO;
import com.maduro.cas.unit.dto.StorageDTO;

@Service
public class FileParserService {

	private StorageNetwork storageNetwork;
	
	public FileParserService(StorageNetwork storageNetwork) {
		this.storageNetwork = storageNetwork;
	}

	public FileParserDTO processStorage(StorageDTO storageDTO) throws Exception {

		FileParserDTO fileParserDTO = new FileParserDTO();

		byte[] bytes = storageNetwork.loadFromStorage(storageDTO);

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
