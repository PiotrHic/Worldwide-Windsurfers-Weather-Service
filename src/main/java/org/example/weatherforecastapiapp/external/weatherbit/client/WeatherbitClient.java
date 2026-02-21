package org.example.weatherforecastapiapp.external.weatherbit.client;

import org.example.weatherforecastapiapp.external.weatherbit.dto.ForecastDayDto;
import org.example.weatherforecastapiapp.external.weatherbit.dto.WeatherbitForecastResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.List;

public class WeatherbitClient {

    private final WebClient webClient;
    private final String apiKey;

    public WeatherbitClient(String baseUrl, String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        this.apiKey = apiKey;
    }

    public List<ForecastDayDto> getDailyForecast( double lat, double lon) {
        try {
            WeatherbitForecastResponse response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/forecast/daily")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("key", apiKey)
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(WeatherbitForecastResponse.class)
                    .timeout(Duration.ofSeconds(5))
                    .block(); // blocking for MVP

            if (response == null || response.data() == null) {
                throw new RuntimeException("Malformed Weatherbit response");
            }

            return response.data();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Weatherbit API returned error: " + e.getStatusCode(), e);
        } catch (Exception e) {
            throw new RuntimeException("Weatherbit API request failed", e);
        }
    }
}
