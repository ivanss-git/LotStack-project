# normalize.py

def normalize_listing(item):
    price = item.get("price", {})

    return {
        "external_id": item.get("itemId"),
        "title": item.get("title"),
        "price": float(price["value"]) if price.get("value") else None,
        "currency": price.get("currency"),
        "condition": item.get("condition"),
        "category_id": item.get("categoryId"),
        "listing_url": item.get("itemWebUrl"),
        "source": "ebay"
    }