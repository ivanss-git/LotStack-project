package com.carauction.service;

import com.carauction.entity.VehicleEntity;
import com.carauction.entity.VehicleTransportEstimateEntity;
import org.springframework.stereotype.Service;

@Service
public class  VehicleTransportEstimateService {
    /**
     * Placeholder method to fetch logistical distances.
     */
    public VehicleTransportEstimateEntity fetchTransportEstimate(VehicleEntity vehicle) {
        return null;
    }

    /**
     * Standard rule-based mock: Extracts transport cost from entity if present,
     * otherwise falls back to a flat-rate shipping fee.
     */
    public double calculateTransportEstimate(VehicleTransportEstimateEntity transportEstimate) {
        if (transportEstimate != null && transportEstimate.getEstimatedCost() != null) {
            return transportEstimate.getEstimatedCost().doubleValue();
        }
        // Fallback flat shipping rate of $450.00 for testing
        return 450.00;
    }
}
