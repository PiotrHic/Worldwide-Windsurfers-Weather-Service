🌊 Use Cases (MVP)


📌 Overview

This document describes the core use cases for the Worldwide Windsurfer’s Weather Service MVP.

The system is designed to help users determine the best predefined windsurfing location worldwide for a given date, based on forecast data retrieved from the Weatherbit 16-Day Forecast API.

The system focuses exclusively on:

Date-based weather evaluation

Rule-based wind condition filtering

Deterministic selection logic

Reliable REST API interaction

No frontend or user interface is included in the MVP.


🌍 Location Evaluation Use Cases
1. Get Best Windsurfing Location

Description:
Determine the best windsurfing location for a given date based on forecasted wind speed criteria.

Endpoint:

GET /best-location?date=yyyy-mm-dd

Preconditions:

The date parameter is provided.

The date is within the 16-day forecast range.

Weatherbit API is available.

Postconditions:

The system returns:

The best qualifying location (if any)

Average forecasted temperature (°C)

Wind speed (m/s)

Business Rules:

Wind speed must be between 5 and 18 m/s (inclusive).

If multiple locations qualify, the one with the highest wind speed is selected.

If no location meets the criteria, no location is returned.


2. Handle No Suitable Location

Description:
Return a valid response when no location satisfies wind criteria.

Preconditions:

Date is valid.

All locations have wind speed outside the 5–18 m/s range.

Postconditions:

Response contains:

The requested date

No selected location

Informational message

Business Rules:

This is not treated as an error.

HTTP status: 200 OK.


3. Handle Invalid Date Format

Description:
Reject requests with invalid date format.

Preconditions:

Date parameter does not match yyyy-mm-dd.

Postconditions:

HTTP 400 (Bad Request) returned.

Error message describing correct format.


4. Handle Date Outside Forecast Range

Description:
Reject requests for dates beyond the 16-day forecast window.

Preconditions:

Date is syntactically valid.

Date is outside forecast range.

Postconditions:

HTTP 400 returned.

Clear explanation message.


5. Handle External API Failure

Description:
Handle unavailability or failure of the Weatherbit API.

Preconditions:

Weatherbit API request fails or times out.

Postconditions:

HTTP 500 (Internal Server Error).

Error message returned.

No partial or inconsistent data returned.


📍 Location Configuration Use Cases
1. Retrieve Embedded Locations

Description:
System loads predefined windsurfing locations at startup.

Preconditions:

Application starts successfully.

Postconditions:

Five locations are available in memory:

Jastarnia (Poland)

Bridgetown (Barbados)

Fortaleza (Brazil)

Pissouri (Cyprus)

Le Morne (Mauritius)

Business Rules:

Locations are embedded in application code/configuration.

No REST API is provided for managing locations.


🔒 Security & Access

No authentication required in MVP.

The API is publicly accessible.

No role-based access control is implemented.

⚙ System Constraints

Java 8 or higher.

Spring Boot backend.

REST-only architecture.

No database required.

Build system: Maven or Gradle.

README with build and run instructions is mandatory.


✅ Summary

The MVP supports the following core operations:

Date-based query for windsurfing conditions

Wind-speed-based location filtering (5–18 m/s)

Deterministic best-location selection

Graceful handling of edge cases and external failures

All use cases are:

Stateless

Deterministic

Based on external weather forecast data

Designed for easy future extension