package org.example.weatherforecastapiapp.domain.service;

import org.example.weatherforecastapiapp.api.dto.BestLocationResponse;
import org.example.weatherforecastapiapp.domain.mapper.ForecastDayMapper;
import org.example.weatherforecastapiapp.domain.model.ForecastDay;
import org.example.weatherforecastapiapp.domain.model.WindsurfingLocation;
import org.example.weatherforecastapiapp.domain.repository.LocationRepository;
import org.example.weatherforecastapiapp.external.weatherbit.client.WeatherbitClient;
import org.example.weatherforecastapiapp.external.weatherbit.dto.ForecastDayDto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class LocationEvaluationServiceImpl {

    private final LocationRepository locationRepository;
    private final WeatherbitClient weatherbitClient;

    public LocationEvaluationServiceImpl(LocationRepository locationRepository,
                                     WeatherbitClient weatherbitClient) {
        this.locationRepository = locationRepository;
        this.weatherbitClient = weatherbitClient;
    }

    public BestLocationResponse evaluateBestLocation(String dateStr) {

        LocalDate date;
        try {
            date = LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            return new BestLocationResponse(
                    null, null, null, null,
                    "Invalid date format. Required yyyy-MM-dd."
            );
        }

        LocalDate today = LocalDate.now();
        if (date.isBefore(today) || date.isAfter(today.plusDays(15))) {
            return new BestLocationResponse(
                    date, null, null, null,
                    "Date must be within 16-day forecast range."
            );
        }

        List<WindsurfingLocation> locations = locationRepository.findAll();

        List<ForecastDayWithLocation> forecastData = locations.stream()
                .map(loc -> {
                    List<ForecastDayDto> dtos = weatherbitClient.getDailyForecast(loc.latitude(), loc.longitude());

                    Optional<ForecastDayDto> dayDtoOpt = dtos.stream()
                            .filter(d -> d.validDate().equals(date))
                            .findFirst();

                    return dayDtoOpt
                            .map(dto -> new ForecastDayWithLocation(
                                    loc,
                                    ForecastDayMapper.toDomain(dto)
                            ))
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .toList();

        List<ForecastDayWithLocation> filtered = forecastData.stream()
                .filter(fd -> fd.forecastDay().windSpeed() >= 5 && fd.forecastDay().windSpeed() <= 18)
                .toList();

        Optional<ForecastDayWithLocation> best = filtered.stream()
                .max(Comparator.comparingDouble( fd -> fd.forecastDay().windSpeed()));


        if (best.isEmpty()) {
            return new BestLocationResponse(
                    date, null, null, null,
                    "No suitable location found for given date"
            );
        }

        ForecastDayWithLocation winner = best.get();
        return new BestLocationResponse(
                date,
                winner.location().name(),
                winner.forecastDay().temperature(),
                winner.forecastDay().windSpeed(),
                null
        );
    }

    private record ForecastDayWithLocation(
            WindsurfingLocation location,
            ForecastDay forecastDay
    ) {}
}
