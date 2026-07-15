# recall that an api returns a JSON, not directly a CSV
# flow is :
# Website API
# HTTP request
# JSON response
# Pandas DataFrame
# Filter Data
# Save csv

"""
Example 2: External API to CSV

Purpose:
    Learn how to retrieve data from a public REST API and process it with pandas.

Concepts:
    - HTTP GET requests
    - Query parameters
    - JSON responses
    - Converting JSON to DataFrames
    - Saving API data to CSV

Data Flow:
    External API
        ↓
    HTTP Request
        ↓
    JSON Response
        ↓
    pandas DataFrame
        ↓
    Save CSV
"""

import requests
import json
import pandas as pd

# specifies the endpoint we're requesting
url = "https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMakeYear/make/honda/modelyear/2020"

# sesends additional query params with the request (asking api to return JSON)
params = {
    "format": "json"
}

# sends a GET request asking for api information, waits 10s at most
response = requests.get(url, params=params, timeout=10)

# verifies that out request succeeded
response.raise_for_status()

# parses the JSON into normal python objects like dicts and lists
data = response.json()

# shows top level fields like count, message, results
print(data.keys())
print(data["Results"][:3])

# converts list of vehicles into a data frame
cars_df = pd.DataFrame(data["Results"])

# saves the data frame as a CSV
cars_df.to_csv("honda_models.csv", index=False)


print(cars_df.describe())
print(cars_df)
print(f"Saved {len(cars_df)} models.")