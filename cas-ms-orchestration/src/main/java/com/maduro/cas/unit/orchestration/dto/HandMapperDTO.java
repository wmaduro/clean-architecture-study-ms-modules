package com.maduro.cas.unit.orchestration.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.maduro.cas.unit.orchestration.domain.HandDataModel;

import lombok.Getter;

public class HandMapperDTO {
	@Getter
	final private Map<String, List<HandDataModel>> handDataModelMap = 
			 new TreeMap<>();
	
	public void mapHandDataModel(HandDataModel handDataModel) {
		if (!handDataModelMap.containsKey(handDataModel.getHand())) {
			handDataModelMap.put(handDataModel.getHand(), new ArrayList<HandDataModel>());
		}
		handDataModelMap.get(handDataModel.getHand()).add(handDataModel);
	}
	
}
