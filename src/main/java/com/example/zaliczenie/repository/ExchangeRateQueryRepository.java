package com.example.zaliczenie.repository;

import com.example.zaliczenie.model.ExchangeRateQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateQueryRepository extends JpaRepository<ExchangeRateQuery, Long> {
    // Spring automatycznie generuje wszystkie metody (save, findAll, itp.)

}



