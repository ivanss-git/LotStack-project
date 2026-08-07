package com.carauction.service;

import com.carauction.entity.VehicleEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.OffsetDateTime;
import java.time.Year;


public class VehicleMarketComparisonService {

     /**
     * Finds regional lookalikes.
     */
    public List<VehicleEntity> findComparableVehicles(VehicleEntity vehicle) {
        return List.of();
    }

    /**
     * Standard rule-based mock: Estimates market price using age and odometer.
     */
    public double calculateMarketValue(VehicleEntity vehicle, List<VehicleEntity> comparableVehicles) {
        double baseValue = 35000.0; 
        
        int currentYear = Year.now().getValue();
        int vehicleAge = Math.max(0, currentYear - vehicle.getModelYear());
        double ageDepreciation = vehicleAge * 1500.0;

        double mileageDepreciation = vehicle.getOdometer() != null ? vehicle.getOdometer() * 0.12 : 0.0;

        double estimatedValue = baseValue - ageDepreciation - mileageDepreciation;
        return Math.max(500.0, estimatedValue);
    }
}
