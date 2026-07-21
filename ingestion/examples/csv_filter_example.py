
"""
Example 1: Local CSV Processing

Purpose:
    Learn how to read, inspect, filter, and write CSV files using pandas.

Concepts:
    - pandas DataFrames
    - Reading CSV files
    - Filtering rows
    - Saving filtered data
    - Basic data inspection

Data Flow:
    Local CSV
        ↓
    pandas DataFrame
        ↓
    Filter rows
        ↓
    Save new CSV
"""

import pandas as pd

csv_path = "src/main/java/com/carauction/data/car_auction_train.csv"

#reads the CSV into a DataFrame
df = pd.read_csv(csv_path)

minYear = 2010

# filters the rows where the year column is greater than minYear#$
filtered_df = df[df["year"] > minYear]

# writes the filtered DataFrame to a new csv file
# index=false prevents pandas from adding an extra numbered column to the csv
filtered_df.to_csv("csv_reader_example.csv", index=False)
filtered_df.to_csv()

# verify it worked
print(filtered_df.describe())
print(len(filtered_df))

# print(df["year"].value_counts().sort_index())