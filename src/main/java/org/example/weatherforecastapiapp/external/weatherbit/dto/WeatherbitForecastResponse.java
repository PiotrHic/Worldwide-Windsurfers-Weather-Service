package org.example.weatherforecastapiapp.external.weatherbit.dto;

import java.util.List;

public record WeatherbitForecastResponse(
        List<ForecastDayDto> data
) {}
