package com.carauction.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
    name = "paperwork_information",
    schema = "paperwork_schema"
)

public class VehiclePaperworkEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "listing_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_paperwork_listing")
    )
    private VehicleEntity listing;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private String titleStatus;

    @Column(length = 2)
    private String titleState;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private String lienStatus;

    @Column
    private Boolean billOfSalePresent;

    @Column(precision = 10, scale = 2)
    private BigDecimal auctionFees;

    public VehiclePaperworkEntity() {
        super();
    }

    public VehiclePaperworkEntity(
        VehicleEntity listing,
        String titleStatus,
        String titleState,
        String lienStatus,
        Boolean billOfSalePresent,
        BigDecimal auctionFees
    ) {
        this.listing = listing;
        this.titleStatus = titleStatus;
        this.titleState = titleState;
        this.lienStatus = lienStatus;
        this.billOfSalePresent = billOfSalePresent;
        this.auctionFees = auctionFees;
    }

    public void setListing(VehicleEntity listing) { this.listing = listing;}

    public void setTitleStatus(String titleStatus) {
        this.titleStatus = titleStatus;
    }

    public void setTitleState(String titleState) {
        this.titleState = titleState;
    }

    public void setLienStatus(String lienStatus) {
        this.lienStatus = lienStatus;
    }

    public void setBillOfSalePresent(Boolean billOfSalePresent) {
        this.billOfSalePresent = billOfSalePresent;
    }

    public void setAuctionFees(BigDecimal auctionFees) {
        this.auctionFees = auctionFees;
    }

    public VehicleEntity getListing() { return listing;}
    public String getTitleStatus() { return titleStatus;}
    public String getTitleState() { return titleState;}
    public String getLienStatus() { return lienStatus;}
    public Boolean getBillOfSalePresent() { return billOfSalePresent;}
    public BigDecimal getAuctionFees() { return auctionFees;}
}

