package com.carauction.service;

import com.carauction.dto.response.RecommendationResponse;
import com.carauction.entity.AuctionListingEntity;
import com.carauction.repository.AuctionListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class DemoRecommendationService {

    private final AuctionListingRepository repository;

    public DemoRecommendationService(AuctionListingRepository repository) {
        this.repository = repository;
    }

    public List<RecommendationResponse> recommendations(int requestedLimit) {
        int limit = Math.max(1, Math.min(requestedLimit, 200));
        return repository.findAll().stream()
                .map(this::score)
                .sorted(Comparator.comparingInt(RecommendationResponse::opportunityScore).reversed()
                        .thenComparing(RecommendationResponse::auctionDateTime,
                                Comparator.nullsLast(Comparator.naturalOrder())))
                .limit(limit)
                .toList();
    }

    RecommendationResponse score(AuctionListingEntity listing) {
        int score = 50;
        List<String> reasons = new ArrayList<>();

        if (Boolean.TRUE.equals(listing.getRunAndDrive())) {
            score += 15;
            reasons.add("Runs and drives");
        } else {
            score -= 12;
            reasons.add("Does not run and drive");
        }
        if (Boolean.TRUE.equals(listing.getHasKeys())) {
            score += 8;
            reasons.add("Keys present");
        } else {
            score -= 8;
            reasons.add("Keys missing");
        }
        if (equalsIgnoreCase(listing.getAirbagState(), "Intact")) {
            score += 7;
            reasons.add("Airbags intact");
        } else if (equalsIgnoreCase(listing.getAirbagState(), "Deployed")) {
            score -= 12;
            reasons.add("Airbags deployed");
        }
        if (equalsIgnoreCase(listing.getOdometerBrand(), "ACTUAL")) {
            score += 5;
            reasons.add("Actual mileage reported");
        } else {
            score -= 5;
            reasons.add("Mileage status is not actual");
        }
        if (listing.getMileage() != null && listing.getMileage() <= 50_000) {
            score += 5;
            reasons.add("Lower mileage");
        } else if (listing.getMileage() != null && listing.getMileage() >= 150_000) {
            score -= 8;
            reasons.add("High mileage");
        }

        String title = upper(listing.getTitleCode());
        if ("CLR".equals(title) || "ORG".equals(title)) {
            score += 5;
            reasons.add("More favorable title code: " + title);
        } else if ("SAL".equals(title)) {
            score -= 10;
            reasons.add("Salvage title");
        } else if ("BOS".equals(title)) {
            score -= 15;
            reasons.add("Bill of sale only");
        } else if ("JNK".equals(title)) {
            score -= 20;
            reasons.add("Junk title");
        }

        String damage = upper(listing.getPrimaryDamage());
        if (damage.contains("FLOOD") || equalsIgnoreCase(listing.getLossType(), "Water")) {
            score -= 20;
            reasons.add("Water/flood risk");
        } else if (equalsIgnoreCase(listing.getLossType(), "Fire")) {
            score -= 20;
            reasons.add("Fire damage risk");
        } else if (damage.contains("FRONT END") || damage.contains("FRONT & REAR")) {
            score -= 7;
            reasons.add("Front-impact damage");
        } else if (!damage.isBlank()) {
            score -= 3;
            reasons.add("Reported damage: " + listing.getPrimaryDamage());
        }
        if (listing.getSecondaryDamage() != null) {
            score -= 5;
            reasons.add("Secondary damage reported");
        }

        score = Math.max(0, Math.min(score, 100));
        String risk = score >= 70 ? "LOW" : score >= 40 ? "MEDIUM" : "HIGH";
        String location = joinLocation(listing.getLocationCity(), listing.getLocationState());

        return new RecommendationResponse(
                listing.getSourceRecordId(), listing.getStockNumber(), listing.getModelYear(),
                listing.getMake(), listing.getModel(), listing.getSeries(), listing.getBodyStyle(),
                listing.getMileage(), listing.getPrimaryDamage(), listing.getSecondaryDamage(),
                listing.getTitleType(), listing.getTitleCode(), listing.getRunAndDrive(),
                listing.getHasKeys(), listing.getAirbagState(), listing.getAuctionDateTime(),
                listing.getBranchName(), location, score, risk, List.copyOf(reasons));
    }

    private static String upper(String value) {
        return value == null ? "" : value.toUpperCase(Locale.ROOT);
    }

    private static boolean equalsIgnoreCase(String left, String right) {
        return left != null && left.equalsIgnoreCase(right);
    }

    private static String joinLocation(String city, String state) {
        if (city == null) return state;
        if (state == null) return city;
        return city + ", " + state;
    }
}
