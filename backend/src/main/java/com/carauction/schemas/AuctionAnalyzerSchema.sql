CREATE SCHEMA IF NOT EXISTS auction_analyzer_schema;

CREATE TABLE auction_analyzer_schema.auction_pricing(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    listing_id BIGINT NOT NULL,

    purchase_price NUMERIC(10, 2) NOT NULL
        CHECK (purchase_price >= 0),

    estimated_market_value NUMERIC(10, 2) NOT NULL
        CHECK (estimated_market_value >= 0),
    
    estimated_repair_cost NUMERIC(10, 2) NOT NULL
        CHECK (estimated_repair_cost >= 0),

    transport_cost NUMERIC(10, 2) NOT NULL
        CHECk (transport_cost >= 0),
    
    auction_fees NUMERIC(10, 2) NOT NULL
        CHECK (auction_fees >= 0),
    
    title_adjustment_cost NUMERIC(10, 2) NOT NULL
        CHECK (title_adjustment_cost >= 0),

    profit_goal NUMERIC(10, 2) NOT NULL
        CHECK (profit_goal >= 0),
    
    total_cost NUMERIC(10, 2) NOT NULL
        CHECK (total_cost >= 0),

    max_bid NUMERIC(10, 2) NOT NULL
        CHECK (max_bid >= 0)

    expected_profit NUMERIC(10, 2)

    is_good_purchase BOOLEAN NOT NULL
    
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_auction_pricing_listing
        FOREIGN KEY (listing_id)
        REFERENCES auction_schema.listings(id),
);