from typing import List, Dict, Any
from app.sources.base import BaseScraper


class BidCarsScraper(BaseScraper):
    source_name = "bidcars"

    def fetch_listings(self) -> List[Dict[str, Any]]:
        # Simulated scraped auction records from BidCars
        mock_raw_items = [
            {"id": "bc-9901", "vin": "1G1BE5SM7H7123456", "year": 2018, "make": "Chevrolet", "model": "Cruze", "odometer": 62000, "bid": 4200.0, "city": "Houston", "state": "TX"},
            {"id": "bc-9902", "vin": "2T1BURHE9KC654321", "year": 2019, "make": "Toyota", "model": "Corolla", "odometer": 41500, "bid": 7800.0, "city": "Austin", "state": "TX"},
        ]

        normalized: List[Dict[str, Any]] = []
        for item in mock_raw_items:
            normalized.append({
                "source_record_id": f"bidcars_{item['id']}",
                "item_id": item["id"],
                "external_auction_id": f"bidcars_lot_{item['id']}",
                "vin": item["vin"],
                "model_year": item["year"],
                "make": item["make"],
                "model": item["model"],
                "mileage": item["odometer"],
                "current_bid": item["bid"],
                "location_city": item["city"],
                "location_state": item["state"],
                "provider_type": self.source_name,
            })
        return normalized


if __name__ == "__main__":
    scraper = BidCarsScraper()
    scraper.run()