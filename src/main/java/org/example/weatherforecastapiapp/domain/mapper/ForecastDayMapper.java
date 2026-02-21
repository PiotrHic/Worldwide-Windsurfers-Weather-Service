package org.example.weatherforecastapiapp.domain.mapper;

import org.example.weatherforecastapiapp.domain.model.ForecastDay;
import org.example.weatherforecastapiapp.external.weatherbit.dto.ForecastDayDto;

public class ForecastDayMapper {

    public static ForecastDay toDomain(ForecastDayDto dto) {
        return new ForecastDay(
                dto.validDate(),
                dto.temperature(),
                dto.windSpeed()
        );
    }
}
