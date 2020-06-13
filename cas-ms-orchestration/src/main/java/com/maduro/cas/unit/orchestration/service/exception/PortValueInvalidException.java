package com.maduro.cas.unit.orchestration.service.exception;

import com.maduro.cas.core.exception.base.BaseInternalException;
import com.maduro.cas.core.exception.base.BaseRuntimeException;

public class PortValueInvalidException extends BaseInternalException {

	public PortValueInvalidException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
