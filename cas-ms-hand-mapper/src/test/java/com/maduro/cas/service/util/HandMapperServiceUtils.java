package com.maduro.cas.service.util;

import java.util.List;

import com.maduro.cas.domain.HandDataModel;
import com.maduro.cas.dto.FileParserDTO;

public class HandMapperServiceUtils {

	public static FileParserDTO getFileParserDTOWithOneHand() throws Exception {

		FileParserDTO fileParserDTO = new FileParserDTO();
		List<HandDataModel> handDataModels = 
				List.of(HandDataModel.builder().game("1").hand("1").build(), 
						HandDataModel.builder().game("1").hand("1").build());

		handDataModels.forEach(handDataModel -> {
			fileParserDTO.addHandDataModel(handDataModel);	
		});

		return fileParserDTO;

	}
}
