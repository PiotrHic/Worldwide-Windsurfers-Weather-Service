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

    public BestLocationController(LocationEvaluationServiceImpl locationEvaluationService) {
        this.locationEvaluationService = locationEvaluationService;
    }

    @GetMapping("/best-location")
    public BestLocationResponse getBestLocation(@RequestParam String date) {
        return locationEvaluationService.evaluateBestLocation(date);
    }
}