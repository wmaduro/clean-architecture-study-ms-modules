package com.maduro.cas.core.exception.external;

import com.maduro.cas.core.exception.base.BaseExternalException;
import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;

public class ExternalServiceUnavailableException  extends BaseExternalException {

	public ExternalServiceUnavailableException(String message, ExternalServiceEnum externalServiceEnum) {
		super(message, externalServiceEnum);
	}

	private static final long serialVersionUID = 1L;
}