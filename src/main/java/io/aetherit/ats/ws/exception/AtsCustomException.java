package io.aetherit.ats.ws.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@SuppressWarnings("serial")
public class AtsCustomException extends RuntimeException {
	HttpStatus status;
	
	public AtsCustomException(String message) {
		super(message);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	
	public AtsCustomException(String message, HttpStatus status) {
		super(message);
		this.status = status; 
	}
	
	
}
