# recall that an api returns a JSON, not directly a CSV
# flow is :
# Website API
# HTTP request
# JSON response
# Pandas DataFrame
# Filter Data
# Save csv
import requests
import pandas as pd

# sends a GET request asking for api information
url = "https://example.com/api/vehicles"
# converts the returned JSON in a python dict and list
response = requests.get(url, timeout=10)
response.raise_for_status()

data = response.json()
df = pd.DataFrame(data)

filtered_df = df[df["year"] > 2010]
filtered_df.to_csv("api_cars.csv", index=False)
print(data.describe())
