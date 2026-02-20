🌊 Worldwide Windsurfer’s Weather Service – Data Model (MVP)

📌 Overview

The data model supports core windsurfing location evaluation functionality:

Predefined windsurfing locations

Forecast data representation

Best-location response model

Designed for MVP simplicity

No database required (in-memory configuration)

Clean separation between domain and external API DTOs

The application does not require persistence in MVP.
All locations are embedded in the application and forecast data is fetched dynamically from the Weatherbit API.

📍 WindsurfingLocation (Domain Model)

Represents a predefined windsurfing location embedded in the system.

Field	Type	Constraints	Description
name	String	not null	Location name
country	String	not null	Country name
latitude	double	not null	Geographic latitude
longitude	double	not null	Geographic longitude

📌 Embedded Locations (MVP)

The system contains exactly five predefined locations:

Jastarnia (Poland)

Bridgetown (Barbados)

Fortaleza (Brazil)

Pissouri (Cyprus)

Le Morne (Mauritius)

Locations are stored:

As static configuration

Or in a dedicated in-memory repository class

Designed to allow easy future extension

🌤 ForecastDay (External API DTO)

Represents forecast data retrieved from Weatherbit API.

Field	Type	Source Field	Description
date	LocalDate	valid_date	Forecast date
temperature	double	temp	Average temperature (°C)
windSpeed	double	wind_spd	Wind speed (m/s)

🏆 BestLocationResponse (API DTO)

Represents the REST API response returned to the client.

Field	Type	Description
date	LocalDate	Requested date
location	String	Best location name (nullable)
averageTemperature	Double	Average temperature (nullable)
windSpeed	Double	Wind speed (nullable)
message	String	Optional informational message
⚙️ Selection Rules (Domain Logic Constraints)

Wind speed must be between 5 and 18 m/s (inclusive).

Wind speed is the primary selection criterion.

If multiple locations qualify:

Select the one with highest wind speed.

If none qualify:

location = null.

🔗 Relationships

In MVP there are no persistent relationships.

Logical flow:

WindsurfingLocation
↓
Weatherbit Forecast (per location)
↓
ForecastDay (filtered by date)
↓
Selection Engine
↓
BestLocationResponse

⚠️ Constraints

Date must be within Weatherbit’s 16-day forecast range.

Date format must be yyyy-mm-dd.

Wind speed filtering applied strictly before comparison.

No fallback logic if Weatherbit API fails.


📝 Design Guidelines

No @Entity annotations required (no database).

Separate:

domain models

external API DTOs

api response DTOs

Use immutable DTOs where possible.

Avoid tight coupling between domain logic and external API structures.

Use RestTemplate or WebClient for API integration.

Apply SOLID principles in service layer.


🗂️ Package Structure (Code)
com.example.windsurfservice
├── api
│   ├── controller
│   └── dto
├── domain
│   ├── model
│   ├── service
│   └── repository
├── external
│   ├── weatherbit
│   │   ├── client
│   │   └── dto
├── config
├── exception
└── common

🚀 Future Extension Possibilities

The data model is intentionally simple but allows:

Adding persistent storage (JPA entities)

Caching forecast results

Adding scoring algorithm entity

Supporting additional weather parameters (wave height, humidity)

Dynamic location management

Multi-day ranking endpoint


✅ Summary

The MVP data model:

Is lightweight and stateless

Separates domain from external API models

Embeds predefined windsurfing locations

Applies deterministic wind-based selection logic

Is easily extensible without architectural redesign