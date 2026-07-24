CREATE SCHEMA IF NOT EXISTS vehicle_schema; 

CREATE TABLE vehicle_schema.attributes (
    id INT,
    vin INT,
    year INT,
    keys INT,
    odometer DOUBLE PRECISION,
    make VARCHAR(255),
    model VARCHAR(255),
    trim VARCHAR(255),
    color VARCHAR(255),
    engine VARCHAR(255),
    transmission VARCHAR(255),
    drivetrain VARCHAR(255)
    );
    