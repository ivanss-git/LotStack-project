package com.carauction.dto.response;

import com.carauction.entity.AuctionListingAnalysisEntity;
import com.carauction.entity.AuctionListingEntity;

import java.math.BigDecimal;

public record RankedListingResponse(
    Long listingId,
    String sourceRecordId,
    String provider,
    String vin,
    Short year,
    String make,
    String model,
    Integer mileage,
    BigDecimal estimatedPurchasePrice,
    BigDecimal estimatedMarketValue,
    BigDecimal estimatedRepairCost,
    BigDecimal transportCost,
    BigDecimal auctionFees,
    BigDecimal totalCost,
    BigDecimal expectedProfit,
    BigDecimal roiPercent,
    BigDecimal recommendedMaxBid,
    Integer opportunityScore,
    String riskLevel,
    Boolean goodCandidate,
    String location
) {

    public static RankedListingResponse from(
        AuctionListingAnalysisEntity analysis
    ) {
        AuctionListingEntity listing =
            analysis.getListing();

        String location;

        if (listing.getLocationCity() == null) {
            location = listing.getLocationState();
        } else if (listing.getLocationState() == null) {
            location = listing.getLocationCity();
        } else {
            location = listing.getLocationCity()
                + ", "
                + listing.getLocationState();
        }

        return new RankedListingResponse(
            listing.getId(),
            listing.getSourceRecordId(),
            listing.getProviderType(),
            listing.getVin(),
            listing.getModelYear(),
            listing.getMake(),
            listing.getModel(),
            listing.getMileage(),
            analysis.getEstimatedPurchasePrice(),
            analysis.getEstimatedMarketValue(),
            analysis.getEstimatedRepairCost(),
            analysis.getTransportCost(),
            analysis.getAuctionFees(),
            analysis.getTotalCost(),
            analysis.getExpectedProfit(),
            analysis.getRoiPercent(),
            analysis.getMaxBid(),
            analysis.getOpportunityScore(),
            analysis.getRiskLevel(),
            analysis.getGoodPurchase(),
            location
        );
    }
}