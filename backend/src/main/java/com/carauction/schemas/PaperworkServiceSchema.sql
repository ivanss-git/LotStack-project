CREATE SCHEMA IF NOT EXISTS paperwork_schema;

CREATE TABLE paperwork_schema.details(
    title_status VARCHAR(255),
    title_state VARCHAR(255),
    lien_status VARCHAR(255),
    bill_of_sale VARCHAR(255),
    auction_fees_receipt DOUBLE PRECISION
);