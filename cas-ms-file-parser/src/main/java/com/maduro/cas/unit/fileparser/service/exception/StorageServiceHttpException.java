package com.maduro.cas.unit.fileparser.service.exception;

public class StorageServiceHttpException extends RuntimeException  {

	public StorageServiceHttpException(String genericMessage) {
		super("Storage access error: "+genericMessage);
	}

	private static final long serialVersionUID = 1L;
}