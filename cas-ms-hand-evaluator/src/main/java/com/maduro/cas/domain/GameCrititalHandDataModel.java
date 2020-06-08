package com.maduro.cas.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameCrititalHandDataModel {
	private String gameCode;
	private List<HandDataModel> handDataModelList;
	private CriticalHandOutcomeEnum criticalHandOutcomeEnum;
}