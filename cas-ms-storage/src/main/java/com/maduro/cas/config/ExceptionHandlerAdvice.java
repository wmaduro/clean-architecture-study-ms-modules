package com.maduro.cas.config;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maduro.cas.unit.service.exception.DatabaseNotAvailableException;

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
	public ResponseEntity<?> handleRunTimeException(Exception e) {
		return new ResponseEntity<>(generateCustomErrorResponse(e), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ DatabaseNotAvailableException.class })
	public ResponseEntity<?> handleStorageServiceUnavailableException(DatabaseNotAvailableException e) {
		return new ResponseEntity<>(generateCustomErrorResponse(e), HttpStatus.SERVICE_UNAVAILABLE);
	}

}
