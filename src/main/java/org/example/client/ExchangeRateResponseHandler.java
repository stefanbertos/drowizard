package org.example.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ExchangeRateResponseHandler implements HttpClientResponseHandler<ExchangeRateResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateResponseHandler.class);

    private final ObjectMapper objectMapper;

    public ExchangeRateResponseHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ExchangeRateResponse handleResponse(ClassicHttpResponse response) throws IOException, ParseException {
        if (response.getCode() == 200) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(jsonResponse, ExchangeRateResponse.class);
        } else {
            LOGGER.error("Failed to fetch exchange rates: {}", response.getCode());
            return null;
        }
    }
}
