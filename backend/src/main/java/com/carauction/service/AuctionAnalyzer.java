package com.carauction.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import com.carauction.entity.AuctionAnalyzerEntity;
import com.carauction.entity.VehicleTransportEstimateEntity;
import com.carauction.entity.VehicleEntity;
import com.carauction.entity.VehicleHistoryEntity;
import com.carauction.entity.VehiclePaperworkEntity;

public class AuctionAnalyzer {  

    private final VehiclePaperworkService paperworkService;
    private final VehicleHistoryService historyService;
    private final VehicleTransportEstimateService transportEstimateService;
    private final VehicleMarketComparisonService marketComparisonService;

    public AuctionAnalyzer( 
        VehiclePaperworkService paperworkService,
        VehicleHistoryService historyService,
        VehicleTransportEstimateService transportEstimateService,
        VehicleMarketComparisonService marketComparisonService
    ) {
        this.paperworkService = paperworkService;
        this.historyService = historyService;
        this.transportEstimateService = transportEstimateService;
        this.marketComparisonService = marketComparisonService;
    }

    public AuctionAnalyzerEntity analyze(VehicleEntity vehicle, BigDecimal purchasePrice, BigDecimal targetProfitGoal, BigDecimal estimatedAuctionFees) {
        VehicleHistoryEntity history = historyService.fetchHistory(vehicle);
        VehiclePaperworkEntity paperwork = paperworkService.fetchPaperwork(vehicle);
        VehicleTransportEstimateEntity transportEstimate = transportEstimateService.fetchTransportEstimate(vehicle);
        List<VehicleEntity> comparableVehicles = marketComparisonService.findComparableVehicles(vehicle);

        double rawMarketValue = marketComparisonService.calculateMarketValue(vehicle, comparableVehicles);
        BigDecimal baseMarketValue = BigDecimal.valueOf(rawMarketValue).setScale(2, RoundingMode.HALF_UP);
        
        double rawRepairCost = historyService.calculateRepairCost(history);
        BigDecimal repairCost = BigDecimal.valueOf(rawRepairCost).setScale(2, RoundingMode.HALF_UP);

        double titleFactor = paperworkService.calculateTitleFactor(paperwork);
        BigDecimal titleMultiplier = BigDecimal.valueOf(titleFactor);

        BigDecimal transportCost = BigDecimal.ZERO;
        if (transportEstimate != null && transportEstimate.getEstimatedCost() != null) {
            transportCost = transportEstimate.getEstimatedCost();
        }

        BigDecimal adjustedMarketValue = baseMarketValue.multiply(titleMultiplier).setScale(2, RoundingMode.HALF_UP);

        BigDecimal titleAdjustmentCost = baseMarketValue.subtract(adjustedMarketValue).setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalFixedCosts = repairCost.add(transportCost).add(estimatedAuctionFees);
        BigDecimal totalCostWithPurchase = purchasePrice.add(totalFixedCosts);

        BigDecimal maxBid = calculateMaxBid(adjustedMarketValue, targetProfitGoal, totalFixedCosts);

        BigDecimal expectedProfit = adjustedMarketValue.subtract(totalCostWithPurchase);

        BigDecimal profitThreshold = targetProfitGoal.multiply(BigDecimal.valueOf(0.20));
    
        boolean isGoodPurchase = purchasePrice.compareTo(maxBid) <= 0 && expectedProfit.compareTo(profitThreshold) >= 0; 

        return new AuctionAnalyzerEntity(
            vehicle,
            purchasePrice,
            adjustedMarketValue,
            repairCost,
            transportCost,
            estimatedAuctionFees,
            titleAdjustmentCost,
            targetProfitGoal,
            totalCostWithPurchase,
            maxBid,
            expectedProfit,
            isGoodPurchase
        );
    }

    private BigDecimal calculateMaxBid(
        BigDecimal adjustedMarketValue,
        BigDecimal profitGoal,
        BigDecimal totalFixedCosts
    ) {

        BigDecimal calculatedBid = adjustedMarketValue.subtract(profitGoal).subtract(totalFixedCosts);

        if (calculatedBid.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return calculatedBid.setScale(2, RoundingMode.HALF_UP);
    }
}

