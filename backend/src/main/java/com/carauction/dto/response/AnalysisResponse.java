package com.carauction.dto.response;

import com.carauction.entity.AuctionAnalyzerEntity;
import java.math.BigDecimal;

public record AnalysisResponse (
    Long id,
    Long vehicleId,
    BigDecimal purchasePrice,
    BigDecimal estimatedMarketValue,
    BigDecimal estimatedRepairCost,
    BigDecimal transportCost,
    BigDecimal auctionFees,
    BigDecimal totalAdjustmentCost,
    BigDecimal profitGoal,
    BigDecimal totalCost,
    BigDecimal expectedProfit,
    BigDecimal roiPercent,
    BigDecimal maxBid,
    Boolean goodPurchase,
    String recommendation
) {
    public static AnalysisResponse from (AuctionAnalyzerEntity a) {
        BigDecimal roi = a.getTotalCost().signum()==0? 
            BigDecimal.ZERO: 
            a.getExpectedProfit()
            .multiply(BigDecimal.valueOf(100))
            .divide(a.getTotalCost(),2,java.math.RoundingMode.HALF_UP);

        return new AnalysisResponse(
            a.getId(),
            a.getVehicle().getId(),
            a.getPurchasePrice(),
            a.getEstimatedMarketValue(),
            a.getEstimatedRepairCost(),
            a.getTransportCost(),
            a.getAuctionFees(),
            a.getTitleAdjustmentCost(),
            a.getProfitGoal(),
            a.getTotalCost(),
            a.getExpectedProfit(),
            roi,
            a.getMaxBid(),
            a.getGoodPurchase(),
            a.getGoodPurchase() ? "Good Purchase" : "DO_NOT_BUY"
        );
    }
}
