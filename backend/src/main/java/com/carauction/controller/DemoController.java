package com.carauction.controller;

import com.carauction.dto.response.ImportResponse;
import com.carauction.dto.response.RecommendationResponse;
import com.carauction.service.AuctionListingImportService;
import com.carauction.service.DemoRecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demo")
@CrossOrigin(origins = "*")
public class DemoController {

    private final AuctionListingImportService importer;
    private final DemoRecommendationService recommendations;

    public DemoController(
            AuctionListingImportService importer,
            DemoRecommendationService recommendations) {
        this.importer = importer;
        this.recommendations = recommendations;
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
}
