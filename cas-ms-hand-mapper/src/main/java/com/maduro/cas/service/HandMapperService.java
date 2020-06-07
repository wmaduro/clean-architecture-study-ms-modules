package com.maduro.cas.service;

import org.springframework.stereotype.Service;

import com.maduro.cas.dto.FileParserDTO;
import com.maduro.cas.dto.HandMapperDTO;

@Service
public class HandMapperService {

	public HandMapperDTO process(FileParserDTO fileParserDTO) {

		if (fileParserDTO == null || fileParserDTO.getHandDataModelList() == null)
			return null;

		HandMapperDTO handMapperDTO = new HandMapperDTO();

		fileParserDTO.getHandDataModelList().forEach(handDataModel -> {
			handMapperDTO.mapHandDataModel(handDataModel);
		});

		return handMapperDTO;
	}



}
