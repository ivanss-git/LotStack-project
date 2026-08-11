package com.carauction.dto.response;

import com.carauction.entity.VehicleTransportEstimateEntity; 
import java.math.BigDecimal;

public record TransportResponse(
    Long id,
    Long vehicleId,
    String pickupLocation,
    Integer distanceInMiles,
    String operability,
    String trailerType,
    String vehicleType,
    BigDecimal additionalFees,
    BigDecimal estimatedCost) {
        public static TransportResponse from(VehicleTransportEstimateEntity t) {
            return new TransportResponse(
                t.getId(),
                t.getVehicle().getId(),
                t.getPickupLocation(),
                t.getDistanceInMiles(),
                t.getOperability(),
                t.getTrailerType(),
                t.getVehicleType(),
                t.getAdditionalFees(),
                t.getEstimatedCost()
            );
        }
    }
