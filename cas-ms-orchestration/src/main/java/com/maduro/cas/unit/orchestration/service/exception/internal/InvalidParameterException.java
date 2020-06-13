package com.maduro.cas.unit.orchestration.service.exception.internal;

public class InvalidParameterException extends RuntimeException {

	public InvalidParameterException() {
		super("File must be provided");
	}

	private static final long serialVersionUID = 1L;
}