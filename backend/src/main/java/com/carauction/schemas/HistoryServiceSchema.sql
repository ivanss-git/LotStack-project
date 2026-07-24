CREATE SCHEMA IF NOT EXISTS history_schema;

CREATE TABLE history_schema.details(
    prior_owners INT,
    previous_accidents INT
    damage_code VARCHAR(255),
    drive_status VARCHAR(255),
    airbag_status VARCHAR(255)
);