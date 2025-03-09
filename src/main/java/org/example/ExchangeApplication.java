package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.apache.hc.client5.http.classic.HttpClient;
import org.example.db.ExchangeRateDAO;
import org.example.resources.ExchangeRateResource;
import org.example.service.ExchangeRateService;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;

public class ExchangeApplication extends io.dropwizard.core.Application<ApplicationConfiguration> {
    public static void main(String[] args) throws Exception {
        new ExchangeApplication().run(args);
    }

    @Override
    public String getName() {
        return "exchange-rate";
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<ApplicationConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ApplicationConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(ApplicationConfiguration configuration, Environment environment) {

        // Flyway Migration
        Flyway flyway = Flyway.configure()
                .dataSource(configuration.getDataSourceFactory().getUrl(), configuration.getDataSourceFactory().getUser(),
                        configuration.getDataSourceFactory().getPassword())
                .load();
        flyway.migrate();

        // Database setup
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        ExchangeRateDAO exchangeRateDAO = jdbi.onDemand(ExchangeRateDAO.class);

        final HttpClient httpClient = new HttpClientBuilder(environment).using(configuration.getHttpClient())
                .build(getName());

        // Get Dropwizard's ObjectMapper
        ObjectMapper objectMapper = environment.getObjectMapper();

        // Schedule data fetching
        ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateDAO, httpClient, configuration.getApiUrl(), objectMapper);
        exchangeRateService.fetchAndStoreRates();

        // Register resources
        environment.jersey().register(new ExchangeRateResource(exchangeRateDAO));
    }
}