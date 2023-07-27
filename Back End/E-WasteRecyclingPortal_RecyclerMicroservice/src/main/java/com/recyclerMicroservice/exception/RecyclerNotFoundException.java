package com.recyclerMicroservice.exception;

public class RecyclerNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;

	public RecyclerNotFoundException() {
	}

	public RecyclerNotFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
