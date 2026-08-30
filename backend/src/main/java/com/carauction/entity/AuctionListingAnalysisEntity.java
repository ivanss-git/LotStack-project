package com.carauction.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
    name = "listing_analysis",
    schema = "auction_analyzer_schema"
)
public class AuctionListingAnalysisEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "listing_id",
        nullable = false,
        unique = true,
        foreignKey = @ForeignKey(
            name = "fk_listing_analysis_listing"
        )
    )
    private AuctionListingEntity listing;

    @Column(
        name = "estimated_purchase_price",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal estimatedPurchasePrice;

    @Column(
        name = "estimated_market_value",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal estimatedMarketValue;

    @Column(
        name = "estimated_repair_cost",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal estimatedRepairCost;

    @Column(
        name = "transport_cost",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal transportCost;

    @Column(
        name = "auction_fees",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal auctionFees;

    @Column(
        name = "title_adjustment_cost",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal titleAdjustmentCost;

    @Column(
        name = "total_cost",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal totalCost;

    @Column(
        name = "expected_profit",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal expectedProfit;

    @Column(
        name = "roi_percent",
        nullable = false,
        precision = 10,
        scale = 2
    )
    private BigDecimal roiPercent;

    @Column(
        name = "max_bid",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal maxBid;

    @Column(name = "opportunity_score", nullable = false)
    private Integer opportunityScore;

    @Column(
        name = "risk_level",
        nullable = false,
        length = 20
    )
    private String riskLevel;

    @Column(name = "is_good_purchase", nullable = false)
    private Boolean goodPurchase;

    protected AuctionListingAnalysisEntity() {}

    public AuctionListingAnalysisEntity(
        AuctionListingEntity listing
    ) {
        this.listing = listing;
    }

    public void update(
        BigDecimal purchasePrice,
        BigDecimal marketValue,
        BigDecimal repairCost,
        BigDecimal transport,
        BigDecimal fees,
        BigDecimal titleCost,
        BigDecimal total,
        BigDecimal profit,
        BigDecimal roi,
        BigDecimal maximumBid,
        int score,
        String risk,
        boolean good
    ) {
        estimatedPurchasePrice = purchasePrice;
        estimatedMarketValue = marketValue;
        estimatedRepairCost = repairCost;
        transportCost = transport;
        auctionFees = fees;
        titleAdjustmentCost = titleCost;
        totalCost = total;
        expectedProfit = profit;
        roiPercent = roi;
        maxBid = maximumBid;
        opportunityScore = score;
        riskLevel = risk;
        goodPurchase = good;
    }

    public AuctionListingEntity getListing() { return listing;}
    public BigDecimal getEstimatedMarketValue() {return estimatedMarketValue;}
    public BigDecimal getEstimatedRepairCost() {return estimatedRepairCost;}
    public BigDecimal getTransportCost() {return transportCost;}
    public BigDecimal getAuctionFees() {return auctionFees;}
    public BigDecimal getTitleAdjustmentCost() {return titleAdjustmentCost;}
    public BigDecimal getTotalCost() {return totalCost;}
    public BigDecimal getExpectedProfit() {return expectedProfit;}
    public BigDecimal getRoiPercent() {return roiPercent;}
    public BigDecimal getMaxBid() {return maxBid;}
    public Integer getOpportunityScore() {return opportunityScore;}
    public String getRiskLevel() {return riskLevel;}
    public Boolean getGoodPurchase() {return goodPurchase;}
    public BigDecimal getEstimatedPurchasePrice() { return estimatedPurchasePrice;}
}