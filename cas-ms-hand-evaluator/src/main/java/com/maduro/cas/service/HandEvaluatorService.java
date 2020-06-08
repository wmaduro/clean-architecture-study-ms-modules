package com.maduro.cas.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.maduro.cas.domain.CriticalHandOutcomeEnum;
import com.maduro.cas.domain.HandDataModel;
import com.maduro.cas.dto.HandEvaluatorDTO;
import com.maduro.cas.dto.HandMapperDTO;
import com.maduro.cas.enums.AggressivityBehaviorEnum;
import com.maduro.cas.service.handdata.HandDataService;

@Service
public class HandEvaluatorService {

	private String mainPlayerName = null;
	private AggressivityBehaviorEnum aggressivityBehaviorEnum = AggressivityBehaviorEnum.RAISER;
	private static final String ACTION_PRE_FLOP = "PRE_FLOP";
	

	public HandEvaluatorDTO process(HandMapperDTO handMapperDTO) {
		if (handMapperDTO == null || handMapperDTO.getHandDataModelMap() == null)
			return null;

		HandEvaluatorDTO handEvaluatorDTO = new HandEvaluatorDTO();

		for (Map.Entry<String, List<HandDataModel>> entry : handMapperDTO.getHandDataModelMap().entrySet()) {

			if (!isHandProcessable(entry)) {
				continue;
			}

			try {
				CriticalHandOutcomeEnum criticalHandOutcomeEnum = new HandDataService().analyzeHands(entry.getValue());
				handEvaluatorDTO.addGameCrititalHandDataModel(entry.getKey(), entry.getValue(),
						criticalHandOutcomeEnum);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return handEvaluatorDTO;
	}

	private boolean isHandProcessable(Entry<String, List<HandDataModel>> entry) {
		if (entry.getValue().size() != 2) {
			return false;
		}

		if (!entry.getValue().get(0).getAll_in_action_street().equals(ACTION_PRE_FLOP)) {
			return false;
		}

		if (!checkIfPlayerExists(entry.getValue())) {
			return false;
		}

		if (!checkMainPlayerAggressityBehavior(entry.getValue())) {
			return false;
		}

		return true;
	}

	private boolean checkIfPlayerExists(List<HandDataModel> handDataModelList) {
		if (this.mainPlayerName == null) {
			return true;
		}
		return handDataModelList.stream()
				.anyMatch(handDataModel -> handDataModel.getUser_name().equals(this.mainPlayerName));
	}

	private boolean checkMainPlayerAggressityBehavior(List<HandDataModel> handDataModelList) {

		if (this.mainPlayerName == null || this.mainPlayerName == null) {
			return true;
		}

		return handDataModelList.stream()
				.anyMatch(handDataModel -> handDataModel.getUser_name().equals(this.mainPlayerName)
						&& handDataModel.getAction_pre_flop().equals(this.aggressivityBehaviorEnum.getValue()));
	}



}
