package com.example.zaliczenie.exception;

// tym raem gdy NBP ma inny błąd (400, 500 i tego typu)
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

