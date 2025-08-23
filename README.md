# Car Analyzer

A small Java console app that estimates **market value** and computes a recommended **max bid** for auction cars based on:
- year & mileage
- title condition (Clean/Salvage/Rebuilt)
- damage type (None/Accident/Engine/Flood/etc.)
- baseline value and target profit
- auction & towing fees

---

## Input: CSV Format
`cars.csv` (comma-separated, one car per line)

## Run
./run.sh                # runs with cars.csv
./run.sh mycars.csv     # runs with a custom file

