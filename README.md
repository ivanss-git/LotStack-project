# LotStack

LotStack is a full-stack vehicle auction analysis platform combining a Spring Boot API, PostgreSQL database, Python data pipeline, React dashboard, and Chrome extension to collect, normalize, evaluate, and rank vehicle auction listings [1.17]. A machine-learning ranking model is currently in development [1.17].

## Live Demo and Results

**[View the Live LotStack Dashboard](https://onrender.com)**

Portfolio outcomes tracked via LotStack:
- **\$101,200** in total vehicle sales [1.17]
- **\$66,326** invested across the portfolio [1.17]
- **\$34,874** realized net profit [1.17]
- **62.6%** average ROI [1.17]

## Current Features

- Tracks real vehicle purchases, pricing, repairs, profit, and ROI [1.17]
- Scrapes and normalizes auction listings via Python and Beautiful Soup [1.17]
- Persists data in PostgreSQL and manages migrations via Flyway [1.17]
- Provides rule-based auction scoring via REST endpoints, React dashboard, and a Chrome extension [1.17]
- Features automated CSV ingestion and statistical re-computation on boot [1.17]

## Architecture & Technology Stack

```text
Auction Sources → Python Scrapers → Normalization → PostgreSQL → Spring Boot REST API → Analysis & Ranking → React Dashboard / Chrome Extension
```

| Area | Technologies |
| :--- | :--- |
| **Backend** | Java 21, Spring Boot, Spring Data JPA, Maven [1.17] |
| **Database** | PostgreSQL, Flyway [1.17] |
| **Scraping/Ingestion** | Python, Requests, Beautiful Soup [1.17] |
| **Web/Extension** | React, TypeScript, Vite, Chrome Manifest V3 [1.17] |
| **Machine Learning** | Python, Jupyter, pandas, scikit-learn [1.17] |
| **DevOps** | Docker Compose, Git, GitHub [1.17] |

## Ranking & Machine Learning

The rule-based system ranks listings using auction prices, estimated values, repair expenses, title risk, mileage, and expected ROI [1.17]. Concurrently, an ML model is under development to predict resale values, compare recommendations against baseline rules, and offer explainable outputs [1.17].

## Project Structure & Developer Setup

Directories include `backend/`, `data/`, `docs/`, `extension/`, `frontend/`, `ingestion/`, `ml/`, `scrapers/`, and `scripts/` [1.17]. 

Ensure [Docker Desktop](https://docker.com) is running, then use these zero-touch commands:
```bash
git clone https://github.com/ivanss-git/LotStack-project.git
cd LotStack-project
npm run dev:all     # Launch full environment
npm run dev:clean   # Hard reset and fresh bootstrap
```
API runs at `http://localhost:8080` and Dashboard at `http://localhost:3000` [1.17].

## Status & Disclaimer
LotStack is under active development [1.17]. It serves as an educational decision-support tool; estimates are not financial guarantees [1.17].
