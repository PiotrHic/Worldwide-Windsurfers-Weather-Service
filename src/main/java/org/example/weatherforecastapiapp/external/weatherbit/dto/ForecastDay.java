package org.example.weatherforecastapiapp.external.weatherbit.dto;

import java.time.LocalDate;

public record ForecastDay(
        LocalDate date,
        double temperature,
        double windSpeed
) {}
