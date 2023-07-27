package com.recyclerMicroservice.exception;

public class NoContentFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;

	public NoContentFoundException() {
	}

	public NoContentFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
