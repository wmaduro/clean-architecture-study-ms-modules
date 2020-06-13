package com.maduro.cas.unit.orchestration.service.exception.internal;

import com.maduro.cas.unit.orchestration.service.exception.base.BaseInternalException;

public class ImportFileInvalid extends BaseInternalException {

	public ImportFileInvalid() {
		super("Error getting bytes of a file");
	}

	private static final long serialVersionUID = 1L;
}