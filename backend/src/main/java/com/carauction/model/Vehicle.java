package com.carauction.model;
// Just the data model, no business logic

public class Vehicle {
    public int year;
    public int mileage;
    public double auctionFees;
    public double towFee;
    public double baseValue;
    public double profitGoal;
    public String vinStatus;
    public String make;
    public String damageType;

    public Vehicle(int year,int mileage,double auctionFees,double towFee,double baseValue,double profitGoal, String vinStatus, String make, String damageType) {
        this.year = year;
        this.mileage = mileage;
        this.auctionFees = auctionFees;
        this.towFee = towFee;
        this.baseValue = baseValue;
        this.profitGoal = profitGoal;
        this.vinStatus = vinStatus;
        this.make = make;
        this.damageType = damageType;
    } 
    @Override
    public String toString() {
        return "\nMake: " +make+ ", Year: " +year+ ", Mileage: " +mileage+ ", Title: " +vinStatus+ ", Damage Type: " +damageType;

    }

}
