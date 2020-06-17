package com.maduro.cas.unit.orchestration.service.exception;

import com.maduro.cas.core.exception.base.BaseInternalException;

public class ImportFileInvalidException extends BaseInternalException {

	public ImportFileInvalidException() {
		super("Error getting bytes of a file");
	}

	private static final long serialVersionUID = 1L;
}