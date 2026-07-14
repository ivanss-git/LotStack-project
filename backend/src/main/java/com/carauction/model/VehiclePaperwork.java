package com.carauction.model;

public class VehiclePaperwork {
    private boolean vinValid;
    private String titleStatus;
    private boolean lienPresent; 

    public VehiclePaperwork(
        boolean vinValid, 
        String titleStatus, 
        boolean lienPresent
    ) {
        this.vinValid = vinValid;
        this.titleStatus = titleStatus;
        this.lienPresent = lienPresent;
    }

    public boolean isVinValid() { return vinValid;}
    public String getTitleStatus() { return titleStatus;}
    public boolean isLienPresent() { return lienPresent;}
}
