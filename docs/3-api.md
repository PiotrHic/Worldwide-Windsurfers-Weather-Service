🌊 Worldwide Windsurfer’s Weather Service – API Documentation (MVP)

📌 Overview

This document describes the REST API endpoints for the Worldwide Windsurfer’s Weather Service MVP.

Public REST API (no authentication required in MVP)

Returns JSON responses

Uses Weatherbit 16-Day Forecast API as data source

Stateless Spring Boot backend

Consistent error response format

Only predefined windsurfing locations are supported

🌍 Windsurfing Location API

1. Get Best Windsurfing Location

GET /api/v1/best-location

Query Parameters:
Parameter	Type	Required	Format	Description
date	String	Yes	yyyy-mm-dd	Date within 16-day forecast range
Example Request:
GET /api/v1/best-location?date=2026-02-25
✅ 200 OK – Location Found
{
"date": "2026-02-25",
"location": "Le Morne (Mauritius)",
"averageTemperature": 27.3,
"windSpeed": 8.1
}
✅ 200 OK – No Suitable Location

Returned when none of the predefined locations meet the wind criteria (5–18 m/s).

{
"date": "2026-02-25",
"location": null,
"message": "No suitable windsurfing location for the given date"
}
❌ 400 Bad Request – Invalid Date Format

Returned when date does not match yyyy-mm-dd.

{
"timestamp": "2026-02-10T12:34:56.789Z",
"status": 400,
"error": "Bad Request",
"message": "Invalid date format. Expected yyyy-mm-dd.",
"path": "/api/v1/best-location"
}
❌ 400 Bad Request – Date Outside Forecast Range

Returned when the requested date is not within the 16-day forecast window.

{
"timestamp": "2026-02-10T12:34:56.789Z",
"status": 400,
"error": "Bad Request",
"message": "Date is outside the 16-day forecast range.",
"path": "/api/v1/best-location"
}
❌ 500 Internal Server Error – External API Failure

Returned when the Weatherbit API is unavailable or returns an unexpected error.

{
"timestamp": "2026-02-10T12:34:56.789Z",
"status": 500,
"error": "Internal Server Error",
"message": "Unable to retrieve forecast data from external weather service.",
"path": "/api/v1/best-location"
}
📍 Predefined Windsurfing Locations

The system evaluates exactly the following embedded locations:

Jastarnia (Poland)

Bridgetown (Barbados)

Fortaleza (Brazil)

Pissouri (Cyprus)

Le Morne (Mauritius)

Each location contains:

Name

Country

Latitude

Longitude

No API is provided for creating, editing, or deleting locations in MVP.


⚙️ Business Rules Applied by API

Wind speed must be between 5 and 18 m/s (inclusive).

Wind speed is taken from wind_spd field of Weatherbit forecast.

Temperature is taken from temp field (°C).

If multiple locations qualify:

The one with the highest wind speed is selected.

If none qualify:

The response contains location: null.


⚠️ Error Response Format

All error responses follow a consistent JSON structure:

{
"timestamp": "2026-02-10T12:34:56.789Z",
"status": 400,
"error": "Bad Request",
"message": "Error description",
"path": "/api/v1/best-location"
}

🔒 Security Rules

No authentication required in MVP.

Public REST endpoint.

No role-based access control.

Invalid input → 400 Bad Request.

External API failures → 500 Internal Server Error.


✅ Summary

This API documentation defines the core MVP endpoint:

Date-based evaluation of windsurfing conditions

Deterministic selection logic

Embedded predefined locations

Integration with Weatherbit 16-day forecast API

Consistent JSON responses and error handling

The API is:

Stateless

Simple

Extensible

Ready for integration with frontend, mobile app, or automated tests