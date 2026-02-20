🧪 Worldwide Windsurfer’s Weather Service – Testing Strategy

📌 Overview

This document defines the testing strategy for the MVP.

Ensures correctness of:

Wind selection business logic

REST API behavior

External API integration handling

Covers unit, integration, and optional end-to-end scenarios

Focused on:

Date validation

Wind speed filtering (5–18 m/s)

Deterministic location selection

Error handling

The system integrates with the external provider Weatherbit, which must be mocked during testing.


🧩 Unit Tests
Service Layer
Purpose:

Validate core business rules in isolation from external systems.

Target Class:

LocationEvaluationService

Example Test Scenarios:
Wind Filtering

Wind speed < 5 m/s → location rejected

Wind speed > 18 m/s → location rejected

Wind speed between 5–18 → location accepted

Selection Logic

Single qualifying location → selected

Multiple qualifying locations → highest wind speed selected

Equal wind speeds → first location in predefined list selected

No qualifying locations → result contains null location

Date Validation

Date outside 16-day range → exception thrown

Valid date → processing continues

Tools:

JUnit 5

Mockito (mock WeatherbitClient)

AssertJ (optional for readable assertions)

External Client Layer
Purpose:

Validate JSON mapping and response transformation logic.

Target Class:

WeatherbitClient

Example Scenarios:

Proper mapping of:

valid_date

temp

wind_spd

Handling malformed JSON

Handling missing fields

Handling HTTP error response

Tools:

Mockito

MockWebServer (optional)

Jackson object mapping tests


🌐 Integration Tests
Controller Layer
Purpose:

Validate full request → service → response flow.

Target:

BestLocationController

Example Scenarios:
Valid Request
GET /api/v1/best-location?date=2026-03-01

Returns 200 OK

Returns selected location

Contains temperature and wind speed

Invalid Date Format

date=01-03-2026

Returns 400 Bad Request

Date Outside Forecast Range

Date beyond 16 days

Returns 400 Bad Request

No Suitable Location

Returns 200 OK

location = null

Tools:

@SpringBootTest

MockMvc

Mocked WeatherbitClient bean


🌐 External API Integration Tests (Optional)
Purpose:

Validate integration with real Weatherbit API.

Notes:

Should be disabled in CI

Run manually or in staging environment

Requires valid API key

Validations:

Correct endpoint call

Valid forecast mapping

Proper error propagation

🏁 End-to-End Tests
Purpose:

Validate full system behavior from HTTP request to final selection.

Example Scenario:

Mock Weatherbit responses for all five locations

Submit request with valid date

Verify:

Correct location selected

Wind constraints applied

Temperature included

Submit request where:

All wind speeds invalid

Verify location = null

Tools:

SpringBootTest (full context)

RestAssured (optional)

Testcontainers (optional for future DB integration)


🔄 Continuous Testing

Run all unit and integration tests on every commit

Mock all external dependencies

Ensure 100% coverage of:

Wind constraint logic

Date validation

Selection algorithm

Fail build if business rule test fails


📊 Edge Case Testing

Special attention must be given to:

Wind speed exactly 5 m/s (boundary)

Wind speed exactly 18 m/s (boundary)

Empty forecast response

Partial API data

Duplicate wind speeds

Null temperature value

API timeout simulation


⚠️ Error Handling Tests

Weatherbit API returns 500 → system returns 500

Weatherbit API timeout → system returns 500

Malformed JSON → system returns 500

Unexpected null values → handled gracefully


💡 Performance Considerations (Basic MVP)

Ensure evaluation of five locations is fast

No blocking or long-running operations

Validate that API call timeouts are respected


🧠 Test Isolation Rules

Business logic tests must not depend on:

External API

Application context

Integration tests must:

Mock external API

Use predictable deterministic responses

No shared mutable state between tests


✅ Summary

The testing strategy ensures:

Strict validation of wind rules (5–18 m/s)

Deterministic and predictable selection

Proper API validation

Robust external API failure handling

High test coverage of business-critical logic

The system is:

Highly testable

Isolated from external dependencies

Safe for CI/CD environments

Ready for future expansion (caching, DB, auth)