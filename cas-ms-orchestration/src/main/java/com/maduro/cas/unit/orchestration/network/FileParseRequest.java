package com.maduro.cas.unit.orchestration.network;

import java.net.MalformedURLException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maduro.cas.unit.orchestration.dto.FileParserDTO;
import com.maduro.cas.unit.orchestration.dto.StorageDTO;

@Component
public class FileParseRequest extends BaseRequest {

	@Value(value = "${cas-ms.service.file-parser.port}")
	public void setPort(String port) {
		super.setPort(port);
	}

	public FileParserDTO processFileParser(StorageDTO storageDTO) throws NumberFormatException, MalformedURLException {

		Optional<Object> oResult = this.sendBlockRequest(storageDTO, "/file-parser", HttpMethod.POST);

		FileParserDTO fileParserDTO = null;
		if (oResult.isPresent()) {
			fileParserDTO = new ObjectMapper().convertValue(oResult.get(), FileParserDTO.class);
		}
		return fileParserDTO;
	}

}
