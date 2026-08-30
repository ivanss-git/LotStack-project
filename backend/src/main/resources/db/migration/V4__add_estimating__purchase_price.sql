ALTER TABLE auction_analyzer_schema.listing_analysis
    ADD COLUMN IF NOT EXISTS estimated_purchase_price
        NUMERIC(12, 2);

UPDATE auction_analyzer_schema.listing_analysis
SET estimated_purchase_price = 0
WHERE estimated_purchase_price IS NULL;

ALTER TABLE auction_analyzer_schema.listing_analysis
    ALTER COLUMN estimated_purchase_price SET NOT NULL;