package org.example.weatherforecastapiapp.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ForecastDayTest {

    @Test
    void testValidForecastDay() {
        LocalDate date = LocalDate.of(2026, 2, 21);
        ForecastDay fd = new ForecastDay(date, 25.5, 10.0);
        assertEquals(date, fd.date());
        assertEquals(25.5, fd.temperature());
        assertEquals(10.0, fd.windSpeed());
    }

    @Test
    void testNullDateThrowsException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> new ForecastDay(null, 25.5, 10.0));
        assertEquals("date cannot be null", ex.getMessage());
    }
}