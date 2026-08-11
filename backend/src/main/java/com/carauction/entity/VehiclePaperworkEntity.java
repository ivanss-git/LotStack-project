package com.carauction.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="paperwork_information",schema="paperwork_schema")
public class VehiclePaperworkEntity extends BaseEntity {

    @OneToOne(fetch=FetchType.LAZY,optional=false)
    @JoinColumn(
        name="vehicle_id",
        nullable=false,
        unique=true,
        foreignKey=@ForeignKey(name="fk_paperwork_listing")
    )
    private VehicleEntity vehicle;

    @Column(name="title_status",nullable=false,length=30)
    private String titleStatus;

    @Column(name="title_state",length=2)
    private String titleState;

    @Column(name="lien_status",length=30)
    private String lienStatus;

    @Column(name="bill_of_sale_present")
    private Boolean billOfSalePresent;

    @Column(name="auction_fees",precision=12,scale=2)
    private BigDecimal auctionFees;

    protected VehiclePaperworkEntity() {}

    public VehiclePaperworkEntity(
        VehicleEntity vehicle,
        String titleStatus,
        String titleState,
        String lienStatus,
        Boolean bill,
        BigDecimal fees
    ) {
        this.vehicle = vehicle;
        update(titleStatus,titleState,lienStatus,bill,fees);
    }

    public void update(String titleStatus, String titleState, String lienStatus, Boolean bill, BigDecimal fees) {
        this.titleStatus = titleStatus;
        this.titleState = titleState;
        this.lienStatus = lienStatus;
        billOfSalePresent = bill;
        auctionFees = fees;
    }

    public VehicleEntity getVehicle() { return vehicle;}
    public String getTitleStatus() { return titleStatus;}
    public String getTitleState() { return titleState;}
    public String getLienStatus() { return lienStatus;}
    public Boolean getBillOfSalePresent() { return billOfSalePresent;}
    public BigDecimal getAuctionFees() { return auctionFees;}
}

