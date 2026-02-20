📋 Worldwide Windsurfer’s Weather Service – Business Rules & Validation

📌 Overview

This document defines business rules and validation constraints for the MVP.

Ensures deterministic and transparent wind-based selection

Validated at both API (controller) and service layers

Focused on:

Date-based evaluation

Wind speed filtering

Location selection

No authentication required in MVP (public endpoint)

The application integrates with the Weatherbit 16-Day Forecast API as the external weather provider.

📅 Date Rules

Request Parameter Validation
Required Parameter

date query parameter is mandatory.

Format Validation

Must follow format: yyyy-MM-dd

Must be a valid calendar date.

Invalid format → 400 Bad Request.

Forecast Range Validation

Date must be within the next 16 days (inclusive).

If date is outside forecast range → 400 Bad Request.

🌬 Windsurfing Selection Rules
Wind Speed Constraint

A location qualifies only if:

wind_spd ≥ 5 m/s

wind_spd ≤ 18 m/s

If wind speed is:

Below 5 → rejected (insufficient wind)

Above 18 → rejected (unsafe wind conditions)

Temperature Handling

temp (average temperature in °C) is:

Returned in response

Not used as filtering criterion in MVP

Temperature must be present in external API response.

Missing temperature → treated as invalid forecast entry.

Multiple Qualified Locations

If more than one location satisfies wind constraints:

Select location with highest wind speed.

If wind speeds are equal:

Select the first location in predefined list (deterministic behavior).

No Qualified Location

If no location satisfies the wind constraint:

Return 200 OK

location = null

Include informational message

This scenario is not treated as an error.


📍 Location Configuration Rules

Exactly five predefined locations exist in MVP:

Jastarnia (Poland)

Bridgetown (Barbados)

Fortaleza (Brazil)

Pissouri (Cyprus)

Le Morne (Mauritius)

Locations:

Are embedded in application

Cannot be modified via API

Must contain:

Name

Country

Latitude

Longitude

Adding new locations requires code/configuration change.


🌐 External API Rules

Weatherbit Integration

Forecast data retrieved per location.

Required fields from API response:

valid_date

temp

wind_spd

External API Failure Handling

If:

API is unavailable

Timeout occurs

Unexpected response structure

Then:

Return 500 Internal Server Error

Do not return partial or inconsistent results


🔒 Security Rules


No authentication required (public endpoint).

No role-based access control in MVP.

API rate limiting not implemented in MVP.

API key for Weatherbit must be stored securely (not hardcoded).


⚠️ Validation Rules

Input Validation

Date must be present.

Date must match yyyy-MM-dd.

Date must be within forecast range.

Invalid input → 400 Bad Request.

Response Validation

Before returning a selected location:

Wind speed must satisfy 5–18 m/s rule.

Temperature must not be null.

Location must exist in predefined list.

Error Response Format

All validation and error responses follow a consistent structure:

{
"timestamp": "2026-02-10T12:34:56.789Z",
"status": 400,
"error": "Bad Request",
"message": "Date is outside the 16-day forecast range.",
"path": "/api/v1/best-location"
}

🧠 Service Layer Enforcement

The service layer is responsible for:

Applying wind filtering logic

Comparing qualifying locations

Guaranteeing deterministic selection

Preventing exposure of incomplete data

Controllers are responsible only for:

Validating input format

Returning appropriate HTTP status codes


🗝️ Key Business Guarantees

Deterministic selection algorithm

Transparent filtering criteria

No hidden scoring logic

No partial responses

No fallback to unsafe wind conditions

Clear distinction between:

Invalid request (400)

No result (200)

System failure (500)


✅ Summary

The MVP business rules ensure:

Strict wind speed qualification (5–18 m/s)

Clear and predictable selection behavior

Proper validation at API and service levels

Safe integration with external weather provider

Clean separation of responsibilities

The system remains:

Deterministic

Stateless

Extensible

Easy to test and reason about