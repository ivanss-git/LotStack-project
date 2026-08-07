CREATE SCHEMA IF NOT EXISTS vehicle_schema; 

CREATE TABLE vehicle_schema.vehicles (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    vin VARCHAR(17),

    model_year SMALLINT NOT NULL
        CONSTRAINT chk_vehicles_model_year
        CHECK (model_year BETWEEN 1886 AND 2100),

    keys_present BOOLEAN,

    odometer INTEGER
        CONSTRAINT chk_vehicles_odometer
        CHECK (odometer >= 0),

    make VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,

    body_type VARCHAR(100) NOT NULL
        CONSTRAINT chk_vehicles_body_type
        CHECK (body_type in (
            'SEDAN','COUPE','HATCHBACK','WAGON',
            'SUV','PICKUP_TRUCK','VAN','MINIVAN',
            'MOTORCYCLE','TRAILER','RV','SPORTS_CAR',
            'OTHER','UNKNOWN'
        )),

    trim VARCHAR(100),
    color VARCHAR(50),
    engine VARCHAR(100),

    transmission VARCHAR(50)
        CONSTRAINT chk_vehicles_transmission
        CHECK (transmission in (
            'AUTOMATIC',
            'MANUAL',
            'CVT',
            'OTHER',
            'UNKNOWN'
        )),

    drivetrain VARCHAR(20)
        CONSTRAINT chk_vehicles_drivetrain
        CHECK (drivetrain in (
            'FWD',
            'RWD',
            'AWD',
            '4WD',
            'OTHER',
            'UNKNOWN'
        )),

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_vehicles PRIMARY KEY (id),
    CONSTRAINT uk_vehicles_vin UNIQUE (vin)
);

CREATE INDEX idx_vehicles_make_model
    ON vehicle_schema.vehicles (make, model);

CREATE INDEX idx_vehicles_model_year
    ON vehicle_schema.vehicles (model_year);

    