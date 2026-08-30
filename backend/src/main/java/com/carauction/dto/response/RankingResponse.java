package com.carauction.dto.response;

import com.carauction.service.RankingMetric;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public record RankingResponse(
    OffsetDateTime generatedAt,
    RankingMetric rankedBy,
    int sitesCompared,
    int vehiclesCompared,
    List<RankedListingResponse> globalTop10,
    Map<String, List<RankedListingResponse>> siteTop10
) {
}
