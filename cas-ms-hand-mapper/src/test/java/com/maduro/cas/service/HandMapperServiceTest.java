package com.maduro.cas.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.maduro.cas.domain.HandDataModel;
import com.maduro.cas.dto.FileParserDTO;
import com.maduro.cas.dto.HandMapperDTO;

class HandMapperServiceTest {

	@Test
	public void must_ProcessFileParserDTO_Successfuly() throws Exception {

		FileParserDTO fileParserDTO = prepareTestData();
		
		HandMapperDTO handMapperDTO = new HandMapperService().process(fileParserDTO);

		assertTrue(validateOutcome(fileParserDTO, handMapperDTO));
	}

	private FileParserDTO prepareTestData() throws Exception {

		FileParserDTO fileParserDTO = new FileParserDTO();
		List<HandDataModel> handDataModels = List.of(HandDataModel.builder().game("1").hand("1").build(),
				HandDataModel.builder().game("1").hand("1").build());

		handDataModels.forEach(handDataModel -> {
			fileParserDTO.addHandDataModel(handDataModel);
		});

		return fileParserDTO;

	}

	private boolean validateOutcome(FileParserDTO fileParserDTO, HandMapperDTO handMapperDTO) {

		HandDataModel firstHandDataModel = fileParserDTO.getHandDataModelList().get(0);
		return handMapperDTO.getHandDataModelMap().size() == 1
				&& handMapperDTO.getHandDataModelMap().get(firstHandDataModel.getHand()).size() == 2;

	}
}