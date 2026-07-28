CREATE SCHEMA IF NOT EXISTS market_comparison_schema;

CREATE TABLE market_comparison_schema.market_factors(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    listing_id BIGINT NOT NULL UNIQUE,

    market_days_supply NUMERIC(8, 2)
        CHECK (market_days_supply >= 0)

    purchase_price_low NUMERIC(10, 2)
        CHECK (market_low >= 0)    

    purchase_price_high NUMERIC(10, 2) 
        CHECK (market_high >= 0)

    sell_price_low NUMERIC(10, 2)
        CHECK (sell_price_low >= 0) 

    sell_price_high NUMERIC(10, 2)
        CHECK (sell_price_high >= 0)

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP

    CONSTRAINT chk_purchase_price_rang
        CHECK (
            purchase_price_low IS NULL
            OR purchase_price_high IS NULL
            OR purchase_price_low <= purchase_price_high
        );

    CONSTRAINT chk_sell_price_range
        CHECK (
            sell_price_low IS NULL
            OR sell_price_high IS NULL
            OR sell_price_low <= sell_price_high
        )

    CONSTRAINT fk_market_factor_listing
        FOREIGN KEY (listing_id)
        REFERENCES auction_schema.listings(id)
);