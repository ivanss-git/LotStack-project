CREATE SCHEMA IF NOT EXISTS history_schema;

CREATE TABLE history_schema.history_information(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    listing_id BIGINT NOT NULL UNIQUE,

    prior_owners INTEGER
        CHECK (prior_owners >= 0),

    previous_accidents INTEGER
        CHECK (previous_accident >= 0),

    damage_code VARCHAR(50)
        CHECK (damage_code in (
            'FRONT_END','REAR_END','SIDE','STRUCTURAL',
            'ALL_OVER','ROLLOVER','UNDERCARRIAGE','MECHANICAL',
            'DENTS','SCRATCHES','NORMAL_WEAR','FLOOD',
            'HAIL','BURN','VANDALISM','BIOHAZARDOUS',
            'THEFT','UNKNOWN'
        )),

    drive_status VARCHAR(50) NOT NULL DEFAULT 'UNKNOWN'
        CEHCK (drive_status in (
            'RUNS','RUNS_AND_DRIVES','NON_RUNNING','AS_IS','UNKNOWN'
        )),

    airbag_status VARCHAR(30)
        CHECK (airbag_status in (
            'INTACT','DEPLOYED','MISSING','UNKNOWN'
        )),

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_history_listing
        FOREIGN KEY (listing_id)
        REFERENCES auction_schema.listings(id)
);