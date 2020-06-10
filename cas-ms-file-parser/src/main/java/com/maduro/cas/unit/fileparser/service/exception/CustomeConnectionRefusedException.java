package com.maduro.cas.unit.fileparser.service.exception;

public class CustomeConnectionRefusedException  extends RuntimeException {

	public CustomeConnectionRefusedException(String genericMessage) {
		super("Error connection: "+genericMessage);
	}

	private static final long serialVersionUID = 1L;
}