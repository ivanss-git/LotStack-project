CREATE SCHEMA IF NOT EXISTS transportation_schema;

CREATE TABLE transportation_schema.transport_estimates(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    listing_id BIGINT NOT NULL,
    pickup_location VARCHAR(150) NOT NULL,
    distance_miles INTEGER CHECK (distance_miles >= 0),
    operational_status VARCHAR(30) NOT NULL,
    estimated_cost NUMERIC(10, 2) NOT NULL
    CHECK (estimated_cost >= 0),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_transport_listing
        FOREIGN KEY (listing_id),
        REFERENCES auction_schema.listings(id)
);