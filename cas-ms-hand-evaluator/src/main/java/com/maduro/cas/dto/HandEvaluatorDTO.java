package com.maduro.cas.dto;

import java.util.ArrayList;
import java.util.List;

import com.maduro.cas.domain.CriticalHandOutcomeEnum;
import com.maduro.cas.domain.GameCrititalHandDataModel;
import com.maduro.cas.domain.HandDataModel;

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
