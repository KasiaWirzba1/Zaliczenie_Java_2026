package com.example.zaliczenie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CurrencyNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(CurrencyNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorBody(ex.getMessage(), 404));
    }



    @ExceptionHandler(NbpApiException.class)
    public ResponseEntity<Map<String, Object>> handleNbpError(NbpApiException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(errorBody(ex.getMessage(), ex.getStatusCode()));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorBody(ex.getMessage(), 400));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {

        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorBody("Wewnętrzny błąd serwera: " + ex.getMessage(), 500));
    }



    private Map<String, Object> errorBody(String message, int status) {
        return Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", status,
                "error", message
        );
    }
}


