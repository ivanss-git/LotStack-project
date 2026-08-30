package com.carauction.controller;

import com.carauction.dto.response.ImportResponse;
import com.carauction.dto.response.RecommendationResponse;
import com.carauction.service.AuctionListingImportService;
import com.carauction.service.DemoRecommendationService;
import org.springframework.web.bind.annotation.*;
import com.carauction.dto.response.RankingResponse;
import com.carauction.service.ListingRankingService;
import com.carauction.service.RankingMetric;
import com.carauction.service.RuleBasedListingAnalysisService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/demo")
@CrossOrigin(origins = "*")
public class DemoController {

    private final AuctionListingImportService importer;
    private final DemoRecommendationService recommendations;
    private final RuleBasedListingAnalysisService listingAnalyzer;
    private final ListingRankingService listingRankings;

    public DemoController(
    AuctionListingImportService importer,
    DemoRecommendationService recommendations,
    RuleBasedListingAnalysisService listingAnalyzer,
    ListingRankingService listingRankings
) {
    this.importer = importer;
    this.recommendations = recommendations;
    this.listingAnalyzer = listingAnalyzer;
    this.listingRankings = listingRankings;
}

    @PostMapping("/import")
    public ImportResponse importDataset() {
        return importer.importConfiguredFile();
    }

    @GetMapping("/recommendations")
    public List<RecommendationResponse> recommendations(
            @RequestParam(defaultValue = "20") int limit) {
        return recommendations.recommendations(limit);
    }

    @PostMapping("/listing-analysis/refresh")
public Map<String, Integer> refreshListingAnalysis() {
    int analyzed = listingAnalyzer.analyzeAll();

    return Map.of("analyzed", analyzed);
}

@GetMapping("/rankings")
public RankingResponse rankings(
    @RequestParam(defaultValue = "10") int limit,
    @RequestParam(
        defaultValue = "OPPORTUNITY"
    ) RankingMetric sortBy
) {
    return listingRankings.rankings(
        sortBy,
        limit
    );
}
}
