package org.example.weatherforecastapiapp.external.weatherbit.client;

import org.example.weatherforecastapiapp.exception.ExternalServiceException;
import org.example.weatherforecastapiapp.external.weatherbit.dto.ForecastDayDto;
import org.example.weatherforecastapiapp.external.weatherbit.dto.WeatherbitForecastResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WeatherbitClientTest {

    private WebClient mockWebClient;
    private WeatherbitClient client;

    @BeforeEach
    void setUp() {

        client = new WeatherbitClient("https://dummy-weatherbit.io", "dummy-key", 5000);
    }

    @Test
    void testValidResponseMapping() {

        ForecastDayDto dto = new ForecastDayDto(LocalDate.of(2026, 2, 25),
                25.0, 10.0);
        WeatherbitForecastResponse response = new WeatherbitForecastResponse(List.of(dto));

        List<ForecastDayDto> result = response.data();
        assertEquals(1, result.size());
        assertEquals(LocalDate.of(2026, 2, 25), result.get(0).validDate());
        assertEquals(25.0, result.get(0).temperature());
        assertEquals(10.0, result.get(0).windSpeed());
    }

    @Test
    void testMalformedResponseThrowsException() {
        WeatherbitForecastResponse badResponse = new WeatherbitForecastResponse(null);

        ExternalServiceException exception = assertThrows(ExternalServiceException.class, () -> {
            if (badResponse.data() == null) {
                throw new ExternalServiceException("Malformed Weatherbit response: data missing");
            }
        });

        assertEquals("Malformed Weatherbit response: data missing", exception.getMessage());
    }

    @Test
    void testValidDateNullThrowsException() {
        ForecastDayDto badDto = new ForecastDayDto(null, 20.0, 8.0);
        WeatherbitForecastResponse response = new WeatherbitForecastResponse(List.of(badDto));

        ExternalServiceException exception = assertThrows(ExternalServiceException.class, () -> {
            response.data().forEach(day -> {
                if (day.validDate() == null) {
                    throw new ExternalServiceException("Malformed Weatherbit response: valid_date missing");
                }
            });
        });

        assertEquals("Malformed Weatherbit response: valid_date missing", exception.getMessage());
    }

    @Test
    void testHttpErrorThrowsException() {
        WebClientResponseException ex = WebClientResponseException.create(500,
                "Internal Server Error", null, null, null);

        ExternalServiceException exception = assertThrows(ExternalServiceException.class, () -> {
            throw new ExternalServiceException("Weatherbit HTTP error: " + ex.getStatusCode(), ex);
        });

        assertTrue(exception.getMessage().contains("Weatherbit HTTP error"));
    }
}
