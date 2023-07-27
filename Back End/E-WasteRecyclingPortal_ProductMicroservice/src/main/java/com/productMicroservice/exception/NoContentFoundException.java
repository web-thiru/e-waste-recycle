package com.productMicroservice.exception;

public class NoContentFoundException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String message;

    public NoContentFoundException() {
        this.message = "";
    }

    public NoContentFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
