package com.carauction.dto.request;

import jakarta.validation.constraints.*; 
import java.math.BigDecimal;

public record TransportRequest(
    @NotBlank String pickupLocation,
    @NotNull @PositiveOrZero Integer distanceInMiles,
    @NotBlank String operability,
    @NotBlank String trailerType,
    @NotBlank String vehicleType,
    @NotNull @PositiveOrZero BigDecimal additionalFees,
    @NotNull @PositiveOrZero BigDecimal estimatedCost
){}
