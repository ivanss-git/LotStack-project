from app.sources.bidcars import BidCarsScraper
from app.sources.bidmotors import BidMotorsScraper
from app.sources.copart import CopartScraper
from app.sources.lso import scan_all_lso_cars


def main():
    print("=== Starting Multi-Source Ingestion ===")
    
    # Class-based scrapers
    scrapers = [
        BidCarsScraper(),
        BidMotorsScraper(),
        CopartScraper(),
    ]
    
    for scraper in scrapers:
        scraper.run()
    
    # Legacy function-based LSO scraper
    print("\n[LSO] Running live auction collector...")
    scan_all_lso_cars()
    
    print("\n=== All Scrapers Finished ===")


if __name__ == "__main__":
    main()
