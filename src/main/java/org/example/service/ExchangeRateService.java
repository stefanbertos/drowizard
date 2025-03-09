package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.example.client.ExchangeRateResponse;
import org.example.client.ExchangeRateResponseHandler;
import org.example.db.ExchangeRateDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class ExchangeRateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateService.class);
    private final ExchangeRateDAO exchangeRateDAO;
    private final HttpClient client;
    private final String apiUrl;
    private final ObjectMapper objectMapper;

    public ExchangeRateService(ExchangeRateDAO exchangeRateDAO, HttpClient client, String url, ObjectMapper objectMapper) {
        this.exchangeRateDAO = exchangeRateDAO;
        this.apiUrl = url;
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public void fetchAndStoreRates() {
        try {
            HttpGet request = new HttpGet(apiUrl);
            ExchangeRateResponseHandler responseHandler = new ExchangeRateResponseHandler(objectMapper);
            ExchangeRateResponse exchangeRateResponse = client.execute(request, responseHandler);
            String baseCurrency = exchangeRateResponse.base();
            LocalDate date = LocalDate.parse(exchangeRateResponse.date());

            Map<String, BigDecimal> rates = exchangeRateResponse.rates();
            for (Map.Entry<String, BigDecimal> entry : rates.entrySet()) {
                String targetCurrency = entry.getKey();
                BigDecimal rate = entry.getValue();
                LOGGER.info("Storing rate for baseCurrency: {}, targetCurrency: {}, rate: {}, date: {}", baseCurrency, targetCurrency, rate, date);
                exchangeRateDAO.insert(baseCurrency, targetCurrency, rate, date);
            }
        } catch (Exception e) {
            LOGGER.error("Error fetching exchange rates", e);
        }
    }
}
