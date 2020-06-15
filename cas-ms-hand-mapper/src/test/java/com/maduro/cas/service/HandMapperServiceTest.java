package com.maduro.cas.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.maduro.cas.domain.HandDataModel;
import com.maduro.cas.dto.FileParserDTO;
import com.maduro.cas.dto.HandMapperDTO;

class HandMapperServiceTest {

	private HandMapperService handMapperService = new HandMapperService();

	@Test
	void must_Process_Return_Null() throws Exception {

		HandMapperDTO handMapperDTO = handMapperService.process(null);
		assertTrue(handMapperDTO == null);

	}
	
	@Test
	void must_ProcessFileParserDTO_Successfuly() throws Exception {

		FileParserDTO fileParserDTO = HandMapperServiceUtils.getFileParserDTOWithOneHand();

		HandMapperDTO handMapperDTO = handMapperService.process(fileParserDTO);

		HandDataModel firstHandDataModel = fileParserDTO.getHandDataModelList().get(0);
		boolean testConditions = handMapperDTO.getHandDataModelMap().size() == 1
				&& handMapperDTO.getHandDataModelMap().get(firstHandDataModel.getHand()).size() == 2;

		assertTrue(testConditions);
	}
	
}

class HandMapperServiceUtils {

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
