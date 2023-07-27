package com.consumerMicroservice.exception;

public class NoContentFoundException extends Exception {

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
