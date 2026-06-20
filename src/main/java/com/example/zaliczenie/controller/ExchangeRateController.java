package com.example.zaliczenie.controller;

import com.example.zaliczenie.model.ExchangeRateResponse;
import com.example.zaliczenie.service.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

    @RestController
    @RequestMapping("/api/rates")
    @Tag(name = "Exchange Rates", description = "Średnie kursy walut NBP")
    public class ExchangeRateController {

        private final ExchangeRateService service;

        public ExchangeRateController(ExchangeRateService service) {
            this.service = service;
        }

        @GetMapping
        @Operation(summary = "Pobierz średni kurs waluty dla zakresu dat")
        public ResponseEntity<ExchangeRateResponse> getAverageRate(
                @Parameter(description = "Kod waluty, np. pln, eru", example = "USD")
                @RequestParam String currency,

                @Parameter(description = "Data początkowa (YYYY-MM-DD)", example = "2024-01-01")
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,

                @Parameter(description = "Data końcowa (YYYY-MM-DD)", example = "2024-01-31")
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
        ) {
            return ResponseEntity.ok(service.getAverageRate(currency, from, to));
        }
    }



