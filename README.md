# LotStack

LotStack is a full-stack vehicle auction analysis platform that collects auction listings, normalizes inconsistent vehicle data, and ranks potential purchases using estimated value, repair cost, title risk, ROI, and other rule-based metrics.

The project combines a Spring Boot REST API, PostgreSQL database, Python scrapers, a React browser extension, and a web dashboard. It is based on practical vehicle-auction purchasing experience and is being expanded with machine-learning ranking models.

## Current Features

- Collects vehicle listings from auction sources through site-specific Python scrapers
- Normalizes fields such as VIN, price, mileage, damage, title, location, and estimated value
- Stores listings and analysis results in PostgreSQL
- Applies rule-based scoring to compare auction opportunities
- Calculates estimated purchase price, repair cost, resale value, ROI, and risk metrics
- Exposes ranked listings through REST API endpoints
- Displays recommendations and risk scores in a Chrome browser extension
- Includes a web dashboard for viewing purchased vehicles, costs, sales, profit, and ROI
- Supports database migrations through Flyway
- Provides a shell script for running the ranking workflow

## How It Works

```text
Auction sources
      |
Python scrapers
      |
Data normalization
      |
PostgreSQL database
      |
Spring Boot analysis and ranking API
      |
Browser extension / web dashboard
```

## Technology Stack

| Area | Technologies |
| --- | --- |
| Backend | Java 21, Spring Boot, Spring Data JPA, Maven |
| Database | PostgreSQL, Flyway |
| Scraping and ingestion | Python, Requests, Beautiful Soup |
| Browser extension | React, TypeScript, Vite, Chrome Manifest V3 |
| Web dashboard | React, TypeScript, Vite |
| Machine learning | Python, Jupyter, pandas/scikit-learn workflow in progress |
| Development | Docker Compose, Git, GitHub |

## Project Structure

```text
backend/      Spring Boot REST API, ranking services, entities, and migrations
data/         Sample datasets and fixtures
docs/         Architecture, API, database, testing, and roadmap documentation
extension/    Chrome extension for ranked auction recommendations
frontend/     Web dashboard for vehicle purchases and outcomes
ingestion/    Vehicle-import pipeline and NHTSA integration experiments
ml/           Analytics notebooks and machine-learning development
scrapers/     Auction scrapers, database helpers, and data normalization
scripts/      Utility scripts for running ranking workflows
```

## Prerequisites

- Java 21
- Maven
- PostgreSQL
- Python 3 with the packages in `requirements.txt`
- Node.js and npm
- Google Chrome or another Chromium-based browser

## Local Setup

### 1. Clone the Repository

```bash
git clone https://github.com/ivanss-git/LotStack-project.git
cd LotStack-project
```

### 2. Start the Database

Start PostgreSQL using the included Docker Compose configuration:

```bash
docker compose up -d
```

Configure the database connection values required by the backend and scraper without committing credentials to Git.

### 3. Run the Backend

```bash
cd backend
mvn spring-boot:run
```

The API runs locally at `http://localhost:8080` by default. Flyway applies the database migrations when the backend starts.

### 4. Run a Scraper

From the project root, create and activate a Python virtual environment, then install the dependencies:

```bash
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

Run an implemented scraper directly, for example:

```bash
python -m scrapers.sites.lso
```

### 5. Run the Ranking Workflow

```bash
chmod +x scripts/rankings.sh
./scripts/rankings.sh
```

### 6. Build the Browser Extension

```bash
cd extension
npm install
npm run build
```

In Chrome:

1. Open `chrome://extensions`.
2. Enable **Developer mode**.
3. Select **Load unpacked**.
4. Choose the generated `extension/dist` directory.

The current extension requests ranking data from `http://localhost:8080`.

### 7. Run the Web Dashboard

```bash
cd frontend
npm install
npm run dev
```

## Ranking System

The current ranking pipeline uses explicit rules and weighted metrics to evaluate each listing. It considers available fields such as price, estimated repair cost, resale potential, title condition, and risk, then returns listings in recommendation order through the backend API.

This rule-based version provides a transparent baseline that can later be compared with the machine-learning model.

## In Progress

- Train and evaluate a machine-learning model for more accurate price and purchase rankings
- Compare predicted rankings with actual auction and resale outcomes
- Expand and improve auction-site integrations
- Improve automated scheduling and notifications
- Add model evaluation, historical performance tracking, and explainable recommendations

## Project Status

LotStack is under active development. The data pipeline, database integration, rule-based ranking service, API responses, web dashboard, and extension interface are implemented.

Machine-learning ranking and additional production-ready scraper integrations are currently in progress.

## Disclaimer

LotStack is an educational and personal decision-support project. Its estimates and rankings are not financial guarantees and should be verified before purchasing a vehicle.