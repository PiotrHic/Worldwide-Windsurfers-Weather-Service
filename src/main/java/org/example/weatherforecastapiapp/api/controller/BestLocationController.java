package org.example.weatherforecastapiapp.api.controller;

import org.example.weatherforecastapiapp.api.dto.BestLocationResponse;
import org.example.weatherforecastapiapp.domain.service.LocationEvaluationServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/v1")
public class BestLocationController {

    private final LocationEvaluationServiceImpl locationEvaluationService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public BestLocationController(LocationEvaluationServiceImpl locationEvaluationService) {
        this.locationEvaluationService = locationEvaluationService;
    }

    @GetMapping("/best-location")
    public BestLocationResponse getBestLocation(@RequestParam String date) {

        try {
            LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid date format. Expected yyyy-MM-dd.");
        }

        BestLocationResponse response = locationEvaluationService.evaluateBestLocation(date);

        if (response.message() != null && response.location() == null) {
            String msg = response.message();
            if (msg.contains("Invalid date format")) {
                throw new InvalidDateException(msg);
            } else if (msg.contains("Date must be within")) {
                throw new DateOutOfRangeException(msg);
            }
        }

        return response;
    }
}