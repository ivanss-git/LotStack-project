package com.carauction.entity;

import jakarta.persistence.*;

@Entity
@Table(name="vehicle_history",schema="history_schema")
public class VehicleHistoryEntity extends BaseEntity {

    @OneToOne(fetch=FetchType.LAZY,optional=false)
    @JoinColumn(
        name="vehicle_id",
        nullable=false,
        unique=true,
        foreignKey=@ForeignKey(name="fk_history_vehicle")
    )
    private VehicleEntity vehicle;

    @Column(name="previous_owners")
    private Integer previousOwners;

    @Column(name="previous_accidents")
    private Integer previousAccidents;

    @Column(name="damage_code",length=50)
    private String damageCode;

    @Column(nullable=false,length=50)
    private String classification;

    @Column(name="airbag_status",length=30)
    private String airbagStatus;                                                                                                                                                                                                                                                                                                                                                                                                    

    protected VehicleHistoryEntity() {}

    public VehicleHistoryEntity(
        VehicleEntity vehicle,
        Integer owners, 
        Integer accidents, 
        String damage,
        String classification,
        String airbag
    ) {
        this.vehicle = vehicle;
        update(owners,accidents,damage,classification,airbag);
    }

    public void update(Integer owners, Integer accidents, String damage, String classification, String airbag) {
        previousOwners = owners;
        previousAccidents = accidents;
        damageCode = damage;
        this.classification = classification;
        airbagStatus = airbag;
    }

    public VehicleEntity getVehicle() { return vehicle;}
    public Integer getPreviousOwners() { return previousOwners;}
    public Integer getPreviousAccidents() { return previousAccidents;}
    public String getDamageCode() { return damageCode;}
    public String getClassification() { return classification;}
    public String getAirbagStatus() { return airbagStatus;}   
}
