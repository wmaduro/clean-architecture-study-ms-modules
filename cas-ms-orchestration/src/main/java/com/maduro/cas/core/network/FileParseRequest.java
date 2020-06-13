package com.maduro.cas.core.network;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;
import com.maduro.cas.core.network.base.BaseRequest;
import com.maduro.cas.unit.orchestration.dto.FileParserDTO;
import com.maduro.cas.unit.orchestration.dto.StorageDTO;
import com.maduro.cas.unit.orchestration.service.exception.StorageDTONullabilyException;

public class FileParseRequest extends BaseRequest {
	
	public FileParseRequest(ExternalServiceEnum externalServiceEnum) {
		super(externalServiceEnum);
	}

	@Value(value = "${cas-ms.service.file-parser.port}")
	public void setPort(String port) {
		super.setPort(port);
	}

	public FileParserDTO processFileParser(StorageDTO storageDTO) {
		
		if (storageDTO == null || storageDTO.getFileReference().isBlank()) {
			throw new StorageDTONullabilyException();
		}	

		Optional<FileParserDTO> oResult = this.sendBlockRequest(FileParserDTO.class, storageDTO, "/file-parser", HttpMethod.POST);

		FileParserDTO fileParserDTO = null;
		if (oResult.isPresent()) {
			fileParserDTO = new ObjectMapper().convertValue(oResult.get(), FileParserDTO.class);
		}
		return fileParserDTO;
	}

}
