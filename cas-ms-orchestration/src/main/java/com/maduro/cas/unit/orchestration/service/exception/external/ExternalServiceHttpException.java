package com.maduro.cas.unit.orchestration.service.exception.external;

import com.maduro.cas.unit.orchestration.service.exception.base.BaseExternalException;
import com.maduro.cas.unit.orchestration.service.exception.base.enums.ExternalServiceEnum;

public class ExternalServiceHttpException extends BaseExternalException  {

	public ExternalServiceHttpException(String message, ExternalServiceEnum externalServiceEnum) {
		super(message, externalServiceEnum);
	}

	private static final long serialVersionUID = 1L;
}