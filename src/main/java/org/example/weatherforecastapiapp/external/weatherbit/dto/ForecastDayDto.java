package org.example.weatherforecastapiapp.external.weatherbit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record ForecastDayDto(
        @JsonProperty("valid_date") LocalDate validDate,
        @JsonProperty("temp") double temperature,
        @JsonProperty("wind_spd") double windSpeed
) {}
