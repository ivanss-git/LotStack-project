export type RankingMetric =
  | "OPPORTUNITY"
  | "ROI"
  | "PROFIT";

export type RiskLevel =
  | "LOW"
  | "MEDIUM"
  | "HIGH"
  | "UNKNOWN";

export interface RankedListing {
  listingId: number;
  sourceRecordId: string;
  provider: string;
  vin: string | null;
  year: number;
  make: string;
  model: string;
  mileage: number | null;
  estimatedPurchasePrice: number;
  estimatedMarketValue: number;
  estimatedRepairCost: number;
  transportCost: number;
  auctionFees: number;
  totalCost: number;
  expectedProfit: number;
  roiPercent: number;
  recommendedMaxBid: number;
  opportunityScore: number;
  riskLevel: RiskLevel;
  goodCandidate: boolean;
  location: string | null;
}

export interface RankingResponse {
  generatedAt: string;
  rankedBy: RankingMetric;
  sitesCompared: number;
  vehiclesCompared: number;
  globalTop10: RankedListing[];
  siteTop10: Record<string, RankedListing[]>;
}