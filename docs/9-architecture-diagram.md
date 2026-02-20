          ┌───────────────┐
          │    Client     │
          └───────┬───────┘
                  │ HTTP Request
          ┌───────▼────────┐
          │  Controller    │
          │ (BestLocation) │
          └───────┬────────┘
                  │ Delegates
          ┌───────▼────────┐
          │   Service      │
          │(LocationEvaluation) │
          └───────┬────────┘
                  │ Calls
          ┌───────▼────────┐
          │ External API   │
          │  Client Layer  │
          │ (Weatherbit)   │
          └───────┬────────┘
                  │ REST API
          ┌───────▼────────┐
          │ Weatherbit API │
          └────────────────┘

Other Components:

┌─────────────────────────────────────────┐
│ Domain Layer                             │
│  - WindsurfingLocation                   │
│  - ForecastDay                            │
│ (Business rules & filtering)             │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│ Location Config Layer                     │
│  - LocationRepository (in-memory)        │
│  - Predefined locations                  │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│ DTO Layer                                 │
│  - BestLocationResponse                   │
│  - ForecastDayDto                          │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│ Exception Handling Layer                  │
│  - GlobalExceptionHandler                │
│  - InvalidDateException, etc.            │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│ Configuration Layer                       │
│  - AppConfig                              │
│  - application.yml                         │
└─────────────────────────────────────────┘