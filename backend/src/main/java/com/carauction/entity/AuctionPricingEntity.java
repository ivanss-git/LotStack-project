package com.carauction.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
    name = "auction_pricing",
    schema = "auction_analyzer_schema",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_auction_pricing_listing",
            columnNames = "listing_id"
        )
    }
)
public class AuctionPricingEntity extends BaseEntity {

    // Maps external listing table relationship
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "listing_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_auction_pricing_listing")
    )
    private VehicleEntity listing;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice; 

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedMarketValue; 

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedRepairCost; 

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal transportCost;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal auctionFees; 

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal titleAdjustmentCost; 

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal profitGoal; 

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost; 

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal maxBid; 

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal expectedProfit; // automatically maps to expected_profit

    @Column(nullable = false)
    private Boolean isGoodPurchase; // automatically maps to is_good_purchase

    // Required default constructor for JPA
    protected AuctionPricingEntity() {
        super();
    }

    // Full constructor for your service layer logic
    public AuctionPricingEntity(
        VehicleEntity listing,
        BigDecimal purchasePrice,
        BigDecimal estimatedMarketValue,
        BigDecimal estimatedRepairCost,
        BigDecimal transportCost,
        BigDecimal auctionFees,
        BigDecimal titleAdjustmentCost,
        BigDecimal profitGoal,
        BigDecimal totalCost,
        BigDecimal maxBid,
        BigDecimal expectedProfit,
        Boolean isGoodPurchase
    ) {
        this.listing = listing;
        this.purchasePrice = purchasePrice;
        this.estimatedMarketValue = estimatedMarketValue;
        this.estimatedRepairCost = estimatedRepairCost;
        this.transportCost = transportCost;
        this.auctionFees = auctionFees;
        this.titleAdjustmentCost = titleAdjustmentCost;
        this.profitGoal = profitGoal;
        this.totalCost = totalCost;
        this.maxBid = maxBid;
        this.expectedProfit = expectedProfit;
        this.isGoodPurchase = isGoodPurchase;
    }

    // Setters
    public void setListing(VehicleEntity listing) { this.listing = listing; }
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
    public void setIsGoodPurchase(Boolean isGoodPurchase) { this.isGoodPurchase = isGoodPurchase; }

    // Getters
    public VehicleEntity getListing() { return listing; }
    public BigDecimal getPurchasePrice() { return purchasePrice; }
    public BigDecimal getEstimatedMarketValue() { return estimatedMarketValue; }
    public BigDecimal getEstimatedRepairCost() { return estimatedRepairCost; }
    public BigDecimal getTransportCost() { return transportCost; }
    public BigDecimal getAuctionFees() { return auctionFees; }
    public BigDecimal getTitleAdjustmentCost() { return titleAdjustmentCost; }
    public BigDecimal getProfitGoal() { return profitGoal; }
    public BigDecimal getTotalCost() { return totalCost; }
    public BigDecimal getMaxBid() { return maxBid; }
    public BigDecimal getExpectedProfit() { return expectedProfit; }
    public Boolean getIsGoodPurchase() { return isGoodPurchase; }
}
