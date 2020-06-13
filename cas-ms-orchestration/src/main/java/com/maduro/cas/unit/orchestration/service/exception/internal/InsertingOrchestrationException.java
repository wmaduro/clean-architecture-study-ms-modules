package com.maduro.cas.unit.orchestration.service.exception.internal;

public class InsertingOrchestrationException extends RuntimeException {

	public InsertingOrchestrationException() {
		super("Error when insertingn the orquestration in the local database");
	}
	private static final long serialVersionUID = 1L;

}
