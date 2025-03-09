package org.example.db;

import org.example.api.ExchangeRate;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RegisterConstructorMapper(ExchangeRate.class)
public interface ExchangeRateDAO {

    @SqlUpdate("INSERT INTO exchange_rates (base_currency, target_currency, rate, date) VALUES (?, ?, ?, ?)")
    void insert(String baseCurrency, String targetCurrency, BigDecimal rate, LocalDate date);

    @SqlQuery("SELECT * FROM exchange_rates WHERE base_currency = ?")
    public List<ExchangeRate> getByBaseCurrency(@Bind("baseCurrency") String baseCurrency);

    @SqlUpdate("DELETE FROM exchange_rates WHERE date < CURRENT_DATE - INTERVAL '30 days'")
    void cleanupOldRates();
}