package com.maduro.cas.unit.result.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDTO {
	private String gameCode;
	private String result;
}
