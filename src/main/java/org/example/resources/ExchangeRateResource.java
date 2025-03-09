package org.example.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.api.ExchangeRate;
import org.example.db.ExchangeRateDAO;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/exchange-rates")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Exchange Rates", description = "Handles currency exchange rates")
public class ExchangeRateResource {
    private final ExchangeRateDAO exchangeRateDAO;

    public ExchangeRateResource(ExchangeRateDAO exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    @GET
    @Operation(summary = "Get all exchange rates", description = "Returns a list of exchange rates")
    public List<ExchangeRate> getAllRates() {
        return exchangeRateDAO.getByBaseCurrency("EUR"); // Default to EUR
    }
}
