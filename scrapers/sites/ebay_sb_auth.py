import os
import requests

# add these to double check 
# python wasnt getting credentials from the environment
# fix was to ecport them again
print("CLIENT ID:" , os.getenv("EBAY_CLIENT_ID"))
print("EBAY_CLIENT_SECRET", os.getenv("EBAY_CLIENT_SECRET"))

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

