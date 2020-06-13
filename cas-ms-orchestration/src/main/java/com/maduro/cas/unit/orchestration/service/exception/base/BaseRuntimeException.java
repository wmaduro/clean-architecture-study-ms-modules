package com.maduro.cas.unit.orchestration.service.exception.base;

public abstract class BaseRuntimeException extends RuntimeException{

	public BaseRuntimeException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
