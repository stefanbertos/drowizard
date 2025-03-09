package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ApplicationConfiguration extends Configuration {
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @Valid
    @NotNull
    private String apiUrl;

    @JsonProperty("apiUrl")
    public String getApiUrl() {
        return apiUrl;
    }

    @JsonProperty("apiUrl")
    public void setApiUrl(String url) {
        this.apiUrl = url;
    }

    @Valid @NotNull
    HttpClientConfiguration httpClient;

    @JsonProperty("httpClient")
    public HttpClientConfiguration getHttpClient() {
        return httpClient;
    }

    @JsonProperty("httpClient")
    public void setHttpClient(HttpClientConfiguration httpClient) {
        this.httpClient = httpClient;
    }
}
