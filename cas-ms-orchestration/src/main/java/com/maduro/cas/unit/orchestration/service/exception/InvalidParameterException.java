package com.maduro.cas.unit.orchestration.service.exception;

import com.maduro.cas.core.exception.base.BaseInternalException;

public class InvalidParameterException extends BaseInternalException {

	public InvalidParameterException() {
		super("File must be provided");
	}

	private static final long serialVersionUID = 1L;
}