package com.carauction.model;

public class AnalysisResult {
    
    private final String vin;
    private final double marketValue;
    private final double repairCost;
    private final double transportCost;
    private final double titleFactor;
    private final double maxBid;
    private final boolean goodPurchase;

    public AnalysisResult(
        String vin,
        double marketValue,
        double repairCost,
        double transportCost,
        double titleFactor,
        double maxBid,
        boolean goodPurchase
    ) {
        this.vin = vin;
        this.marketValue = marketValue;
        this.repairCost = repairCost;
        this.transportCost = transportCost;
        this.titleFactor = titleFactor;
        this.maxBid = maxBid;
        this.goodPurchase = goodPurchase;
    }
    public String getVin() { return vin;} 
    public double getMarketValue() { return marketValue;}
    public double getRepairCost() { return repairCost;}
    public double getTransportCost() { return transportCost;}
    public double getTitleFactor() { return titleFactor;}
    public double getMaxBid() { return maxBid;}
    public boolean isGoodPurchase() { return goodPurchase;}
    
}
