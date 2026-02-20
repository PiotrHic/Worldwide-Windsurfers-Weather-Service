🏗️ Worldwide Windsurfer’s Weather Service – Architecture (MVP)

📌 Overview

The application is built as a Spring Boot backend following layered architecture principles.

Exposes a public REST API

Integrates with external weather provider: Weatherbit

Evaluates predefined windsurfing locations

Applies deterministic wind-based selection logic

Stateless and lightweight (no database in MVP)

The system focuses on clean separation of concerns, testability, and extensibility.


⚙️ Architectural Style

Monolithic backend (single deployable service)

Layered architecture

Stateless REST API

No persistence layer (MVP)

Synchronous external API integration

Clear separation between:

Domain logic

External API integration

API layer


🏛️ Application Layers

1️⃣ Controller Layer
Responsibility:

Handle HTTP requests and responses

Validate input parameters

Delegate logic to service layer

Return structured JSON responses

Key Component:

BestLocationController

Rules:

Controllers must not contain business logic

Controllers must not call external APIs directly

Only coordinate request → service → response


2️⃣ Service Layer
Responsibility:

Implement business rules

Coordinate calls to external weather service

Filter and compare locations

Select best windsurfing destination

Key Component:

LocationEvaluationService

Notes:

Annotated with @Service

Contains selection algorithm:

Wind speed must be between 5–18 m/s

Highest wind speed wins

Fully unit-testable


3️⃣ External API Client Layer
Responsibility:

Communicate with Weatherbit API

Map external JSON responses to internal DTOs

Handle API errors and timeouts

Key Components:

WeatherbitClient

WeatherbitForecastResponse

ForecastDayDto

Rules:

No business logic in client

Responsible only for HTTP communication

Isolated from domain layer


4️⃣ Domain Layer
Responsibility:

Represent core business concepts

Encapsulate selection logic criteria

Key Models:

WindsurfingLocation

ForecastDay

Notes:

Independent of REST and external DTOs

Contains filtering rules (wind constraints)

No persistence annotations in MVP


5️⃣ Location Configuration Layer
Responsibility:

Provide embedded windsurfing locations

Allow easy future extension

Key Component:

LocationRepository (in-memory)

Design:

Stores predefined list:

Jastarnia

Bridgetown

Fortaleza

Pissouri

Le Morne

Could later be replaced by database-backed repository

6️⃣ DTO Layer
Responsibility:

Define API request/response structures

Separate external API models from internal models

Key DTOs:

BestLocationResponse

WeatherbitForecastResponse

ForecastDayDto

Rules:

Do not expose domain objects directly

Keep API contract stable


7️⃣ Exception Handling Layer
Responsibility:

Centralized error handling

Consistent JSON error responses

Key Component:

GlobalExceptionHandler

Custom Exceptions:

InvalidDateException

DateOutOfRangeException

ExternalServiceException

Behavior:

400 → Validation issues

500 → External API failure

200 → No suitable location (not treated as error)


8️⃣ Configuration Layer
Responsibility:

Define beans and application properties

Configure HTTP client

Store API key securely

Key Components:

AppConfig

application.yml

Properties:

Weatherbit API key

Base URL

Timeout configuration


🔄 Request Flow Example – Get Best Location

HTTP request reaches BestLocationController

Date parameter validated

LocationEvaluationService invoked

Service retrieves predefined locations

For each location:

WeatherbitClient fetches forecast

Extracts temp and wind_spd

Service filters:

Wind between 5–18 m/s

Highest wind speed selected

BestLocationResponse returned


🧪 Testing Strategy

Unit tests:

Wind filtering logic

Selection algorithm

Date validation

Integration tests:

Controller + Service flow

Mocked WeatherbitClient

Error scenario tests:

Invalid date

API failure

No qualifying location


✨ Extensibility

Architecture allows easy extension:

Add caching layer (e.g., Redis)

Add persistent database for locations

Add scoring algorithm including:

Temperature comfort range

Humidity

Wave height

Add authentication (Spring Security)

Convert to reactive WebClient-based architecture

Add multi-day ranking endpoint


🗝️ Key Design Decisions

No database in MVP (stateless design)

Embedded locations for simplicity

Strict separation of external API models and domain models

Controllers contain zero business logic

Deterministic wind-based selection

Centralized exception handling

Designed for easy future scaling


🧱 High-Level Component Diagram (Conceptual)
Client
↓
Controller
↓
Service (Selection Logic)
↓
WeatherbitClient
↓
Weatherbit API

✅ Summary

The architecture ensures:

Clean separation of responsibilities

High testability

Clear business rule enforcement

Stateless and scalable REST service

Simple but extensible foundation for future enhancements