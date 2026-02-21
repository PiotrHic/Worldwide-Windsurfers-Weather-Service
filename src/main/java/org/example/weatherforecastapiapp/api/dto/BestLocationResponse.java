package org.example.weatherforecastapiapp.api.dto;

import java.time.LocalDate;

public record BestLocationResponse(
        LocalDate date,
        String location,
        Double averageTemperature,
        Double windSpeed,
        String message
) {}
