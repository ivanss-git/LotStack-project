CREATE SCHEMA IF NOT EXISTS vehicle_schema; 

CREATE TABLE vehicle_schema.vehicles (
    id BIGSERIAL PRIMARY KEY,
    vin VARCHAR(17) UNIQUE,
    model_year SMALLINT NOT NULL
        CHECK (model_year BETWEEN 1886 AND 2100),
    keys_present BOOLEAN,
    odometer INTEGER CHECK (odometer >= 0),
    make VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    trim VARCHAR(100),
    color VARCHAR(50),
    engine VARCHAR(100),
    transmission VARCHAR(50),
    drivetrain VARCHAR(20),
    created_at TIMESTAMPZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

    