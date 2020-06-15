package com.maduro.cas.service;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.maduro.cas.domain.HandDataModel;
import com.maduro.cas.dto.FileParserDTO;
import com.maduro.cas.dto.HandMapperDTO;
import com.maduro.cas.service.util.HandMapperServiceUtils;

class HandMapperServiceTest {

	private HandMapperService handMapperService = new HandMapperService();

	@Test
	void must_Process_Return_Null() throws Exception {

		HandMapperDTO handMapperDTO = handMapperService.process(null);
		assertTrue(handMapperDTO == null);

	}
	
	@Test
	void must_FileParserDTO_Process_Successfuly() throws Exception {

		FileParserDTO fileParserDTO = HandMapperServiceUtils.getFileParserDTOWithOneHand();
		HandMapperDTO handMapperDTO = handMapperService.process(fileParserDTO);

		assertTrue(validateOutcome(fileParserDTO, handMapperDTO));

	}

	private boolean validateOutcome(FileParserDTO fileParserDTO, HandMapperDTO handMapperDTO) {
		HandDataModel firstHandDataModel = fileParserDTO.getHandDataModelList().get(0);
		return handMapperDTO.getHandDataModelMap().size() == 1
				&& handMapperDTO.getHandDataModelMap().get(firstHandDataModel.getHand()).size() == 2;
	}
}
