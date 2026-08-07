package com.carauction.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "vehicle_history",
    schema = "vehicle_history_schema"
)

public class VehicleHistoryEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "vehicle_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_vehicle_history_vehicle")
    )
    private VehicleEntity listing;

    @Column(name = "previousOwners")
    private int previousOwners;

    @Column(name = "previousAccidents")
    private int previousAccidents;

    @Column(name = "damageCode", length = 50)
    private String damageCode;

    @Column(name = "classification", nullable = false, length = 50)
    private String classification;

    @Column(name = "airbag_status", length = 30)
    private String airbagStatus;                                                                                                                                                                                                                                                                                                                                                                                                    

    public VehicleHistoryEntity() {
        super();
    }

    public VehicleHistoryEntity(
        VehicleEntity listing,
        Integer previousOwners, 
        Integer previousAccidents, 
        String damageCode,
        String classification,
        String airbagStatus
    ) {
        this.listing = listing;
        this.previousOwners = previousOwners;
        this.previousAccidents = previousAccidents;
        this.damageCode = damageCode;
        this.classification = classification;
        this.airbagStatus = airbagStatus;
    }
    public void setListing(VehicleEntity listing) { this.listing = listing;}
    public void setPreviousOwners(Integer previousOwners) { this.previousOwners = previousOwners;}
    public void setPreviousAccidents(Integer previousAccidents) { this.previousAccidents = previousAccidents;}
    public void setDamageCode(String damageCode) { this.damageCode = damageCode;}
    public void setClassification(String classification) { this.classification = classification;}
    public void setAirbagStatus(String airbagStatus) { this.airbagStatus = airbagStatus;}

    public VehicleEntity getListing() { return listing;}
    public Integer getPreviousOwners() { return previousOwners;}
    public Integer getPreviousAccidents() { return previousAccidents;}
    public String getDamageCode() { return damageCode;}
    public String getClassification() { return classification;}
    public String getAirbagStatus() { return airbagStatus;}
}
