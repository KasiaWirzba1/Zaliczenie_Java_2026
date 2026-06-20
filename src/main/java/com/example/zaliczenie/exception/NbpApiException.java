package com.example.zaliczenie.exception;

public class NbpApiException extends RuntimeException {

    private final int statusCode;

    public NbpApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}