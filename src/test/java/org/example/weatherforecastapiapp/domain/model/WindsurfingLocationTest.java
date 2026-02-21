package org.example.weatherforecastapiapp.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WindsurfingLocationTest {

    @Test
    void testValidLocation() {
        WindsurfingLocation loc = new WindsurfingLocation("Jastarnia", "Poland", 54.7,
                18.8);
        assertEquals("Jastarnia", loc.name());
        assertEquals("Poland", loc.country());
        assertEquals(54.7, loc.latitude());
        assertEquals(18.8, loc.longitude());
    }

    @Test
    void testNullNameThrowsException() {
        assertThrows(NullPointerException.class,
                () -> new WindsurfingLocation(null, "Poland", 54.7, 18.8));
    }

    @Test
    void testNullCountryThrowsException() {
        assertThrows(NullPointerException.class,
                () -> new WindsurfingLocation("Jastarnia", null, 54.7, 18.8));
    }

    @Test
    void testInvalidLatitude() {
        assertThrows(IllegalArgumentException.class,
                () -> new WindsurfingLocation("Jastarnia", "Poland", -91, 18.8));
        assertThrows(IllegalArgumentException.class,
                () -> new WindsurfingLocation("Jastarnia", "Poland", 91, 18.8));
    }

    @Test
    void testInvalidLongitude() {
        assertThrows(IllegalArgumentException.class,
                () -> new WindsurfingLocation("Jastarnia", "Poland", 54.7, -181));
        assertThrows(IllegalArgumentException.class,
                () -> new WindsurfingLocation("Jastarnia", "Poland", 54.7, 181));
    }
}