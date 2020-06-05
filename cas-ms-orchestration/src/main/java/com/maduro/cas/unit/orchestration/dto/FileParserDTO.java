package com.maduro.cas.unit.orchestration.dto;

import java.util.ArrayList;
import java.util.List;

import com.maduro.cas.unit.orchestration.domain.HandDataModel;

import lombok.Getter;

public class FileParserDTO {
	@Getter
	final private List<HandDataModel> handDataModelList = new ArrayList<>();
	
	public void addHandDataModel(HandDataModel handDataModel) {
		this.handDataModelList.add(handDataModel);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		this.handDataModelList.stream().forEach(handDataModel -> {
			sb.append(handDataModel.getGame() + " | " + handDataModel.getHand() +"\n");
		});
		return sb.toString();
	}
}
