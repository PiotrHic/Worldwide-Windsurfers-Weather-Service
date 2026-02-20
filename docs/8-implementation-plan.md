🚀 Worldwide Windsurfer’s Weather Service – Implementation Plan

📌 Overview

This document defines the step-by-step implementation plan for the MVP.

Covers coding order, layer separation, and external API integration

Ensures clean architecture, deterministic business logic, and stateless behavior

Focused on:

Date-based evaluation

Wind speed filtering (5–18 m/s)

Best-location selection

Integration with Weatherbit 16-Day Forecast API


🛠️ Step 1 – Project Setup

Create Spring Boot project:

Java 8+

Dependencies:

Spring Web

Validation

Lombok (optional)

Spring Boot Test

Jackson

Configure:

application.yml

Weatherbit base URL

API key (environment variable)

Timeout settings

Maven or Gradle build structure

Basic package structure

🛠️ Step 2 – Define Package Structure
com.example.windsurfservice
├── api
│   ├── controller
│   └── dto
├── domain
│   ├── model
│   └── service
├── external
│   └── weatherbit
│       ├── client
│       └── dto
├── config
├── exception
└── common

Ensure strict separation between:

API layer

Domain logic

External API models


🛠️ Step 3 – Domain Model

Implement WindsurfingLocation

name

country

latitude

longitude

Implement ForecastDay

date

temperature

windSpeed

Implement in-memory LocationRepository

Store exactly five predefined locations:

Jastarnia

Bridgetown

Fortaleza

Pissouri

Le Morne

Designed for easy extension


🛠️ Step 4 – External API Client

Implement WeatherbitClient

Use RestTemplate or WebClient

Call:

/forecast/daily?lat={lat}&lon={lon}&key={API_KEY}

Create DTOs:

WeatherbitForecastResponse

ForecastDayDto

Map:

valid_date

temp

wind_spd

Handle:

HTTP errors

Timeouts

Malformed responses


🛠️ Step 5 – Service Layer

Implement LocationEvaluationService

Responsibilities:

Validate date (range + format)

Retrieve all predefined locations

Fetch forecast per location

Filter:

Wind speed between 5–18 m/s

Select:

Highest wind speed

Deterministic tie-breaker

Return BestLocationResponse

Ensure:

No business logic in controller

No external API calls outside client


🛠️ Step 6 – Controller Layer

Implement BestLocationController

Endpoint:

GET /api/v1/best-location?date=yyyy-MM-dd

Responsibilities:

Validate input format

Delegate to service

Return JSON response

Map exceptions to HTTP codes


🛠️ Step 7 – Validation & Error Handling

Implement:

InvalidDateException

DateOutOfRangeException

ExternalServiceException

Create GlobalExceptionHandler

Return consistent JSON error structure

Map:

400 → validation issues

500 → external failures

Ensure:

No stack traces leaked

Clear error messages


🛠️ Step 8 – Testing

Unit Tests:

Wind filtering logic

Boundary values (5 and 18 m/s)

Selection algorithm

Date validation

Integration Tests:

Controller flow using MockMvc

Mock WeatherbitClient

Edge Case Tests:

No qualifying locations

API timeout

Equal wind speeds

Optional:

Real Weatherbit integration test (manual)


🛠️ Step 9 – API Documentation

Document endpoint:

Request format

Response examples

Error examples

Optional:

Add OpenAPI / Swagger


🛠️ Step 10 – Deployment

Prepare deployment configuration:

Environment variable: WEATHERBIT_API_KEY

JVM memory settings

Timeout configuration

Deploy to:

Render / Railway / Docker container

Validate:

Endpoint accessibility

Proper error handling

External API connectivity


📌 Key Notes

Follow layered architecture:

Controller → Service → External Client

No persistence layer in MVP

Controllers contain zero business logic

Services enforce all wind rules

External client isolated from domain logic

API must remain deterministic


⚙️ Recommended Order of Implementation

Project setup

Domain models

External client

Service logic

Controller

Exception handling

Unit tests

Integration tests

Documentation

Deployment


✅ MVP Completion Criteria

REST endpoint /api/v1/best-location

Wind filtering strictly 5–18 m/s

Deterministic highest-wind selection

Proper validation and error handling

Weatherbit integration working

Unit and integration tests passing

Application deployable with environment-based API key