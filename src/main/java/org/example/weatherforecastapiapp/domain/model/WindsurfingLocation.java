package org.example.weatherforecastapiapp.domain.model;

import java.util.Objects;

public record WindsurfingLocation(
        String name,
        String country,
        double latitude,
        double longitude
) {
    public WindsurfingLocation {
        Objects.requireNonNull(name);
        Objects.requireNonNull(country);

        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Invalid latitude");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Invalid longitude");
        }
    }
}
