
"""
Example 3: FastAPI CSV Service

Purpose:
    Build a simple REST API that serves data stored in a local CSV file.

Concepts:
    - FastAPI
    - REST endpoints
    - Pydantic models
    - Reading CSV data
    - Returning JSON responses

Data Flow:
    Client Request
        ↓
    FastAPI
        ↓
    Read CSV
        ↓
    pandas DataFrame
        ↓
    JSON Response
"""

from fastapi import FastAPI
import pandas as pd

app = FastAPI()

df = pd.read_csv("car_auction_train.csv")

@app.get("/Vehicles")
def get_vehicles(min_year: int = 2010):
    filtered_df = df[df["year" > min_year]]

    return filtered_df.head(100).to_dict(orient="records")
