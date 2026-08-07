package com.carauction.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(
    name = "vehicle_transport_estimate",
    schema = "vehicle_transport_estimate_schema"
)

public class VehicleTransportEstimateEntity extends BaseEntity {
// 1. Missing relationship to the vehicle/listing
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "listing_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_transport_estimate_listing")
    )
    private VehicleEntity listing;

    @Column(name = "pickup_location", nullable = false)
    private String pickupLocation;

    @Column(name = "distance_in_miles", nullable = false)
    private Integer distanceInMiles;

    @Column(nullable = false)
    private String operability;

    @Column(name = "trailer_type", nullable = false)
    private String trailerType;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;

    // 2. Changed from double to BigDecimal to prevent currency rounding errors
    @Column(name = "additional_fees", nullable = false, precision = 10, scale = 2)
    private BigDecimal additionalFees;

    @Column(name = "estimated_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedCost;

    // Required default constructor for JPA
    public VehicleTransportEstimateEntity() {
        super();
    }

    // Full constructor for your service layer logic
    public VehicleTransportEstimateEntity(
            VehicleEntity listing,
            String pickupLocation,
            Integer distanceInMiles,
            String operability,
            String trailerType,
            String vehicleType,
            BigDecimal additionalFees,
            BigDecimal estimatedCost
    ) {
        this.listing = listing;
        this.pickupLocation = pickupLocation;
        this.distanceInMiles = distanceInMiles;
        this.operability = operability;
        this.trailerType = trailerType;
        this.vehicleType = vehicleType;
        this.additionalFees = additionalFees;
        this.estimatedCost = estimatedCost;
    }

    public BigDecimal getEstimatedCost() {
        return this.estimatedCost;
    }
}