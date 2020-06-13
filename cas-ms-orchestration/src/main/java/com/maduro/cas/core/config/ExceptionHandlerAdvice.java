package com.maduro.cas.core.config;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maduro.cas.core.exception.base.BaseInternalException;
import com.maduro.cas.core.exception.external.ExternalServiceHttpException;
import com.maduro.cas.core.exception.external.ExternalServiceUnavailableException;

import lombok.Data;

@Data
class CustomErrorResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;

}

@ControllerAdvice
public class ExceptionHandlerAdvice {

	private CustomErrorResponse generateCustomErrorResponse(Exception e) {
		return generateCustomErrorResponse(
				(e.getMessage() == null || e.getMessage().isBlank()) ? "Undefined Message" : e.getMessage());
	}

	private CustomErrorResponse generateCustomErrorResponse(String message) {
		CustomErrorResponse customErrorResponse = new CustomErrorResponse();
		customErrorResponse.setTimestamp(LocalDateTime.now());
		customErrorResponse.setMessage(message);
		return customErrorResponse;
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<?> handleException(Exception e) {
		return new ResponseEntity<>(generateCustomErrorResponse(e), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ BaseInternalException.class })
	public ResponseEntity<?> handleBaseInternalException(Exception e) {
		return new ResponseEntity<>(generateCustomErrorResponse(e), HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler({ ExternalServiceHttpException.class })
	public ResponseEntity<?> handleExternalServiceHttpException(Exception e) {
		return new ResponseEntity<>(generateCustomErrorResponse(e), HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler({ ExternalServiceUnavailableException.class })
	public ResponseEntity<?> handleExternalServiceUnavailableException(Exception e) {
		return new ResponseEntity<>(generateCustomErrorResponse(e), HttpStatus.SERVICE_UNAVAILABLE);
	}

}
