import os
import requests
from dotenv import load_dotenv

load_dotenv()

def get_access_token():
    response = requests.post(
        "https://api.sandbox.ebay.com/identity/v1/oauth2/token",
        headers={
            "Content-Type": "application/x-www-form-urlencoded"
        },
        auth=(
            os.getenv("EBAY_CLIENT_ID"),
            os.getenv("EBAY_CLIENT_SECRET")
        ),
        data={
            "grant_type": "client_credentials",
            "scope": "https://api.ebay.com/oauth/api_scope"
        }
    )

    response.raise_for_status()
    return response.json()["access_token"]

