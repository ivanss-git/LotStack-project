# Car Analyzer

Batch-analyzes auction cars from a **CSV** and recommends a **max bid** using simple risk/valuation rules.

## Features
- CSV-driven (no hardcoding) — header auto-detected, comments/blank lines ignored
- Computes **Market Value**, **Repair Cost**, **Title Factor**, **Max Bid**
- Clean, aligned console output (USD currency)

## Run
```bash
# Option A — compile & run directly
javac *.java
java Main                 # uses cars.csv by default
java Main path/to/my.csv  # analyze a different file

# Option B — script (macOS/Linux)
chmod +x run.sh
./run.sh                  # uses cars.csv
./run.sh path/to/my.csv

