package com.carauction.model;
// Just the data model, no business logic

public class Vehicle {
    private String vin;
    private int year;
    private String make;
    private String model;
    private String color;
    private double mileage;
    private String bodyCondition;
    private String mechanicalStatus;
    private String titleStatus;

    public Vehicle(
        String vin, 
        int year,
        String make, 
        String model, 
        String color, 
        double mileage, 
        String bodyCondition, 
        String mechanicalStatus, 
        String titleStatus
    ) {
        this.vin = vin;
        this.make = make; 
        this.model = model;
        this.year = year;
        this.color = color;
        this.mileage = mileage;
        this.bodyCondition = bodyCondition;
        this.mechanicalStatus = mechanicalStatus;
        this.titleStatus = titleStatus;
    } 

    public String getVin() { return vin;}
    public int getYear() { return year;}
    public String getMake() { return make;}
    public String getModel() { return model;}
    public String getColor() { return color;}
    public double getMileage() { return mileage;}
    public String getBodyCondition() { return bodyCondition;}
    public String getMechanicalStatus() { return mechanicalStatus;}
    public String getTitleStatus() { return titleStatus;}
}
