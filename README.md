# Car Auction Analyzer

A Java application that analyzes auction vehicles from a CSV file and recommends whether a vehicle appears to be a good purchase based on simple valuation and risk calculations.

## Features

* Reads vehicle data from a CSV file
* Automatically detects the CSV header
* Ignores blank lines and comments
* Calculates:

  * Estimated Market Value
  * Estimated Repair Cost
  * Title Risk Factor
  * Recommended Maximum Bid
* Returns whether the vehicle is a good or bad purchase based on the calculated values
* Displays clean, formatted console output

## Project Structure

```text
backend/
data/
database/
docs/
frontend/
ml/
scraping/
```

## Running the Application

From the `backend` directory:

```bash
javac -d target/classes src/main/java/com/carauction/*.java

java -cp target/classes com.carauction.Main ../data/samples/cars.csv
```

To analyze a different CSV file:

```bash
java -cp target/classes com.carauction.Main path/to/your/file.csv
```

## Future Goals

This project will continue to grow into a full-stack application with:

* Real auction data collection
* Database integration
* REST APIs
* Web interface
* Data visualization
* Machine learning predictions

