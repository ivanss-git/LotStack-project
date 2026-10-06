from abc import ABC, abstractmethod
from typing import Dict, Any, List
from app.db.session import insert_or_update_car


class BaseScraper(ABC):
    """Base interface for auction scrapers."""

    source_name: str = "generic"

    @abstractmethod
    def fetch_listings(self) -> List[Dict[str, Any]]:
        """Scrape or simulate fetching listings from the auction provider."""
        pass

    def run(self) -> int:
        """Run the scraper and persist extracted vehicles to PostgreSQL."""
        listings = self.fetch_listings()
        saved = 0
        for car in listings:
            if insert_or_update_car(car):
                saved += 1
        print(f"[{self.source_name.upper()}] Run complete: {saved} of {len(listings)} vehicles upserted.")
        return saved
