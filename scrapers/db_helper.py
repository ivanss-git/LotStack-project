import os

import psycopg2


DATABASE_URL = os.getenv(
    "DATABASE_URL",
    "postgresql:///car_auction?user=ivanibarra",
)


UPSERT_CAR_QUERY = """
    INSERT INTO auction_listing_schema.auction_listings (
        source_record_id,
        source_first_seen_at,
        source_last_seen_at,
        item_id,
        external_auction_id,
        vin,
        model_year,
        make,
        model,
        mileage,
        current_bid,
        location_city,
        location_state,
        provider_type,
        created_at,
        updated_at
    )
    VALUES (
        %(source_record_id)s,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        %(item_id)s,
        %(external_auction_id)s,
        %(vin)s,
        %(model_year)s,
        %(make)s,
        %(model)s,
        %(mileage)s,
        %(current_bid)s,
        %(location_city)s,
        %(location_state)s,
        %(provider_type)s,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    )
    ON CONFLICT (source_record_id)
    DO UPDATE SET
        source_last_seen_at = CURRENT_TIMESTAMP,
        item_id = EXCLUDED.item_id,
        external_auction_id = EXCLUDED.external_auction_id,
        vin = EXCLUDED.vin,
        model_year = EXCLUDED.model_year,
        make = EXCLUDED.make,
        model = EXCLUDED.model,
        mileage = EXCLUDED.mileage,
        current_bid = EXCLUDED.current_bid,
        location_city = EXCLUDED.location_city,
        location_state = EXCLUDED.location_state,
        provider_type = EXCLUDED.provider_type,
        updated_at = CURRENT_TIMESTAMP;
"""


def check_connection():
    """Confirm that the scraper can connect to PostgreSQL."""
    with psycopg2.connect(DATABASE_URL) as connection:
        with connection.cursor() as cursor:
            cursor.execute("SELECT current_database();")
            database_name = cursor.fetchone()[0]

    print(f"Connected to PostgreSQL database: {database_name}")


def insert_or_update_car(car_data):
    """Insert a listing or update it when it already exists."""
    try:
        with psycopg2.connect(DATABASE_URL) as connection:
            with connection.cursor() as cursor:
                cursor.execute(UPSERT_CAR_QUERY, car_data)

        return True

    except psycopg2.Error as error:
        print(
            f"Could not save "
            f"{car_data.get('source_record_id')}: {error}"
        )
        raise