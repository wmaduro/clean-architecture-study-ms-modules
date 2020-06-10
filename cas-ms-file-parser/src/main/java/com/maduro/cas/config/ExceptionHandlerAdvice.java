package com.maduro.cas.config;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maduro.cas.unit.fileparser.service.exception.StorageServiceHttpException;
import com.maduro.cas.unit.fileparser.service.exception.StorageServiceUnavailableException;

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

//	@ExceptionHandler({ ConnectException.class })
//	public ResponseEntity<?> handleRunTimeException(ConnectException e) {
//		return new ResponseEntity<>(
//				generateCustomErrorResponse("The service couldn't reach the server: " + e.getMessage()),
//				HttpStatus.INTERNAL_SERVER_ERROR);
//	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<?> handleRunTimeException(Exception e) {
		return new ResponseEntity<>(generateCustomErrorResponse(e), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ StorageServiceUnavailableException.class})
	public ResponseEntity<?> handleStorageServiceUnavailableException(StorageServiceUnavailableException e) {
		return new ResponseEntity<>(generateCustomErrorResponse("Storage Unavailable: " + e.getMessage()),
				HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler({ StorageServiceHttpException.class })
	public ResponseEntity<?> handleStorageServiceHttpException(StorageServiceHttpException e) {
		return new ResponseEntity<>(generateCustomErrorResponse("Storage Http Error: " + e.getMessage()),
				HttpStatus.BAD_GATEWAY);
	}

}