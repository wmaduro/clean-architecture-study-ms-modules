package com.maduro.cas.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.maduro.cas.domain.CriticalHandOutcomeEnum;
import com.maduro.cas.domain.HandDataModel;
import com.maduro.cas.dto.HandEvaluatorDTO;
import com.maduro.cas.dto.HandMapperDTO;

class HandEvaluatorServiceTest {
	private static final String ACTION_PRE_FLOP = "PRE_FLOP";

	@Test
	public void must_ProcessFileParserDTO_Successfuly() throws Exception {

		HandMapperDTO handMapperDTO = prepareTestData();

		HandEvaluatorDTO handEvaluatorDTO = new HandEvaluatorService().process(handMapperDTO);

		assertTrue(validateOutcome(handEvaluatorDTO));
	}

	private HandMapperDTO prepareTestData() {

		HandMapperDTO handMapperDTO = new HandMapperDTO();

		HandDataModel handDataModelWmaduro = HandDataModel.builder().game("1").hand("1").user_name("wmaduro")
				.card_sequence("AcAs").value_won("100").all_in_action_street(ACTION_PRE_FLOP).build();
		HandDataModel handDataModelOpponent = HandDataModel.builder().game("1").hand("1").user_name("wmaduro")
				.card_sequence("7c2s").value_won("0").all_in_action_street(ACTION_PRE_FLOP).build();

		handMapperDTO.mapHandDataModel(handDataModelWmaduro);
		handMapperDTO.mapHandDataModel(handDataModelOpponent);

		return handMapperDTO;

	}

	private boolean validateOutcome(HandEvaluatorDTO handEvaluatorDTO) {
		return handEvaluatorDTO.getGameCrititalHandDataModelList().size() == 1 && handEvaluatorDTO.getGameCrititalHandDataModelList().get(0).getCriticalHandOutcomeEnum() == CriticalHandOutcomeEnum.BEST_WIN;
	}

}
