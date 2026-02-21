package org.example.weatherforecastapiapp.config;

import org.example.weatherforecastapiapp.external.weatherbit.client.WeatherbitClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${weatherbit.base-url}")
    private String baseUrl;

    @Value("${weatherbit.api-key}")
    private String apiKey;

    @Value("${weatherbit.timeout-ms}")
    private int timeoutMs;

    @Bean
    public WeatherbitClient weatherbitClient() {
        return new WeatherbitClient(baseUrl, apiKey, timeoutMs);
    }
}
