package com.carauction.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name="vehicle_transport_estimate",schema="transport_schema")
public class VehicleTransportEstimateEntity extends BaseEntity {
// 1. Missing relationship to the vehicle/listing
    @OneToOne(fetch=FetchType.LAZY,optional=false)
    @JoinColumn(
        name="vehicle_id", 
        nullable=false, 
        unique=true,
        foreignKey=@ForeignKey(name="fk_transport_vehicle")
    )
    private VehicleEntity vehicle;

    @Column(name="pickup_location",nullable=false)
    private String pickupLocation;

    @Column(name="distance_in_miles",nullable=false)
    private Integer distanceInMiles;

    @Column(nullable=false)
    private String operability;

    @Column(name="trailer_type",nullable=false)
    private String trailerType;

    @Column(name="vehicle_type",nullable=false)
    private String vehicleType;

    @Column(name="additional_fees",nullable=false,precision=12,scale=2)
    private BigDecimal additionalFees;

    @Column(name="estimated_cost",nullable=false,precision=12,scale=2)
    private BigDecimal estimatedCost;

    // Required default constructor for JPA
    protected VehicleTransportEstimateEntity() {}

    // Full constructor for your service layer logic
    public VehicleTransportEstimateEntity(
            VehicleEntity vehicle,
            String pickup,
            Integer miles,
            String operability,
            String trailer,
            String type,
            BigDecimal fees,
            BigDecimal cost
    ) {
        this.vehicle = vehicle;
        update(pickup,miles,operability,trailer,type,fees,cost);
    }

    public void update(
        String pickup, 
        Integer miles,
        String operability,
        String trailer,
        String type,
        BigDecimal fees,
        BigDecimal cost
    ) {
        pickupLocation = pickup;
        distanceInMiles = miles;
        this.operability = operability;
        trailerType = trailer;
        vehicleType = type;
        additionalFees = fees;
        estimatedCost = cost;
    }

    public VehicleEntity getVehicle() { return vehicle;}
    public String getPickupLocation() { return pickupLocation;}
    public Integer getDistanceInMiles() { return distanceInMiles;}
    public String getOperability() { return operability;}
    public String getTrailerType() { return trailerType;}
    public String getVehicleType() { return vehicleType;}
    public BigDecimal getAdditionalFees() { return additionalFees;}
    public BigDecimal getEstimatedCost() { return estimatedCost;}
}