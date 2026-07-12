package com.carauction.model;

public class TransportEstimate {
   private double distanceInMiles;
   private String operability;
   private String trailerType;
   private String vehicleType;
   private double additionalFees;

   public TransportEstimate(
        double distanceInMiles,
        String operability,
        String trailerType, 
        String vehicleType, 
        double additionalFees
    ) {
        this.distanceInMiles = distanceInMiles;
        this.operability = operability;
        this.trailerType = trailerType;
        this.vehicleType = vehicleType;
        this.additionalFees = additionalFees;

   }

    public double getDistanceInMiles() { return distanceInMiles;}
    public String getOperability() { return operability;}
    public String getTrailerType() { return trailerType;}
    public String getVehicleType() { return vehicleType;}
    public double getAdditionalFees() { return additionalFees;}

}
