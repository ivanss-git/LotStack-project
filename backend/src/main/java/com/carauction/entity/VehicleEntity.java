package com.carauction.entity;

import jakarta.persistence.*;
@Entity
@Table(name="vehicles",schema="vehicle_schema")
public class VehicleEntity extends BaseEntity { // Inherits id, createdAt, updatedAt

    @Column(nullable=false,unique=true,length=17) 
    private String vin;

    @Column(name="model_year",nullable=false)
    private Short modelYear;

    @Column(name="keys_present")   
    private Boolean keysPresent;

    private Integer odometer;

    @Column(nullable=false,length=100)
    private String make;

    @Column(nullable=false,length=100)
    private String model;

    @Column(name="body_type",nullable=false,length=100)
    private String bodyType;

    @Column(length=100)
    private String trim;

    @Column(length=50)
    private String color;

    @Column(length=100)
    private String engine;

    @Column(length=50)
    private String transmission;

    @Column(length=20)
    private String drivetrain;

    protected VehicleEntity() {}

    public VehicleEntity(
        String vin,
        Short modelYear,
        Boolean keysPresent,
        Integer odometer,
        String make,
        String model,
        String bodyType,
        String trim,
        String color,
        String engine,
        String transmission,
        String drivetrain
    ) {
        update(vin, modelYear, keysPresent, odometer, make, model, bodyType, trim, color, engine, transmission, drivetrain);
    }

    public void update(
        String vin, 
        Short modelYear, 
        Boolean keysPresent,
        Integer odometer,
        String make, 
        String model,
        String bodyType, 
        String trim,
        String color,
        String engine,
        String transmission,
        String drivetrain
    ) {
        this.vin = vin;
        this.modelYear = modelYear;
        this.keysPresent = keysPresent;
        this.odometer = odometer;
        this.make = make;
        this.model = model;
        this.bodyType = bodyType;
        this.trim = trim;
        this.color = color;
        this.engine = engine;
        this.transmission = transmission;
        this.drivetrain = drivetrain;
    }

    public String getVin() { return vin;}
    public Short getModelYear() { return modelYear;}
    public Boolean getKeysPresent() { return keysPresent;}
    public Integer getOdometer() { return odometer;}
    public String getMake() { return make;}
    public String getModel() { return model;}
    public String getBodyType() { return bodyType;}
    public String getTrim() { return trim;}
    public String getColor() { return color;}
    public String getEngine() { return engine;}
    public String getTransmission() { return transmission;}
    public String getDrivetrain() { return drivetrain;}
}