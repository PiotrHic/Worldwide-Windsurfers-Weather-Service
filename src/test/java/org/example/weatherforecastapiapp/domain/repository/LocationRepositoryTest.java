package org.example.weatherforecastapiapp.domain.repository;

import org.example.weatherforecastapiapp.domain.model.WindsurfingLocation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocationRepositoryTest {

    private final LocationRepository repository = new LocationRepository();

    @Test
    void testFindAllReturnsFiveLocations() {
        List<WindsurfingLocation> locations = repository.findAll();
        assertEquals(5, locations.size());

        assertEquals("Jastarnia", locations.get(0).name());
        assertEquals("Bridgetown", locations.get(1).name());
        assertEquals("Fortaleza", locations.get(2).name());
        assertEquals("Pissouri", locations.get(3).name());
        assertEquals("Le Morne", locations.get(4).name());
    }

    @Test
    void testReturnedListIsImmutable() {
        List<WindsurfingLocation> locations = repository.findAll();
        assertThrows(UnsupportedOperationException.class, () -> locations.add(
                new WindsurfingLocation("Test", "Country", 0, 0)
        ));
    }
}