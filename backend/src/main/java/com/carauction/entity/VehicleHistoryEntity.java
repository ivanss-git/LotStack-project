package com.carauction.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "vehicle_history",
    schema = "vehicle_history_schema",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_vehicle_history_vehicle",
            columnNames = "vehicle_id"
        )
    }
)

public class VehicleHistoryEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "vehicle_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_vehicle_history_vehicle")
    )
    private VehicleEntity vehicle;

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

    protected VehicleHistoryEntity() {
        super();
    }

    public VehicleHistoryEntity(
        VehicleEntity vehicle,
        Integer previousOwners, 
        Integer previousAccidents, 
        String damageCode,
        String classification,
        String airbagStatus
    ) {
        this.vehicle = vehicle;
        this.previousOwners = previousOwners;
        this.previousAccidents = previousAccidents;
        this.damageCode = damageCode;
        this.classification = classification;
        this.airbagStatus = airbagStatus;
    }

    public void setPreviousOwners(Integer previousOwners) { this.previousOwners = previousOwners;}
    public void setPreviousAccidents(Integer previousAccidents) { this.previousAccidents = previousAccidents;}
    public void setDamageCode(String damageCode) { this.damageCode = damageCode;}
    public void setClassification(String classification) { this.classification = classification;}
    public void setAirbagStatus(String airbagStatus) { this.airbagStatus = airbagStatus;}

    public VehicleEntity getVehicle() { return vehicle;}
    public Integer getPreviousOwners() { return previousOwners;}
    public Integer getPreviousAccidents() { return previousAccidents;}
    public String getDamageCode() { return damageCode;}
    public String getClassification() { return classification;}
    public String getAirbagStatus() { return airbagStatus;}
}
