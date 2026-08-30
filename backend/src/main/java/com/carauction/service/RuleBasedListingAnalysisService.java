package com.carauction.service;

import com.carauction.entity.AuctionListingAnalysisEntity;
import com.carauction.entity.AuctionListingEntity;
import com.carauction.repository.AuctionListingAnalysisRepository;
import com.carauction.repository.AuctionListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class RuleBasedListingAnalysisService {

    private static final BigDecimal MINIMUM_PROFIT =
        new BigDecimal("2500.00");

    private static final Set<String> SUPPORTED_MAKES = Set.of(
        "ACURA",
        "AUDI",
        "BMW",
        "BUICK",
        "CADILLAC",
        "CHEVROLET",
        "CHRYSLER",
        "DODGE",
        "FORD",
        "GENESIS",
        "GMC",
        "HONDA",
        "HYUNDAI",
        "INFINITI",
        "JEEP",
        "KIA",
        "LAND ROVER",
        "LEXUS",
        "LINCOLN",
        "MAZDA",
        "MERCEDES-BENZ",
        "MINI",
        "MITSUBISHI",
        "NISSAN",
        "PORSCHE",
        "RAM",
        "SUBARU",
        "TESLA",
        "TOYOTA",
        "VOLKSWAGEN",
        "VOLVO"
    );

    /*
     * Starting-price estimates by manufacturer.
     * These should later be replaced with model-specific
     * historical information or ML predictions.
     */
    private static final Map<String, BigDecimal> BASE_VALUES =
        Map.ofEntries(
            Map.entry("ACURA", new BigDecimal("42000")),
            Map.entry("AUDI", new BigDecimal("55000")),
            Map.entry("BMW", new BigDecimal("57000")),
            Map.entry("BUICK", new BigDecimal("37000")),
            Map.entry("CADILLAC", new BigDecimal("58000")),
            Map.entry("CHEVROLET", new BigDecimal("39000")),
            Map.entry("CHRYSLER", new BigDecimal("38000")),
            Map.entry("DODGE", new BigDecimal("42000")),
            Map.entry("FORD", new BigDecimal("41000")),
            Map.entry("GENESIS", new BigDecimal("51000")),
            Map.entry("GMC", new BigDecimal("48000")),
            Map.entry("HONDA", new BigDecimal("31000")),
            Map.entry("HYUNDAI", new BigDecimal("30000")),
            Map.entry("INFINITI", new BigDecimal("49000")),
            Map.entry("JEEP", new BigDecimal("44000")),
            Map.entry("KIA", new BigDecimal("30000")),
            Map.entry("LAND ROVER", new BigDecimal("65000")),
            Map.entry("LEXUS", new BigDecimal("52000")),
            Map.entry("LINCOLN", new BigDecimal("54000")),
            Map.entry("MAZDA", new BigDecimal("32000")),
            Map.entry("MERCEDES-BENZ", new BigDecimal("62000")),
            Map.entry("MINI", new BigDecimal("35000")),
            Map.entry("MITSUBISHI", new BigDecimal("29000")),
            Map.entry("NISSAN", new BigDecimal("33000")),
            Map.entry("PORSCHE", new BigDecimal("75000")),
            Map.entry("RAM", new BigDecimal("49000")),
            Map.entry("SUBARU", new BigDecimal("34000")),
            Map.entry("TESLA", new BigDecimal("50000")),
            Map.entry("TOYOTA", new BigDecimal("34000")),
            Map.entry("VOLKSWAGEN", new BigDecimal("34000")),
            Map.entry("VOLVO", new BigDecimal("52000"))
        );

    private final AuctionListingRepository listingRepository;
    private final AuctionListingAnalysisRepository analysisRepository;

    public RuleBasedListingAnalysisService(
        AuctionListingRepository listingRepository,
        AuctionListingAnalysisRepository analysisRepository
    ) {
        this.listingRepository = listingRepository;
        this.analysisRepository = analysisRepository;
    }

    public int analyzeAll() {
        int analyzed = 0;

        for (AuctionListingEntity listing
                : listingRepository.findAll()) {

            if (!isSupportedVehicle(listing)) {
                continue;
            }

            analyze(listing);
            analyzed++;
        }

        return analyzed;
    }

    public AuctionListingAnalysisEntity analyze(
        AuctionListingEntity listing
    ) {
        BigDecimal marketValue = money(
            estimateMarketValue(listing)
        );

        BigDecimal purchasePrice = money(
            estimatePurchasePrice(
                listing,
                marketValue
            )
        );

        BigDecimal repairCost = money(
            estimateRepairCost(listing)
        );

        BigDecimal transport = money(
            estimateTransport(listing)
        );

        BigDecimal fees = money(
            estimateAuctionFees(purchasePrice)
        );

        BigDecimal titleCost = money(
            estimateTitleCost(listing)
        );

        BigDecimal totalCost = money(
            purchasePrice
                .add(repairCost)
                .add(transport)
                .add(fees)
                .add(titleCost)
        );

        BigDecimal expectedProfit = money(
            marketValue.subtract(totalCost)
        );

        BigDecimal roi = totalCost.signum() == 0
            ? BigDecimal.ZERO
            : expectedProfit
                .multiply(new BigDecimal("100"))
                .divide(
                    totalCost,
                    2,
                    RoundingMode.HALF_UP
                );

        BigDecimal maxBid = money(
            marketValue
                .subtract(repairCost)
                .subtract(transport)
                .subtract(fees)
                .subtract(titleCost)
                .subtract(MINIMUM_PROFIT)
                .max(BigDecimal.ZERO)
        );

        int opportunityScore =
            calculateOpportunityScore(
                listing,
                expectedProfit,
                roi
            );

        double confidence =
            confidenceScore(listing);

        String riskLevel;

        if (confidence < 40) {
            riskLevel = "UNKNOWN";
        } else if (opportunityScore >= 70) {
            riskLevel = "LOW";
        } else if (opportunityScore >= 40) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "HIGH";
        }

        /*
         * This is a promising historical candidate,
         * not a final live-buy verdict.
         */
        boolean goodCandidate =
            confidence >= 25
            && expectedProfit.compareTo(
                MINIMUM_PROFIT
            ) >= 0
            && roi.compareTo(
                new BigDecimal("20")
            ) >= 0;

        AuctionListingAnalysisEntity analysis =
            analysisRepository
                .findByListing_Id(listing.getId())
                .orElseGet(
                    () -> new AuctionListingAnalysisEntity(
                        listing
                    )
                );

        analysis.update(
            purchasePrice,
            marketValue,
            repairCost,
            transport,
            fees,
            titleCost,
            totalCost,
            expectedProfit,
            roi,
            maxBid,
            opportunityScore,
            riskLevel,
            goodCandidate
        );

        return analysisRepository.save(analysis);
    }

    private boolean isSupportedVehicle(
        AuctionListingEntity listing
    ) {
        return SUPPORTED_MAKES.contains(
            upper(listing.getMake())
        );
    }

    private BigDecimal estimateMarketValue(
        AuctionListingEntity listing
    ) {
        BigDecimal baseValue = BASE_VALUES.getOrDefault(
            upper(listing.getMake()),
            new BigDecimal("35000")
        );

        int age = Math.max(
            0,
            Year.now().getValue()
                - listing.getModelYear()
        );

        double retainedValue;

        if (age == 0) {
            retainedValue = 0.92;
        } else if (age == 1) {
            retainedValue = 0.80;
        } else if (age == 2) {
            retainedValue = 0.70;
        } else {
            retainedValue = Math.max(
                0.18,
                0.70 * Math.pow(0.90, age - 2)
            );
        }

        return baseValue
            .multiply(
                BigDecimal.valueOf(retainedValue)
            )
            .multiply(
                BigDecimal.valueOf(
                    mileageFactor(listing.getMileage())
                )
            );
    }

    private double mileageFactor(Integer mileage) {
        if (mileage == null) return 0.90;
        if (mileage <= 30_000) return 1.10;
        if (mileage <= 75_000) return 1.00;
        if (mileage <= 125_000) return 0.90;
        if (mileage <= 175_000) return 0.78;

        return 0.65;
    }

    private BigDecimal estimatePurchasePrice(
        AuctionListingEntity listing,
        BigDecimal marketValue
    ) {
        String damage = upper(
            listing.getPrimaryDamage()
        );

        double ratio;

        if (damage.contains("FLOOD")
                || damage.contains("FIRE")
                || damage.contains("ROLLOVER")) {
            ratio = 0.15;
        } else if (damage.contains("MECHANICAL")) {
            ratio = 0.23;
        } else if (damage.contains("FRONT")) {
            ratio = 0.28;
        } else if (damage.contains("SIDE")
                || damage.contains("REAR")) {
            ratio = 0.32;
        } else if (damage.contains("HAIL")) {
            ratio = 0.38;
        } else if (damage.contains("MINOR")) {
            ratio = 0.42;
        } else if (damage.contains("NORMAL WEAR")) {
            ratio = 0.55;
        } else {
            ratio = 0.35;
        }

        String title = upper(
            listing.getTitleCode()
        );

        if ("JNK".equals(title)) {
            ratio = Math.min(ratio, 0.12);
        } else if ("BOS".equals(title)) {
            ratio = Math.min(ratio, 0.20);
        } else if ("SAL".equals(title)) {
            ratio = Math.min(ratio, 0.32);
        }

        return marketValue.multiply(
            BigDecimal.valueOf(ratio)
        );
    }

    private BigDecimal estimateRepairCost(
        AuctionListingEntity listing
    ) {
        String damage = upper(
            listing.getPrimaryDamage()
        );

        BigDecimal repair;

        if (damage.contains("FLOOD")) {
            repair = new BigDecimal("8500");
        } else if (damage.contains("FIRE")) {
            repair = new BigDecimal("9000");
        } else if (damage.contains("ROLLOVER")) {
            repair = new BigDecimal("7500");
        } else if (damage.contains("FRONT")) {
            repair = new BigDecimal("4000");
        } else if (damage.contains("REAR")) {
            repair = new BigDecimal("2500");
        } else if (damage.contains("SIDE")) {
            repair = new BigDecimal("2750");
        } else if (damage.contains("MECHANICAL")) {
            repair = new BigDecimal("3500");
        } else if (damage.contains("HAIL")) {
            repair = new BigDecimal("1600");
        } else if (damage.contains("MINOR")) {
            repair = new BigDecimal("900");
        } else if (damage.contains("NORMAL WEAR")) {
            repair = new BigDecimal("500");
        } else {
            repair = new BigDecimal("2500");
        }

        if (listing.getSecondaryDamage() != null) {
            repair = repair.add(
                new BigDecimal("900")
            );
        }

        if (equalsIgnoreCase(
                listing.getAirbagState(),
                "Deployed"
        )) {
            repair = repair.add(
                new BigDecimal("2000")
            );
        }

        if (Boolean.FALSE.equals(
                listing.getHasKeys()
        )) {
            repair = repair.add(
                new BigDecimal("250")
            );
        }

        if (Boolean.FALSE.equals(
                listing.getRunAndDrive()
        )) {
            repair = repair.add(
                new BigDecimal("1800")
            );
        }

        return repair;
    }

    private BigDecimal estimateAuctionFees(
        BigDecimal purchasePrice
    ) {
        BigDecimal rate;

        if (purchasePrice.compareTo(
                new BigDecimal("2500")
            ) < 0) {
            rate = new BigDecimal("0.15");
        } else if (
            purchasePrice.compareTo(
                new BigDecimal("10000")
            ) < 0
        ) {
            rate = new BigDecimal("0.12");
        } else {
            rate = new BigDecimal("0.10");
        }

        return purchasePrice
            .multiply(rate)
            .add(new BigDecimal("200"));
    }

    private BigDecimal estimateTransport(
        AuctionListingEntity listing
    ) {
        String state = upper(
            listing.getLocationState()
        );

        if ("TX".equals(state)) {
            return new BigDecimal("300");
        }

        if (state.equals("OK")
                || state.equals("AR")
                || state.equals("LA")
                || state.equals("NM")) {
            return new BigDecimal("650");
        }

        return new BigDecimal("1100");
    }

    private BigDecimal estimateTitleCost(
        AuctionListingEntity listing
    ) {
        String title = upper(
            listing.getTitleCode()
        );

        return switch (title) {
            case "CLR", "ORG" ->
                BigDecimal.ZERO;

            case "SAL" ->
                new BigDecimal("500");

            case "BOS" ->
                new BigDecimal("1200");

            case "JNK" ->
                new BigDecimal("2500");

            default ->
                new BigDecimal("750");
        };
    }

    private int calculateOpportunityScore(
        AuctionListingEntity listing,
        BigDecimal profit,
        BigDecimal roi
    ) {
        double roiPoints =
            clamp(roi.doubleValue(), 0, 100)
                * 0.40;

        double profitPoints =
            clamp(
                profit.doubleValue()
                    / 10_000.0
                    * 100,
                0,
                100
            ) * 0.30;

        double conditionPoints =
            conditionScore(listing) * 0.20;

        double confidence =
            confidenceScore(listing);

        double confidencePoints =
            confidence * 0.10;

        double rawScore =
            roiPoints
            + profitPoints
            + conditionPoints
            + confidencePoints;

        double confidenceMultiplier =
            0.50 + confidence / 200.0;

        return (int) Math.round(
            clamp(
                rawScore * confidenceMultiplier,
                0,
                100
            )
        );
    }

    private double conditionScore(
        AuctionListingEntity listing
    ) {
        double score = 50;

        if (Boolean.TRUE.equals(
                listing.getRunAndDrive()
        )) {
            score += 20;
        } else if (Boolean.FALSE.equals(
                listing.getRunAndDrive()
        )) {
            score -= 20;
        }

        if (Boolean.TRUE.equals(
                listing.getHasKeys()
        )) {
            score += 10;
        } else if (Boolean.FALSE.equals(
                listing.getHasKeys()
        )) {
            score -= 10;
        }

        if (equalsIgnoreCase(
                listing.getAirbagState(),
                "Intact"
        )) {
            score += 10;
        } else if (equalsIgnoreCase(
                listing.getAirbagState(),
                "Deployed"
        )) {
            score -= 20;
        }

        String damage = upper(
            listing.getPrimaryDamage()
        );

        if (damage.contains("FLOOD")
                || damage.contains("FIRE")) {
            score -= 35;
        } else if (damage.contains("FRONT")) {
            score -= 15;
        } else if (damage.contains("MINOR")
                || damage.contains("NORMAL WEAR")) {
            score += 10;
        }

        return clamp(score, 0, 100);
    }

    private double confidenceScore(
        AuctionListingEntity listing
    ) {
        int present = 0;
        int total = 7;

        if (listing.getVin() != null) present++;
        if (listing.getMileage() != null) present++;
        if (listing.getPrimaryDamage() != null) present++;
        if (listing.getTitleCode() != null) present++;
        if (listing.getHasKeys() != null) present++;
        if (listing.getRunAndDrive() != null) present++;
        if (listing.getLocationState() != null) present++;

        return present * 100.0 / total;
    }

    private static BigDecimal money(
        BigDecimal value
    ) {
        return value.setScale(
            2,
            RoundingMode.HALF_UP
        );
    }

    private static String upper(String value) {
        return value == null
            ? ""
            : value
                .trim()
                .toUpperCase(Locale.ROOT);
    }

    private static boolean equalsIgnoreCase(
        String left,
        String right
    ) {
        return left != null
            && left.equalsIgnoreCase(right);
    }

    private static double clamp(
        double value,
        double minimum,
        double maximum
    ) {
        return Math.max(
            minimum,
            Math.min(value, maximum)
        );
    }
}