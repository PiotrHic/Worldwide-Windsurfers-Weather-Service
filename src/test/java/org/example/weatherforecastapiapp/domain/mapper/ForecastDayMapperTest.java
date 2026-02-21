package org.example.weatherforecastapiapp.domain.mapper;

import org.example.weatherforecastapiapp.domain.model.ForecastDay;
import org.example.weatherforecastapiapp.external.weatherbit.dto.ForecastDayDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ForecastDayMapperTest {

    @Test
    void testToDomain() {
        LocalDate date = LocalDate.of(2026, 2, 21);
        ForecastDayDto dto = new ForecastDayDto(date, 25.5, 10.0);

        ForecastDay domain = ForecastDayMapper.toDomain(dto);

        assertEquals(date, domain.date());
        assertEquals(25.5, domain.temperature());
        assertEquals(10.0, domain.windSpeed());
    }
}