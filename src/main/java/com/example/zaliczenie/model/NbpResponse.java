package com.example.zaliczenie.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class NbpResponse {

    private String code;
    private String currency;
    private List<Rate> rates;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rate {
        private String effectiveDate;
        private BigDecimal mid; // średni kurs

        public String getEffectiveDate() {
            return effectiveDate;
        }

        public void setEffectiveDate(String effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        public BigDecimal getMid() {
            return mid;
        }

        public void setMid(BigDecimal mid) {
            this.mid = mid;
        }
    }
}