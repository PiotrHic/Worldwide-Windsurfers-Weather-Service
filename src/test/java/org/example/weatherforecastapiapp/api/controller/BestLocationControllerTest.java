package org.example.weatherforecastapiapp.api.controller;

import org.example.weatherforecastapiapp.api.dto.BestLocationResponse;
import org.example.weatherforecastapiapp.domain.service.LocationEvaluationServiceImpl;
import org.example.weatherforecastapiapp.exception.ExternalServiceException;
import org.example.weatherforecastapiapp.exception.GlobalExceptionHandler;
import org.example.weatherforecastapiapp.exception.InvalidDateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BestLocationController.class)
@Import(GlobalExceptionHandler.class)
class BestLocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LocationEvaluationServiceImpl service;

    @Test
    void shouldReturn200WhenValidRequest() throws Exception {

        BestLocationResponse response = new BestLocationResponse(
                LocalDate.of(2026, 3, 1),
                "Jastarnia",
                18.5,
                12.0,
                null
        );

        when(service.evaluateBestLocation(eq("2026-03-01")))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/best-location")
                        .param("date", "2026-03-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2026-03-01"))
                .andExpect(jsonPath("$.location").value("Jastarnia"))
                .andExpect(jsonPath("$.averageTemperature").value(18.5))
                .andExpect(jsonPath("$.windSpeed").value(12.0))
                .andExpect(jsonPath("$.message").doesNotExist());
    }

    @Test
    void shouldDelegateToService() throws Exception {

        when(service.evaluateBestLocation("2026-03-01"))
                .thenReturn(new BestLocationResponse(
                        LocalDate.of(2026, 3, 1),
                        "Jastarnia",
                        20.0,
                        10.0,
                        null
                ));

        mockMvc.perform(get("/api/v1/best-location")
                        .param("date", "2026-03-01"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn400WhenInvalidDateExceptionThrown() throws Exception {

        when(service.evaluateBestLocation("bad-date"))
                .thenThrow(new InvalidDateException("Invalid date format."));

        mockMvc.perform(get("/api/v1/best-location")
                        .param("date", "bad-date"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Invalid date format."));
    }

    @Test
    void shouldReturn500WhenExternalServiceFails() throws Exception {

        when(service.evaluateBestLocation("2026-03-01"))
                .thenThrow(new ExternalServiceException("Weatherbit unavailable"));

        mockMvc.perform(get("/api/v1/best-location")
                        .param("date", "2026-03-01"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("Weatherbit unavailable"));
    }
}