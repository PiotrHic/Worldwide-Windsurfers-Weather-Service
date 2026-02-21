🌊 Worldwide Windsurfer’s Weather Service

A deterministic Spring Boot REST API that selects the best global windsurfing location based on safe wind conditions.
Stateless • Layered Architecture • Fully Testable • Production-Ready Deployment

🚀 Live API

Deployed on Render

GET https://worldwide-windsurfers-weather-service.onrender.com/api/v1/best-location?date=2026-02-27

Try it directly in your browser or via Postman.

✨ Project Highlights

✔ Clean layered architecture
✔ Deterministic wind-based selection logic
✔ Strict input validation
✔ Centralized error handling
✔ Fully unit & integration tested
✔ No database (stateless MVP)
✔ Easily extensible

🎯 What Problem Does It Solve?

Windsurfers need reliable wind conditions — not too weak, not too dangerous.

This service:

Fetches 16-day forecast data

Evaluates predefined global windsurfing spots

Applies strict safety constraints

Returns the best qualifying destination

Simple. Predictable. Transparent.


🌍 Evaluated Locations (MVP)


The system evaluates exactly five embedded locations:

Jastarnia – Poland

Bridgetown – Barbados

Fortaleza – Brazil

Pissouri – Cyprus

Le Morne – Mauritius

Locations are embedded in configuration and can be easily replaced with a database layer in future versions.

📡 REST API

Endpoint
GET /api/v1/best-location?date=yyyy-MM-dd
Query Parameter
Parameter	Required	Format
date	Yes	yyyy-MM-dd
✅ Example – Location Found

{
"date": "2026-02-27",
"location": "Le Morne (Mauritius)",
"averageTemperature": 27.3,
"windSpeed": 8.1
}
🌬 Example – No Suitable Conditions

{
"date": "2026-02-27",
"location": null,
"message": "No suitable windsurfing location for the given date"
}
❌ Error Response Format

{
"timestamp": "2026-02-10T12:34:56.789Z",
"status": 400,
"error": "Bad Request",
"message": "Date is outside the 16-day forecast range.",
"path": "/api/v1/best-location"
}
⚡ Business Rules

📅 Date Validation

Parameter is mandatory

Format must be yyyy-MM-dd

Must be within the next 16 days

Invalid input → 400 Bad Request

🌬 Wind Constraints

A location qualifies only if:

5 m/s ≤ wind speed ≤ 18 m/s
Condition	Result
< 5 m/s	Rejected (insufficient wind)
> 18 m/s	Rejected (unsafe conditions)
5–18 m/s	Accepted
Selection Logic

Highest wind speed wins

Equal wind speeds → first predefined location

No qualifying location → 200 OK with location = null

No hidden scoring. No fallback logic. Fully deterministic.

🏗 Architecture

Architectural Style

Monolithic backend

Layered architecture

Stateless REST API

Synchronous external API integration

🔹 Application Layers

Controller

Validates input

Handles HTTP

No business logic

Service

Applies wind filtering

Selects best location

Fully unit-testable

External Client

Integrates with Weatherbit API

Maps external DTOs

Handles timeouts & failures

Domain

Core business models

Encapsulated selection logic

Global Exception Handler

Consistent error responses

Clear status code distinction (400 / 200 / 500)

🔄 Request Flow

Client
↓
Controller
↓
Service (Selection Logic)
↓
WeatherbitClient
↓
Weather API

🧪 Testing Strategy
Unit Tests

Wind boundary tests (5 m/s & 18 m/s)

Deterministic selection

Equal wind speed handling

Date validation

Integration Tests

Full controller → service flow

Mocked external API

Validation scenarios

Error Handling Tests

API timeout

HTTP 500 from provider

Malformed responses

Missing forecast fields

External API is always mocked in CI.
Business logic is completely isolated and testable.


🛠 Tech Stack

Java 8+

Spring Boot

REST API

Maven / Gradle

JUnit 5

Mockito

Weatherbit 16-Day Forecast API


🔐 Security (MVP)

Public endpoint

No authentication

No role-based access

API key stored securely

No database (stateless design)

🚀 Running Locally

Build
mvn clean install

Run
mvn spring-boot:run

🔮 Future Enhancements

Redis caching layer

JPA persistence for locations

Advanced scoring (temperature comfort, humidity, wave height)

Spring Security authentication

Multi-day ranking endpoint

Reactive WebClient integration


🏁 Final Notes

This project demonstrates:

Clean separation of concerns

Deterministic business logic

Robust validation

Centralized error handling

Test-driven design principles

Production deployment readiness


🌊 Built for clarity, predictability, and extensibility.