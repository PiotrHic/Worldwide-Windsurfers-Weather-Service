package org.example.weatherforecastapiapp.exception;

import org.example.weatherforecastapiapp.api.controller.BestLocationController;
import org.example.weatherforecastapiapp.domain.service.LocationEvaluationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BestLocationController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LocationEvaluationServiceImpl service;

    @Test
    void shouldReturn400ForInvalidDate() throws Exception {
        when(service.evaluateBestLocation(anyString()))
                .thenThrow(new InvalidDateException("Invalid date format."));

        mockMvc.perform(get("/api/v1/best-location")
                        .param("date", "bad-date"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Invalid date format."))
                .andExpect(jsonPath("$.path").value("/api/v1/best-location"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturn400ForDateOutOfRange() throws Exception {
        when(service.evaluateBestLocation(anyString()))
                .thenThrow(new DateOutOfRangeException("Date must be within 16-day forecast range."));

        mockMvc.perform(get("/api/v1/best-location")
                        .param("date", "2020-01-01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Date must be within 16-day forecast range."));
    }

    @Test
    void shouldReturn500ForExternalServiceException() throws Exception {
        when(service.evaluateBestLocation(anyString()))
                .thenThrow(new ExternalServiceException("Weatherbit API down"));

        mockMvc.perform(get("/api/v1/best-location")
                        .param("date", "2026-03-01"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("Weatherbit API down"))
                .andExpect(jsonPath("$.path").value("/api/v1/best-location"));
    }

    @Test
    void shouldReturnGeneric500ForUnexpectedException() throws Exception {
        when(service.evaluateBestLocation(anyString()))
                .thenThrow(new RuntimeException("Unexpected failure"));

        mockMvc.perform(get("/api/v1/best-location")
                        .param("date", "2026-03-01"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message")
                        .value("An unexpected error occurred."));
    }
}