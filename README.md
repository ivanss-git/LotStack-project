# LotStack

LotStack is a full-stack vehicle auction analysis platform combining a Java 21/Spring Boot API, PostgreSQL database, Python data pipeline, React dashboard, and Chrome extension to collect, normalize, evaluate, and rank vehicle auction listings. The platform also tracks real vehicle investments, expenses, and profitability. **Machine learning integration is the next stage of development**, with plans to improve vehicle valuation and ranking accuracy.

## Live Demo and Results

**[View the Live LotStack Dashboard](https://lotstack.onrender.com)**

Portfolio outcomes tracked via LotStack:

- **$101,200** in total vehicle sales
- **$66,326** invested across the portfolio
- **$34,874** realized net profit
- **62.6%** average ROI

## Current Features

- Tracks real vehicle purchases, pricing, repairs, expenses, profit, and ROI
- Scrapes and normalizes auction listings using Python, Requests, and Beautiful Soup
- Persists vehicle records in PostgreSQL with Spring Data JPA and Flyway migrations
- Provides rule-based auction scoring and vehicle recommendations
- Supports vehicle creation, retrieval, updates, deletion, and VIN-based lookup
- Manages vehicle transportation, paperwork, history, and analysis records
- Exposes 22 REST API endpoints across six Spring Boot controllers
- Provides interactive API documentation and endpoint testing through Swagger UI
- Integrates a React dashboard and Chrome extension
- Supports automated CSV ingestion and statistical recomputation on startup

## Architecture & Technology Stack

```text
Auction Sources
      |
      v
Python Scrapers
      |
      v
Data Normalization & Ingestion
      |
      v
PostgreSQL Database
      |
      v
Spring Boot REST API
      |
      v
Analysis & Rule-Based Ranking
      |
      +----------------------+
      |                      |
      v                      v
React Dashboard       Chrome Extension
```

| Area | Technologies |
| :--- | :--- |
| **Backend** | Java 21, Spring Boot 4.1.1, Spring Data JPA, Maven |
| **Database** | PostgreSQL, Flyway |
| **Scraping/Ingestion** | Python, Requests, Beautiful Soup, Apache Commons CSV |
| **Web/Extension** | React, TypeScript, Vite, Chrome Manifest V3 |
| **API Documentation** | Springdoc OpenAPI 3, Swagger UI |
| **DevOps** | Docker Compose, Git, GitHub |

## REST API

LotStack exposes 22 REST endpoints organized into six controllers.

### Vehicle Management

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/api/vehicles` | Retrieve vehicle records |
| POST | `/api/vehicles` | Create a vehicle record |
| GET | `/api/vehicles/{id}` | Retrieve a vehicle by ID |
| PATCH | `/api/vehicles/{id}` | Update vehicle information |
| DELETE | `/api/vehicles/{id}` | Delete a vehicle record |
| GET | `/api/vehicles/vin/{vin}` | Retrieve a vehicle by VIN |

### Vehicle Details

Transportation, paperwork, history, and analysis each support retrieving, updating, and deleting associated vehicle information.

| Resource | GET | PUT | DELETE |
| :--- | :---: | :---: | :---: |
| `/api/vehicles/{vehicleId}/transport` | ✓ | ✓ | ✓ |
| `/api/vehicles/{vehicleId}/paperwork` | ✓ | ✓ | ✓ |
| `/api/vehicles/{vehicleId}/history` | ✓ | ✓ | ✓ |
| `/api/vehicles/{vehicleId}/analysis` | ✓ | ✓ | ✓ |

### Auction Analysis & Recommendations

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| POST | `/api/demo/import` | Import demonstration data |
| POST | `/api/demo/listing-analysis/refresh` | Refresh listing analysis |
| GET | `/api/demo/recommendations` | Retrieve vehicle recommendations |
| GET | `/api/demo/rankings` | Retrieve ranked auction listings |

## API Documentation

LotStack uses Springdoc OpenAPI and Swagger UI to automatically generate interactive documentation from its Spring Boot controllers.

With the backend running locally, access:

- **[Swagger UI](http://localhost:8080/swagger-ui/index.html)** — Explore and test REST endpoints
- **[OpenAPI Specification](http://localhost:8080/v3/api-docs)** — View the generated OpenAPI 3.1 definition

Swagger UI provides endpoint descriptions, request and response schemas, and interactive testing for supported HTTP operations.

## Ranking & Analysis

LotStack uses a rule-based analysis system to evaluate vehicle auction opportunities.

Listings are evaluated using factors including:

- Auction purchase prices
- Estimated market values
- Repair and additional expenses
- Vehicle title risk
- Mileage
- Expected profit and return on investment

The system provides ranked listings and recommendations through REST API endpoints for use by the dashboard and extension.

## Project Structure & Developer Setup

The project is organized into the following directories:

```text
LotStack-project/
├── backend/       # Spring Boot REST API
├── data/          # Vehicle and auction datasets
├── docs/          # Project documentation
├── extension/     # Chrome extension
├── frontend/      # React dashboard
├── ingestion/     # Data ingestion pipeline
├── scrapers/      # Auction listing scrapers
└── scripts/       # Development and automation scripts
```

### Prerequisites

- Java 21
- Node.js and npm
- Docker Desktop
- Git

### Local Development

Ensure [Docker Desktop](https://docker.com) is running, then clone the repository:

```bash
git clone https://github.com/ivanss-git/LotStack-project.git
cd LotStack-project
```

Launch the development environment:

```bash
npm run dev:all
```

For a clean environment and fresh bootstrap:

```bash
npm run dev:clean
```

### Local Services

| Service | URL |
| :--- | :--- |
| React Dashboard | http://localhost:3000 |
| Spring Boot API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| OpenAPI Specification | http://localhost:8080/v3/api-docs |

## Project Status

LotStack is under active development, with a functioning Spring Boot backend, PostgreSQL persistence, data ingestion pipeline, rule-based analysis, React dashboard, and Chrome extension.

The platform continues to evolve through improvements to auction data collection, vehicle evaluation, and the overall user experience.