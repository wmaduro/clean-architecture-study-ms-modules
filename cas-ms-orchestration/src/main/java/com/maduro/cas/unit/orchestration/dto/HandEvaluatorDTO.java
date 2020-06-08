package com.maduro.cas.unit.orchestration.dto;

import java.util.ArrayList;
import java.util.List;

import com.maduro.cas.unit.orchestration.domain.CriticalHandOutcomeEnum;
import com.maduro.cas.unit.orchestration.domain.GameCrititalHandDataModel;
import com.maduro.cas.unit.orchestration.domain.HandDataModel;

import lombok.Getter;

public class HandEvaluatorDTO {

	@Getter
	private List<GameCrititalHandDataModel> gameCrititalHandDataModelList = new ArrayList<>();

	public void addGameCrititalHandDataModel(String gameCode, List<HandDataModel> handDataModelList,
			CriticalHandOutcomeEnum criticalHandOutcomeEnum) {
		
		gameCrititalHandDataModelList
				.add(new GameCrititalHandDataModel(gameCode, handDataModelList, criticalHandOutcomeEnum));

	}

}
