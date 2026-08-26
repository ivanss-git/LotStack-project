import requests

item_id = "229091"
auction_id = "7729"

item_url = (
    "https://www.lso.cc/auction/7729/"
    "item/2009-honda-civic-gx-cng-229091/"
)

session = requests.Session()

session.headers.update({
    "Accept": "application/json, text/plain, */*",
    "User-Agent": "Mozilla/5.0",
    "Origin": "https://www.lso.cc",
    "Referer": item_url,
})

# Establish PHP session cookies
session.get(item_url, timeout=20)
session.cookies.set("ckchk", "1", domain="www.lso.cc")

response = session.post(
    "https://www.lso.cc/api/ItemData",
    data={
        "item_id": item_id,
        "auction_id": auction_id,
    },
    timeout=20,
)

print("Status:", response.status_code)
print("Response:", response.text[:1000])

response.raise_for_status()
item = response.json()

print(item["title"])
print(item["current_bid"])

# Succfully : Create session → fetch page → call internal API → parse JSON → extract fields