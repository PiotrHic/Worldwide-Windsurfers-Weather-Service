package org.example.weatherforecastapiapp.external.weatherbit.dto;

import java.time.LocalDate;
import java.util.Objects;

public record ForecastDay(
        LocalDate date,
        double temperature,
        double windSpeed
) {

    public ForecastDay {
        Objects.requireNonNull(date, "date cannot be null");
    }
}
