import html
import re
import time

import requests

from scrapers import db_helper


CATALOG_URL = "https://www.lso.cc/api/getitems"
LSO_HOME_URL = "https://www.lso.cc/"

PER_PAGE = 24
REQUEST_DELAY_SECONDS = 1
MAX_PAGES = 100


def clean_html(value):
    """Remove HTML tags and decode HTML entities."""
    value = html.unescape(value or "")

    value = re.sub(
        r"<br\s*/?>",
        " ",
        value,
        flags=re.IGNORECASE,
    )

    value = re.sub(r"<[^>]+>", " ", value)

    return " ".join(value.split())


def extract_vehicle(item):
    """Convert one LSO item into the database format."""
    item_id = item.get("id")

    title = clean_html(item.get("title"))
    description = clean_html(item.get("description"))
    full_text = f"{title} {description}"

    vin_match = re.search(
        r"\b[A-HJ-NPR-Z0-9]{17}\b",
        full_text,
        re.IGNORECASE,
    )

    year_match = re.search(
        r"\b(?:19|20)\d{2}\b",
        title,
    )

    mileage_match = re.search(
        r"(?:MILEAGE|ODOMETER(?: SHOWS)?)"
        r"\s*:?\s*([\d,]+)",
        full_text,
        re.IGNORECASE,
    )

    # The database requires an ID, year, make and model.
    if not item_id or not vin_match or not year_match:
        return None

    year = int(year_match.group(0))

    # Remove the VIN and extra labels such as "- Key".
    vehicle_title = re.split(
        r"\bVIN\s*#?",
        title,
        maxsplit=1,
        flags=re.IGNORECASE,
    )[0]

    vehicle_title = vehicle_title.split(" - ")[0].strip()
    title_words = vehicle_title.split()

    if len(title_words) < 3:
        return None

    make = title_words[1].title()
    model = " ".join(title_words[2:]).title()

    mileage = (
        int(mileage_match.group(1).replace(",", ""))
        if mileage_match
        else None
    )

    location_city = (
        clean_html(item.get("mapping_city"))
        or None
    )

    location_state = clean_html(
        item.get("auction_state")
        or item.get("seller_state")
    ).strip() or None

    return {
        "source_record_id": f"LSO:{item_id}",
        "item_id": str(item_id),
        "external_auction_id": str(
            item.get("auction_id") or ""
        ),
        "vin": vin_match.group(0).upper(),
        "model_year": year,
        "make": make,
        "model": model,
        "mileage": mileage,
        "current_bid": float(
            item.get("current_bid") or 0
        ),
        "location_city": location_city,
        "location_state": location_state,
        "provider_type": "LSO",
    }


def create_lso_session():
    """Create a browser-like session for LSO."""
    session = requests.Session()

    session.headers.update({
        "Accept": "application/json, text/plain, */*",
        "User-Agent": (
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) "
            "AppleWebKit/537.36 Chrome/151 Safari/537.36"
        ),
        "Origin": "https://www.lso.cc",
        "Referer": LSO_HOME_URL,
    })

    response = session.get(
        LSO_HOME_URL,
        timeout=20,
    )

    response.raise_for_status()

    session.cookies.set(
        "ckchk",
        "1",
        domain="www.lso.cc",
    )

    return session


def request_catalog_page(session, page):
    """Download one page of the LSO catalog."""
    response = session.post(
        CATALOG_URL,
        data={
            "page": page,
            "perpage": PER_PAGE,
        },
        timeout=30,
    )

    response.raise_for_status()
    page_data = response.json()

    if "items" not in page_data:
        raise RuntimeError(
            f"Unexpected LSO response: {page_data}"
        )

    return page_data


def scan_all_lso_cars():
    """Scan all LSO pages and save every valid vehicle."""
    db_helper.check_connection()
    session = create_lso_session()

    page = 1
    total_pages = 1

    saved = 0
    skipped = 0
    processed_item_ids = set()

    while page <= total_pages:
        page_data = request_catalog_page(
            session,
            page,
        )

        total_pages = int(
            page_data.get("total_pages", 1)
        )

        if total_pages > MAX_PAGES:
            raise RuntimeError(
                f"LSO returned {total_pages} pages. "
                f"Safety limit: {MAX_PAGES}."
            )

        items = page_data.get("items", [])

        for item in items:
            item_id = item.get("id")

            if item_id in processed_item_ids:
                continue

            processed_item_ids.add(item_id)

            car_data = extract_vehicle(item)

            if car_data is None:
                skipped += 1
                continue

            if db_helper.insert_or_update_car(car_data):
                saved += 1

        print(
            f"Finished page {page} of {total_pages}: "
            f"{len(items)} listings checked"
        )

        page += 1

        if page <= total_pages:
            time.sleep(REQUEST_DELAY_SECONDS)

    print(
        f"LSO scan complete: {saved} vehicles saved or updated, "
        f"{skipped} listings skipped."
    )


if __name__ == "__main__":
    scan_all_lso_cars()