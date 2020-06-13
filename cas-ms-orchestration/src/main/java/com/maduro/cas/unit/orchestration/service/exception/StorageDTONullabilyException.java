package com.maduro.cas.unit.orchestration.service.exception;

import com.maduro.cas.core.exception.base.BaseInternalException;

public class StorageDTONullabilyException extends BaseInternalException {

	public StorageDTONullabilyException() {
		super("StorageDTO cannot be null");
	}

	private static final long serialVersionUID = 1L;
}