package com.example.zaliczenie.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class ExchangeRateResponse {

    private Long id;
    private String currency;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private BigDecimal averageRate;
    private LocalDateTime requestedAt;

    public ExchangeRateResponse(Long id, String currency, LocalDate dateFrom,
                                LocalDate dateTo, BigDecimal averageRate,
                                LocalDateTime requestedAt) {
        this.id = id;
        this.currency = currency;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.averageRate = averageRate;
        this.requestedAt = requestedAt;
    }

    public Long getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public BigDecimal getAverageRate() {
        return averageRate;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }
}