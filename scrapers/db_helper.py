import psycopg2
from psycopg2 import extras

# Use Render's external connection string directly (Cleanest method for psycopg2)
# Replace this with the actual External Database URL from your Render Dashboard
DATABASE_URL = "postgresql://ivanibarra:db_password@://render.com"

def insert_or_update_car(car_data):
    """
    Shares this exact saving function with every scraper you write.
    Targets custom auction_listing_schema explicitly.
    """
    conn = None
    cursor = None
    
    # Prefix the table with your active schema where the scraper tables live
    query = """
    INSERT INTO auction_listing_schema.auction_cars 
        (vin, make, model, year, price, mileage, source_site) 
    VALUES 
        (%(vin)s, %(make)s, %(model)s, %(year)s, %(price)s, %(mileage)s, %(source_site)s) 
    ON CONFLICT (vin) 
    DO UPDATE SET 
        price = EXCLUDED.price, 
        mileage = EXCLUDED.mileage, 
        last_updated = CURRENT_TIMESTAMP;
    """
    
    try:
        # Connect using the optimized connection string URL
        conn = psycopg2.connect(DATABASE_URL)
        cursor = conn.cursor()
        
        cursor.execute(query, car_data)
        conn.commit()
        print(f"Successfully processed VIN: {car_data.get('vin')}")
        
    except Exception as e:
        if conn:
            conn.rollback()
        print(f"Database error during scraper execution: {e}")
        
    finally:
        if cursor:
            cursor.close()
        if conn:
            conn.close()
