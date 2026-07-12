package com.carauction.service;
// should calculate market value, repair costs, max bid, and isGoodpurchase

import com.carauction.model.AnalysisResult;
import com.carauction.model.Vehicle;

// Should eventually look like : 
// public AnalysisResult analyze(Vehicle vehicle) {
//     double marketValue = marketService.calculateMarketValue(vehicle);
//     double repairCost = historyService.calculateRepairCost(vehicle);
//     double titleFactor = paperworkService.titleFactor(vehicle);
//     double transportCost = transportService.calculateTransportCost(vehicle);

//     double maxBid = marketValue
//                   - repairCost
//                   - transportCost
//                   - vehicle.auctionFees
//                   - vehicle.profitGoal;

//     return new AnalysisResult(...);
// }

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

    public PaperworkService getPaperWorkService() { return paperworkService;}
    public HistoryService getHsitoryService() { return historyService;}
    public TransportService getTransportService() { return transportService;}
    public MarketComparisonService getMarketComparisonService() { return marketService;}

    public AnalysisResult analyze(Vehicle vehicle) {
        throw new UnsupportedOperationException("Logic not implemented yet.");
    }


    public double marketValue(Vehicle car) {
        throw new UnsupportedOperationException("Logic not implemented yet.");
    }
    public double repairCost(Vehicle car) {
        throw new UnsupportedOperationException("Logic not implemented yet.");
    }
    public double titleFactor(Vehicle car) {
        throw new UnsupportedOperationException("Logic not implemented yet.");
        }

    public double maxBid(Vehicle car) {
        // double cost = repairCost(car) + car.auctionFees + car.towFee;
        // return Math.max(0, value - car.profitGoal - cost);
        throw new UnsupportedOperationException("Logic not implemented yet.");

    }
}

