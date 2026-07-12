package com.carauction.service;
// should calculate market value, repair costs, max bid, and isGoodpurchase

import com.carauction.model.AnalysisResult;
import com.carauction.model.Vehicle;

public class AuctionAnalyzer {  

    private final PaperworkService paperworkService;
    private final HistoryService historyService;
    private final TransportService transportService;
    private final MarketComparisonService marketService;

    public AuctionAnalyzer( 
        PaperworkService paperworkService,
        HistoryService historyService,
        TransportService transportService,
        MarketComparisonService marketService
    ) {
        this.paperworkService = paperworkService;
        this.historyService = historyService;
        this.transportService = transportService;
        this.marketService = marketService;
    }

    public AnalysisResult analyze(Vehicle vehicle) {
        throw new UnsupportedOperationException("Logic not implemented yet.");
    }


    public double marketValue(Vehicle car) {
        double value = car.baseValue;
        int age = 2025 - car.year;
        value *= titleFactor(car);
        return Math.max(1000,value);

    }
    public double repairCost(Vehicle car) {
        double repairCost = 0.0;
        
        switch(car.damageType.toLowerCase()) {
            case "none": 
                repairCost = 0.0;
                break;
        }
        return repairCost;
    }
    public double titleFactor(Vehicle car) {
        switch(car.vinStatus.toLowerCase()) {
            case "salvage":
                return 0.80;
            case "rebuilt":
                return 0.90;
            default:
                return 1.0;
        }
    }
    public double maxBid(Vehicle car) {
        double value = marketValue(car);
        double cost = repairCost(car) + car.auctionFees + car.towFee;
        return Math.max(0, value - car.profitGoal - cost);
    }
}

