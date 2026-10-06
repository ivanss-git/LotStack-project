from typing import List, Dict, Any
from app.sources.base import BaseScraper


class CopartScraper(BaseScraper):
    source_name = "copart"

    def fetch_listings(self) -> List[Dict[str, Any]]:
        mock_lots = [
            {
                "lot_id": "CP-442109",
                "vin": "1FA6P8CF0H5111111",
                "make": "Ford",
                "model": "Mustang",
                "model_year": 2017,
                "mileage": 45000,
                "current_bid": 15400.0,
                "location_city": "Dallas",
                "location_state": "TX",
            },
            {
                "lot_id": "CP-442110",
                "vin": "WBA3A5C58EF222222",
                "make": "BMW",
                "model": "328i",
                "model_year": 2014,
                "mileage": 82000,
                "current_bid": 6100.0,
                "location_city": "Arlington",
                "location_state": "TX",
            },
        ]

        records: List[Dict[str, Any]] = []
        for lot in mock_lots:
            records.append({
                "source_record_id": f"copart_{lot['lot_id']}",
                "item_id": lot["lot_id"],
                "external_auction_id": lot["lot_id"],
                "vin": lot["vin"],
                "model_year": lot["model_year"],
                "make": lot["make"],
                "model": lot["model"],
                "mileage": lot["mileage"],
                "current_bid": lot["current_bid"],
                "location_city": lot["location_city"],
                "location_state": lot["location_state"],
                "provider_type": self.source_name,
            })
        return records


if __name__ == "__main__":
    scraper = CopartScraper()
    scraper.run()
