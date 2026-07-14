# Car Auction Analyzer - Backend

Java backend for analyzing auction vehicles and determining whether they are worth purchasing.

The backend follows a layered architecture that separates data models, business logic, data loading, and presentation, making it easy to extend with APIs, databases, and a frontend in future iterations.

---

## Features

- Read vehicle information from external data
- Calculate estimated market value
- Estimate repair costs
- Evaluate paperwork and title status
- Estimate transportation costs
- Calculate recommended maximum bid
- Generate an overall purchase recommendation

---

## Project Structure

```
src/main/java/com/carauction
├── data/       # Reads vehicle data
├── model/      # Domain models
├── service/    # Business logic
├── ui/         # Console output
└── Main.java   # Application entry point
```

### Package Responsibilities

| Package | Purpose |
|----------|---------|
| `model` | Represents the application's data and domain objects. |
| `data` | Loads vehicle information from external sources. |
| `service` | Performs all calculations and business logic. |
| `ui` | Displays results to the user. |
| `Main` | Starts the application and coordinates execution. |

---

## Current Architecture

```
Vehicle Data
      │
      ▼
 CsvReader
      │
      ▼
 Vehicle Models
      │
      ▼
AuctionAnalyzer
 ├── HistoryService
 ├── MarketComparisonService
 ├── PaperworkService
 └── TransportService
      │
      ▼
AnalysisResult
      │
      ▼
ConsolePrinter
```

---

## Future Improvements

- Spring Boot REST API
- PostgreSQL integration
- Vehicle history API integration
- Market pricing API
- React frontend
- Authentication
- Report generation

---

## Technologies

- Java
- Maven
- Object-Oriented Programming
- Layered Architecture

---

## Status

Currently under active development.