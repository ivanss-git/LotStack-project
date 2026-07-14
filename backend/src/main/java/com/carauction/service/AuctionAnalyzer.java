package com.carauction.service;
// should calculate market value, repair costs, max bid, and isGoodpurchase
import com.carauction.model.AnalysisResult;
import com.carauction.model.TransportEstimate;
import com.carauction.model.Vehicle;
import com.carauction.model.VehicleHistory;
import com.carauction.model.VehiclePaperwork;

import java.util.List;

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
        VehicleHistory history = historyService.fetchHistory(vehicle);
        VehiclePaperwork paperwork = paperworkService.fetchPaperwork(vehicle);
        TransportEstimate transportEstimate = transportService.estimateTransport(vehicle);
        List<Vehicle> comparableVehicles = marketService.findComparableVehicles(vehicle);
        
        double marketValue = marketService.calculateMarketValue(vehicle, comparableVehicles);
        double repairCost = historyService.calculateRepairCost(history);
        double titleFactor = paperworkService.calculateTitleFactor(paperwork);
        double transportCost = transportService.calculateTransportCost(transportEstimate);

        double maxBid = 0.0; // TODO
        boolean goodPurchase = false; // TODO

        return new AnalysisResult(
            vehicle.getVin(),
            marketValue,
            repairCost,
            transportCost,
            titleFactor,
            maxBid,
            goodPurchase
    );
    
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

