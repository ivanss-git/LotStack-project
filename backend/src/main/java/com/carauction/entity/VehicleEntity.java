package com.carauction.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "vehicles",
    schema = "vehicle_schema"
)

public class VehicleEntity extends BaseEntity { // Inherits id, createdAt, updatedAt

    @Column(nullable = false, unique = true, length = 17) 
    private String vin;

    @Column(nullable = false)
    private Short modelYear;

    @Column    
    private Boolean keysPresent;

    @Column
    private Integer odometer;

    @Column(nullable = false, length = 100)
    private String make;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false, length = 100)
    private String bodyType;

    @Column(length = 100)
    private String trim;

    @Column(length = 50)
    private String color;

    @Column(length = 100)
    private String engine;

    @Column(length = 50)
    private String transmission;

    @Column(length = 20)
    private String drivetrain;

    public VehicleEntity() {
        super();
    }

    // Getters and Setters for vehicle-specific fields
    public void setVin(String vin) { this.vin = vin; }
    public void setModelYear(Short modelYear) { this.modelYear = modelYear; }
    public void setKeysPresent(Boolean keysPresent) { this.keysPresent = keysPresent; }
    public void setOdometer(Integer odometer) { this.odometer = odometer; }
    public void setMake(String make) { this.make = make; }
    public void setModel(String model) { this.model = model; }
    public void setBodyType(String bodyType) { this.bodyType = bodyType; }
    public void setTrim(String trim) { this.trim = trim; }
    public void setColor(String color) { this.color = color; }
    public void setEngine(String engine) { this.engine = engine; }
    public void setTransmission(String transmission) { this.transmission = transmission; }
    public void setDrivetrain(String drivetrain) { this.drivetrain = drivetrain; }

    public String getVin() { return vin; }
    public Short getModelYear() { return modelYear; }
    public Boolean getKeysPresent() { return keysPresent; }
    public Integer getOdometer() { return odometer; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public String getBodyType() { return bodyType; }
    public String getTrim() { return trim; }
    public String getColor() { return color; }
    public String getEngine() { return engine; }
    public String getTransmission() { return transmission; }
    public String getDrivetrain() { return drivetrain; }
}