import type {
  RankingMetric,
  RankingResponse,
} from "./rankingTypes";

const API_URL = "http://localhost:8080";

export async function getRankings(
  sortBy: RankingMetric = "OPPORTUNITY",
  limit = 10,
): Promise<RankingResponse> {
  const parameters = new URLSearchParams({
    limit: String(limit),
    sortBy,
  });

  const response = await fetch(
    `${API_URL}/api/demo/rankings?${parameters.toString()}`,
  );

  if (!response.ok) {
    throw new Error(
      `Backend returned status ${response.status}.`,
    );
  }

  return response.json() as Promise<RankingResponse>;
}