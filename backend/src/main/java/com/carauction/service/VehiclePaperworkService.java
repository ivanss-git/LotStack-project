package com.carauction.service;

import com.carauction.entity.VehicleEntity;
import com.carauction.entity.VehiclePaperworkEntity;
import org.springframework.stereotype.Service;

@Service
public class VehiclePaperworkService {

    /**
     * Placeholder method to fetch paperwork details.
     */
    public VehiclePaperworkEntity fetchPaperwork(VehicleEntity vehicle) {
        // Returns null for now until you build a Paperwork entity/table
        return null; 
    }

    /**
     * Standard rule-based mock: Returns a market value multiplier.
     * Clean = 1.0 (100% value), Salvage = 0.50 (50% value drop).
     */
    public double calculateTitleFactor(VehiclePaperworkEntity paperwork) {
        // Defaulting to 1.0 (Clean Title) for testing purposes
        return 1.0; 
    }
}
