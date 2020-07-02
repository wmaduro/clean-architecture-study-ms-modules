package com.maduro.cas.unit.orchestration.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrchestrationDTO {
	private String id;
	private String version="v1.0.0";
}
