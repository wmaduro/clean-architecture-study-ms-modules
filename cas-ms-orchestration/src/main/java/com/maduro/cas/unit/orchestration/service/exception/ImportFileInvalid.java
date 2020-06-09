package com.maduro.cas.unit.orchestration.service.exception;

public class ImportFileInvalid extends RuntimeException {

	public ImportFileInvalid() {
		super("Error getting bytes of a file");
	}

	private static final long serialVersionUID = 1L;
}