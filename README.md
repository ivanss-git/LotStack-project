# LotStack

LotStack is a full-stack vehicle auction analysis platform that collects and normalizes auction listings, evaluates potential purchases, and ranks vehicles using estimated value, repair cost, title risk, resale potential, and expected ROI.

The project is based on real vehicle-auction purchasing experience and combines a Spring Boot API, PostgreSQL database, Python data pipeline, React dashboard, and Chrome extension. A machine-learning ranking model is currently in development.

## Live Demo and Results

**[View the Live LotStack Dashboard](https://lotstack.onrender.com)**

The dashboard displays real vehicle purchase and resale outcomes tracked through LotStack:

- **$94,800** in total vehicle sales
- **$62,106** invested in sold vehicles
- **$32,694** in realized net profit
- **64.0%** average ROI
- **$92,119** invested across the complete portfolio

> The hosted application may take a moment to load after a period of inactivity.

## Current Features

- Displays real vehicle purchases and resale results
- Tracks purchase prices, fees, repair expenses, and selling prices
- Calculates profit and ROI for each vehicle
- Collects auction listings through site-specific Python scrapers
- Processes all available listing pages from supported auction sources
- Normalizes VIN, price, mileage, damage, title, location, and estimated value
- Stores vehicle listings and analysis results in PostgreSQL
- Applies transparent, rule-based scoring to rank auction opportunities
- Exposes vehicle information and recommendations through REST API endpoints
- Displays ranked recommendations and risk scores through a Chrome extension
- Manages database changes through Flyway migrations

## How It Works

```text
Auction Sources
       ↓
Python Scrapers
       ↓
Data Cleaning and Normalization
       ↓
PostgreSQL Database
       ↓
Spring Boot Analysis and Ranking API
       ↓
Web Dashboard and Browser Extension
```

The scraping pipeline gathers vehicle listings and converts inconsistent auction information into a standardized format.

The backend then evaluates each listing using available information such as price, estimated repair cost, resale potential, title condition, and overall risk. The results are returned through the API and displayed through the dashboard or browser extension.

## Technology Stack

| Area | Technologies |
|---|---|
| Backend | Java 21, Spring Boot, Spring Data JPA, Maven |
| Database | PostgreSQL, Flyway |
| Scraping and ingestion | Python, Requests, Beautiful Soup |
| Web dashboard | React, TypeScript, Vite |
| Browser extension | React, TypeScript, Vite, Chrome Manifest V3 |
| Machine learning | Python, Jupyter, pandas, scikit-learn |
| Development | Docker Compose, Git, GitHub |

## Ranking System

The current ranking system uses explicit rules and weighted metrics to evaluate each auction listing.

Depending on the available data, the system considers:

- Current auction price
- Estimated market value
- Expected repair expenses
- Resale potential
- Title condition
- Vehicle damage
- Mileage
- Location and transportation costs
- Expected profit and ROI
- Overall purchase risk

The rule-based system provides a transparent working baseline that can later be compared against the machine-learning model.

## Machine Learning Development

A machine-learning model is currently being developed to predict vehicle resale value and improve purchase recommendations using historical auction, vehicle, and sales data.

Planned development includes:

- Training and comparing multiple prediction models
- Predicting resale value and potential profit
- Comparing model recommendations with the current rule-based rankings
- Evaluating predictions against actual vehicle resale outcomes
- Measuring prediction error, realized profit, and ROI
- Adding explainable recommendations that show why a vehicle received its ranking

The README and live application will be updated once the model has been fully trained, evaluated, and integrated.

## Project Structure

```text
backend/      Spring Boot API, services, entities, repositories, and migrations
data/         Sample datasets and project data
docs/         Architecture, API, database, testing, and roadmap documentation
extension/    Chrome extension for auction recommendations
frontend/     React dashboard for vehicle purchases and outcomes
ingestion/    Vehicle import and data-processing pipeline
ml/           Analytics notebooks and machine-learning development
scrapers/     Auction scrapers, database helpers, and normalization
scripts/      Ranking and workflow utilities
```

## Roadmap

- Complete and evaluate the machine-learning model
- Integrate model predictions into the backend
- Compare predicted results with actual auction and resale outcomes
- Expand supported auction-site integrations
- Improve automated scanning and scheduling
- Add notifications for high-ranking vehicles
- Track historical model performance
- Improve recommendation explanations

## Developer Setup

Detailed installation and local development instructions are available in the [`docs`](docs/) directory.

To clone the repository:

```bash
git clone https://github.com/ivanss-git/LotStack-project.git
cd LotStack-project
```

## Project Status

LotStack is under active development.

The dashboard, database integration, rule-based ranking system, REST API, scraping pipeline, and browser-extension interface have been implemented. The machine-learning model and additional auction-site integrations are currently in progress.

## Disclaimer

LotStack is an educational and personal decision-support project. Vehicle estimates and rankings are not financial guarantees and should be independently verified before making a purchase.
