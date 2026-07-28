CREATE SCHEMA IF NOT EXISTS vehicle_schema; 

CREATE TABLE vehicle_schema.vehicles (
    id BIGINT GENERATED ALWAYS AS PRIMARY KEY,
    vin VARCHAR(17) UNIQUE,

    model_year SMALLINT NOT NULL
        CHECK (model_year BETWEEN 1886 AND 2100),

    keys_present BOOLEAN,

    odometer INTEGER
        CHECK (odometer >= 0),

    make VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,

    body_type VARCHAR(100) NOT NULL
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
        CHECK (transmission in (
            'AUTOMATIC','MANUAL','CVT','OTHER','UNKNOWN'
        ))

    drivetrain VARCHAR(20)
        CHECK (drivetrain in (
            'FWD','RWD','AWD','4WD','OTHER','UNKNOWN'
        )),

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

    