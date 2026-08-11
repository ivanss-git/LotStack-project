package com.carauction.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="auction_pricing",schema="auction_analyzer_schema")
public class AuctionAnalyzerEntity extends BaseEntity {

    // Maps external listing table relationship
    @OneToOne(fetch=FetchType.LAZY,optional=false)
    @JoinColumn(name="vehicle_id",nullable=false,foreignKey=@ForeignKey(name="fk_auction_pricing_listing"))
    private VehicleEntity vehicle;

    @Column(name="purchase_price",nullable=false,precision=12, scale=2)
    private BigDecimal purchasePrice; 

    @Column(name="estimated_market_value",nullable=false,precision=12,scale=2)
    private BigDecimal estimatedMarketValue; 

    @Column(name="estimated_repair_cost",nullable=false,precision=12,scale=2)
    private BigDecimal estimatedRepairCost; 

    @Column(name="transport_cost",nullable=false,precision=12,scale=2)
    private BigDecimal transportCost;

    @Column(name="auction_fees",nullable=false,precision=12,scale=2)
    private BigDecimal auctionFees; 

    @Column(name="title_adjustment_cost",nullable=false,precision=12,scale=2)
    private BigDecimal titleAdjustmentCost; 

    @Column(name="profit_goal",nullable=false,precision=10,scale=2)
    private BigDecimal profitGoal; 

    @Column(name="total_cost",nullable=false,precision=12,scale=2)
    private BigDecimal totalCost; 

    @Column(name="max_bid",nullable=false,precision=12,scale=2)
    private BigDecimal maxBid; 

    @Column(name="expected_profit",nullable=false,precision=12,scale=2)
    private BigDecimal expectedProfit; 

    @Column(name="is_good_purchase",nullable=false)
    private Boolean goodPurchase; 

    // Required default constructor for JPA
    protected AuctionAnalyzerEntity() {}

    // Full constructor for your service layer logic
    public AuctionAnalyzerEntity(
        VehicleEntity v,
        BigDecimal purchase,
        BigDecimal market,
        BigDecimal repair,
        BigDecimal transport,
        BigDecimal fee,
        BigDecimal title,
        BigDecimal goal,
        BigDecimal total,
        BigDecimal max,
        BigDecimal profit,
        Boolean good
    ) {
        vehicle=v;
        update(purchase,market,repair,transport,fee,title,goal,total,max,profit,good);
        
    }

    public void update(
        BigDecimal purchase,
        BigDecimal market,
        BigDecimal repair,
        BigDecimal transport,
        BigDecimal fees,
        BigDecimal title,
        BigDecimal goal,
        BigDecimal total,
        BigDecimal max,
        BigDecimal profit,
        Boolean good) {

            purchasePrice=purchase;
            estimatedMarketValue=market;
            estimatedRepairCost=repair;
            transportCost=transport;
            auctionFees=fees;
            titleAdjustmentCost=title;
            profitGoal=goal;
            totalCost=total;
            maxBid=max;
            expectedProfit=profit;
            goodPurchase=good;
        }

    // Getters
    public VehicleEntity getVehicle() { return vehicle;}
    public BigDecimal getPurchasePrice() { return purchasePrice;}
    public BigDecimal getEstimatedMarketValue() { return estimatedMarketValue;}
    public BigDecimal getEstimatedRepairCost() { return estimatedRepairCost;}
    public BigDecimal getTransportCost() { return transportCost;}
    public BigDecimal getAuctionFees() { return auctionFees;}
    public BigDecimal getTitleAdjustmentCost() { return titleAdjustmentCost;}
    public BigDecimal getProfitGoal() { return profitGoal;}
    public BigDecimal getTotalCost() { return totalCost;}
    public BigDecimal getMaxBid() { return maxBid;}
    public BigDecimal getExpectedProfit() { return expectedProfit;}
    public Boolean getGoodPurchase() { return goodPurchase;}

    // setters
    public void setVehicle(VehicleEntity vehicle) { this.vehicle = vehicle; }
    public void setPurchasePrice(BigDecimal purchasePrice) { this.purchasePrice = purchasePrice; }
    public void setEstimatedMarketValue(BigDecimal estimatedMarketValue) { this.estimatedMarketValue = estimatedMarketValue; }
    public void setEstimatedRepairCost(BigDecimal estimatedRepairCost) { this.estimatedRepairCost = estimatedRepairCost; }
    public void setTransportCost(BigDecimal transportCost) { this.transportCost = transportCost; }
    public void setAuctionFees(BigDecimal auctionFees) { this.auctionFees = auctionFees; }
    public void setTitleAdjustmentCost(BigDecimal titleAdjustmentCost) { this.titleAdjustmentCost = titleAdjustmentCost; }
    public void setProfitGoal(BigDecimal profitGoal) { this.profitGoal = profitGoal; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public void setMaxBid(BigDecimal maxBid) { this.maxBid = maxBid; }
    public void setExpectedProfit(BigDecimal expectedProfit) { this.expectedProfit = expectedProfit; }
    public void setGoodPurchase(Boolean goodPurchase) { this.goodPurchase = goodPurchase; }    
}
