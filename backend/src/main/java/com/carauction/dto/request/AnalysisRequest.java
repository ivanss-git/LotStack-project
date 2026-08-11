package com.carauction.dto.request;

import jakarta.validation.constraints.*; 
import java.math.BigDecimal;

public record AnalysisRequest(
        @NotNull @PositiveOrZero BigDecimal purchasePrice,
        @NotNull @PositiveOrZero BigDecimal estimatedMarketValue,
        @NotNull @PositiveOrZero BigDecimal estimatedRepairCost,
        @NotNull @PositiveOrZero BigDecimal transportCost,
        @NotNull @PositiveOrZero BigDecimal auctionFees,
        @NotNull @PositiveOrZero BigDecimal titleAdjustmentCost,
        @NotNull @PositiveOrZero BigDecimal profitGoal
) {}

