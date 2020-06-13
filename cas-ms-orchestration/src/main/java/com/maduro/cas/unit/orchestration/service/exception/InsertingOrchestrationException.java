package com.maduro.cas.unit.orchestration.service.exception;

import com.maduro.cas.core.exception.base.BaseInternalException;

public class InsertingOrchestrationException extends BaseInternalException {

	public InsertingOrchestrationException() {
		super("Error when insertingn the orquestration in the local database");
	}
	private static final long serialVersionUID = 1L;

}
