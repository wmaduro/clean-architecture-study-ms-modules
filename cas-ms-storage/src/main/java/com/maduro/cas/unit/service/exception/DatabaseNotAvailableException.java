package com.maduro.cas.unit.service.exception;

public class DatabaseNotAvailableException extends RuntimeException {

	public DatabaseNotAvailableException() {
		super("Storage Database Unavailable");
	}

	private static final long serialVersionUID = 1L;
}