package com.maduro.cas.core.exception.base;

public abstract class BaseRuntimeException extends RuntimeException{

	public BaseRuntimeException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
