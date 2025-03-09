package org.example.api;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExchangeRate(
        int id,
        String baseCurrency,
        String targetCurrency,
        BigDecimal rate,
        LocalDate date
) {
}