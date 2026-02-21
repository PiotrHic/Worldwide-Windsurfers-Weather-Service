package org.example.weatherforecastapiapp.domain.service;

import org.example.weatherforecastapiapp.api.dto.BestLocationResponse;
import org.example.weatherforecastapiapp.domain.model.WindsurfingLocation;
import org.example.weatherforecastapiapp.domain.repository.LocationRepository;
import org.example.weatherforecastapiapp.exception.DateOutOfRangeException;
import org.example.weatherforecastapiapp.exception.ExternalServiceException;
import org.example.weatherforecastapiapp.exception.InvalidDateException;
import org.example.weatherforecastapiapp.external.weatherbit.client.WeatherbitClient;
import org.example.weatherforecastapiapp.external.weatherbit.dto.ForecastDayDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LocationEvaluationServiceImplTest {

    private LocationRepository repository;
    private WeatherbitClient client;
    private LocationEvaluationServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(LocationRepository.class);
        client = mock(WeatherbitClient.class);
        service = new LocationEvaluationServiceImpl(repository, client);
    }

    @Test
    void testInvalidDateFormatThrows() {
        assertThrows( InvalidDateException.class,
                () -> service.evaluateBestLocation("2026/02/21"));
    }

    @Test
    void testDateOutOfRangeThrows() {
        LocalDate future = LocalDate.now().plusDays(20);
        assertThrows(DateOutOfRangeException.class,
                () -> service.evaluateBestLocation(future.toString()));
    }

    @Test
    void testNoQualifyingLocationReturnsNullLocation() {
        WindsurfingLocation loc = new WindsurfingLocation("Test", "Country", 0, 0);
        when(repository.findAll()).thenReturn(List.of(loc));

        ForecastDayDto dto = new ForecastDayDto(LocalDate.now(), 25.0, 3.0); // wiatr <5
        when(client.getDailyForecast(anyDouble(), anyDouble())).thenReturn(List.of(dto));

        BestLocationResponse resp = service.evaluateBestLocation(LocalDate.now().toString());
        assertNull(resp.location());
        assertEquals("No suitable location found for given date", resp.message());
    }

    @Test
    void testSelectsBestLocationByWind() {
        WindsurfingLocation loc1 = new WindsurfingLocation("A", "C1", 0, 0);
        WindsurfingLocation loc2 = new WindsurfingLocation("B", "C2", 0, 0);
        when(repository.findAll()).thenReturn(List.of(loc1, loc2));

        LocalDate date = LocalDate.now();
        ForecastDayDto dto1 = new ForecastDayDto(date, 20.0, 10.0);
        ForecastDayDto dto2 = new ForecastDayDto(date, 22.0, 12.0);

        when(client.getDailyForecast(anyDouble(), anyDouble()))
                .thenReturn(List.of(dto1))
                .thenReturn(List.of(dto2));

        BestLocationResponse resp = service.evaluateBestLocation(date.toString());
        assertEquals("B", resp.location());
        assertEquals(22.0, resp.averageTemperature());
        assertEquals(12.0, resp.windSpeed());
    }

    @Test
    void testExternalServiceException() {
        WindsurfingLocation loc = new WindsurfingLocation("Test", "Country", 0, 0);
        when(repository.findAll()).thenReturn(List.of(loc));
        when(client.getDailyForecast(anyDouble(), anyDouble())).thenThrow(RuntimeException.class);

        assertThrows(ExternalServiceException.class,
                () -> service.evaluateBestLocation(LocalDate.now().toString()));
    }
}