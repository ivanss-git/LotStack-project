CREATE SCHEMA IF NOT EXISTS paperwork_schema;

CREATE TABLE paperwork_schema.paperwork_information(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    listing_id BIGINT NOT NULL
    title_status VARCHAR(30) NOT NULL,
    title_state VARCHAR(2),
    lien_status VARCHAR(30),
    bill_of_sale_present BOOLEAN,
    auction_fees_receipt NUMERIC(10, 2)
        CHECK (auctions_fees_receipt >= 0),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_paperwork_listing
        FOREIGN KEY (listing_id),
        REFERENCES auction_schema.listins(id)
);