package com.maduro.cas.unit.orchestration.domain;

import java.util.List;

import org.springframework.boot.context.properties.ConstructorBinding;

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