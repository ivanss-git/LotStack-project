from typing import List, Dict, Any
from app.sources.base import BaseScraper


class BidMotorsScraper(BaseScraper):
    source_name = "bidmotors"

    def fetch_listings(self) -> List[Dict[str, Any]]:
        # Simulated scraped auction records from BidMotors
        mock_raw_items = [
            {"lot": "bm-501", "vin": "JN8AS5MV7FW888888", "yr": 2015, "mk": "Nissan", "md": "Rogue", "miles": 89000, "price": 3100.0, "loc": "Fort Worth", "st": "TX"},
            {"lot": "bm-502", "vin": "3FA6P0HD9HR999999", "yr": 2017, "mk": "Ford", "md": "Fusion", "miles": 73200, "price": 5400.0, "loc": "San Antonio", "state": "TX"},
        ]

        normalized: List[Dict[str, Any]] = []
        for item in mock_raw_items:
            normalized.append({
                "source_record_id": f"bidmotors_{item['lot']}",
                "item_id": item["lot"],
                "external_auction_id": f"bidmotors_lot_{item['lot']}",
                "vin": item["vin"],
                "model_year": item["yr"],
                "make": item["mk"],
                "model": item["md"],
                "mileage": item["miles"],
                "current_bid": item["price"],
                "location_city": item["loc"],
                "location_state": item.get("st") or item.get("state", "TX"),
                "provider_type": self.source_name,
            })
        return normalized


if __name__ == "__main__":
    scraper = BidMotorsScraper()
    scraper.run()