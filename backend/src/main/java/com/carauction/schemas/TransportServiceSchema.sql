CREATE SCHEMA IF NOT EXISTS transportation_schema;

CREATE TABLE transportation_schema.details(
    lot_fees DOUBLE PRECISION,
    body_type VARCHAR(255),
    pickup_location VARCHAR(255),
    operational_status VARCHAR(255),
);