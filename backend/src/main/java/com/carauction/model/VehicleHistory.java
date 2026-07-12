package com.carauction.model;

public class VehicleHistory {
    private int previousOwners;
    private int previousAccidents;
    private String damageSeverity;
    private String classification;

    public VehicleHistory(
        int previousOwners, 
        int previousAccidents, 
        String damageSeverity, 
        String classification
    ) {
        this.previousOwners = previousOwners;
        this.previousAccidents = previousAccidents;
        this.damageSeverity = damageSeverity;
        this.classification = classification;
    }

    public int getPreviousOwners() { return previousOwners;}
    public int getPreviousAccidents() { return previousAccidents;}
    public String getDamageSeverity() { return damageSeverity;}
    public String getClassification() { return classification;}

}
