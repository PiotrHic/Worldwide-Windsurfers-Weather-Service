package org.example.weatherforecastapiapp.domain.repository;

import org.example.weatherforecastapiapp.domain.model.WindsurfingLocation;

import java.util.List;

public class LocationRepository {

    private static final List<WindsurfingLocation> LOCATIONS = List.of(
            new WindsurfingLocation("Jastarnia", "Poland", 54.6963, 18.6780),
            new WindsurfingLocation("Bridgetown", "Barbados", 13.0975, -59.6167),
            new WindsurfingLocation("Fortaleza", "Brazil", -3.7319, -38.5267),
            new WindsurfingLocation("Pissouri", "Cyprus", 34.6694, 32.7013),
            new WindsurfingLocation("Le Morne", "Mauritius", -20.4440, 57.3260)
    );

    public List<WindsurfingLocation> findAll() {
        return LOCATIONS;
    }
}
