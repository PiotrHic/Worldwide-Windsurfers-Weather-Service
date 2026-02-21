package org.example.weatherforecastapiapp.external.weatherbit.client;

import org.example.weatherforecastapiapp.exception.ExternalServiceException;
import org.example.weatherforecastapiapp.external.weatherbit.dto.ForecastDayDto;
import org.example.weatherforecastapiapp.external.weatherbit.dto.WeatherbitForecastResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
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

    public List<ForecastDayDto> getDailyForecast(double lat, double lon) {
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
                    .onStatus(HttpStatusCode::isError,
                            clientResponse -> Mono.error(
                                    new ExternalServiceException("Weatherbit HTTP error: " + clientResponse.statusCode())
                            )
                    )
                    .bodyToMono(WeatherbitForecastResponse.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            if (response == null || response.data() == null) {
                throw new ExternalServiceException("Malformed Weatherbit response: data missing");
            }

            for (ForecastDayDto day : response.data()) {
                if (day.validDate() == null) {
                    throw new ExternalServiceException("Malformed Weatherbit response: valid_date missing");
                }
            }

            return response.data();
        } catch (WebClientResponseException e) {
            throw new ExternalServiceException("Weatherbit HTTP error: " + e.getStatusCode(), e);
        } catch (Exception e) {
            throw new ExternalServiceException("Weatherbit API request failed", e);
        }
    }
}
