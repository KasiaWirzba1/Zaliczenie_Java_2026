package com.example.zaliczenie.service;

import com.example.zaliczenie.exception.CurrencyNotFoundException;
import com.example.zaliczenie.exception.NbpApiException;
import com.example.zaliczenie.model.ExchangeRateQuery;
import com.example.zaliczenie.model.ExchangeRateResponse;
import com.example.zaliczenie.model.NbpResponse;
import com.example.zaliczenie.repository.ExchangeRateQueryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExchangeRateService {



    private static final String NBP_URL =
            "http://api.nbp.pl/api/exchangerates/rates/A/{currency}/{from}/{to}/?format=json";


    private final RestTemplate restTemplate;
    private final ExchangeRateQueryRepository repository;


    public ExchangeRateService(ExchangeRateQueryRepository repository) {
        this.restTemplate = new RestTemplate();
        this.repository = repository;
    }

    public ExchangeRateResponse getAverageRate(String currency, LocalDate from, LocalDate to) {


        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Data 'od' nie może być późniejsza niż data 'do'");
        }
        NbpResponse nbpResponse = callNbpApi(currency.toUpperCase(), from, to);

        // Obliczam tutaj średnią  średniej z wszystkich kursów w przedziale
        List<BigDecimal> rates = nbpResponse.getRates().stream()
                .map(NbpResponse.Rate::getMid)
                .toList();

        BigDecimal average = rates.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(rates.size()), 4, RoundingMode.HALF_UP);

        // Zapis do bazy
        ExchangeRateQuery query = new ExchangeRateQuery();
        query.setCurrency(currency.toUpperCase());
        query.setDateFrom(from);
        query.setDateTo(to);
        query.setAverageRate(average);
        query.setRequestedAt(LocalDateTime.now());

        ExchangeRateQuery saved = repository.save(query);

        // Zwrócenie odpowiedzi
        return new ExchangeRateResponse(
                saved.getId(),
                saved.getCurrency(),
                saved.getDateFrom(),
                saved.getDateTo(),
                saved.getAverageRate(),
                saved.getRequestedAt()
        );
    }

    private NbpResponse callNbpApi(String currency, LocalDate from, LocalDate to) {
        try {
            ResponseEntity<NbpResponse> response = restTemplate.getForEntity(
                    NBP_URL,
                    NbpResponse.class,
                    currency, from.toString(), to.toString()
            );
            return response.getBody();

        } catch (HttpClientErrorException.NotFound e) {
            throw new CurrencyNotFoundException(
                    "Nie znaleziono kursów dla waluty '" + currency + "' w zakresie " + from + " - " + to
            );
        } catch (HttpClientErrorException e) {
            throw new NbpApiException(
                    "Błąd zapytania do NBP: " + e.getStatusCode(),
                    e.getStatusCode().value()
            );
        } catch (HttpServerErrorException e) {
            throw new NbpApiException(
                    "Błąd serwera NBP: " + e.getStatusCode(),
                    e.getStatusCode().value()
            );
        } catch (Exception e) {
            throw new NbpApiException("Nie można połączyć się z API NBP: " + e.getMessage(), 503);
        }
    }
}














