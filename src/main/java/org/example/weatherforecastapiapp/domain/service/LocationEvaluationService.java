package org.example.weatherforecastapiapp.domain.service;

import org.example.weatherforecastapiapp.api.dto.BestLocationResponse;

public interface LocationEvaluationService {

    BestLocationResponse evaluateBestLocation(String dateStr);
}
