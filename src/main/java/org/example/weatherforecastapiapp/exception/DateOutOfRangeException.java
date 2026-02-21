package org.example.weatherforecastapiapp.exception;

public class DateOutOfRangeException extends RuntimeException {
    public DateOutOfRangeException(String message) {
        super(message);
    }
}