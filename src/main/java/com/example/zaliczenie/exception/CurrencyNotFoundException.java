package com.example.zaliczenie.exception;

// gdy NBP zwroci 404 blad
public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(String message) {
        super(message);
    }
}

