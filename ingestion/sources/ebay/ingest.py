# ebay_ingest.py

from ingestion.sources.ebay.auth import get_access_token
import requests


def fetch_listings(query: str):
    token = get_access_token()

    headers = {
        "Authorization": f"Bearer {token}"
    }

    response = requests.get(
        f"https://api.sandbox.ebay.com/buy/browse/v1/item_summary/search?q={query}",
        headers=headers
    )

    response.raise_for_status()

    return response.json()


def main():
    data = fetch_listings("toyota")

    print(f"Listings Returned: {len(data.get('itemSummaries', []))}")

    for item in data.get("itemSummaries", [])[:5]:
        print(item.get("title"))


if __name__ == "__main__":
    main()
# now that fixed, the pipeline is:
# python->ebayOAuthEndpoint->AccessToken->ebayBrowseApi->JSONresponse