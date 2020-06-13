package com.maduro.cas.unit.orchestration.service.exception.internal;

public class StorageDTONullabilyException extends RuntimeException {

	public StorageDTONullabilyException() {
		super("StorageDTO cannot be null");
	}

	private static final long serialVersionUID = 1L;
}