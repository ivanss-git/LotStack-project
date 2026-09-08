"""CarFast structured-endpoint collector."""

from typing import Any

import requests


CARFAST_BASE_URL = "https://carfast.express"
BRANDS_URL = f"{CARFAST_BASE_URL}/api/auction/brands"

REQUEST_TIMEOUT_SECONDS = 30


class CarFastError(RuntimeError):
    """Raised when a CarFast request fails."""


def create_session() -> requests.Session:
    """Create a reusable HTTP session."""

    session = requests.Session()

    session.headers.update(
        {
            "Accept": "application/json",
            "User-Agent": (
                "LotStack/1.0 "
                "(educational vehicle-analysis project)"
            ),
        }
    )

    return session


def fetch_brands(
    vehicle_type: str = "v",
    session: requests.Session | None = None,
) -> list[dict[str, Any]]:
    """Retrieve vehicle brands from CarFast."""

    active_session = session or create_session()

    response = active_session.get(
        BRANDS_URL,
        params={"vehicle_type": vehicle_type},
        timeout=REQUEST_TIMEOUT_SECONDS,
    )

    if not response.ok:
        raise CarFastError(
            f"CarFast brands request failed with status "
            f"{response.status_code}: {response.text[:500]}"
        )

    payload = response.json()

    if not isinstance(payload, list):
        raise CarFastError(
            "CarFast returned an unexpected brands response."
        )

    return payload


def normalize_brand(
    brand: dict[str, Any],
) -> dict[str, Any]:
    """Normalize one CarFast brand record."""

    return {
        "source": "carfast",
        "external_id": brand.get("id"),
        "name": brand.get("name"),
        "slug": brand.get("slug"),
        "listing_count": brand.get("count", 0),
        "popular": bool(brand.get("popular", 0)),
    }


def collect_brands() -> list[dict[str, Any]]:
    """Collect and normalize all available CarFast brands."""

    raw_brands = fetch_brands()

    return [
        normalize_brand(brand)
        for brand in raw_brands
    ]


if __name__ == "__main__":
    brands = collect_brands()

    print(f"Collected {len(brands)} CarFast brands.")

    for brand in brands:
        print(
            f"{brand['external_id']:>4} | "
            f"{brand['name']:<20} | "
            f"{brand['listing_count']:>6} listings"
        )
