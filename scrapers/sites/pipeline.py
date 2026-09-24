# pipeline.py

from ebay_sb_ingest import fetch_listings
from normalize import normalize_listing

data = fetch_listings("toyota")

normalized = [
    normalize_listing(item)
    for item in data.get("itemSummaries", [])
]

for listing in normalized:
    print(listing)