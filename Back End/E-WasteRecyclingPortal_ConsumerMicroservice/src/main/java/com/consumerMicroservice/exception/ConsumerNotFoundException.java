package com.consumerMicroservice.exception;

public class ConsumerNotFoundException extends Exception {

	private String message;

	public ConsumerNotFoundException() {
	}

	public ConsumerNotFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
