package com.carauction.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "auction_listings", schema = "auction_listing_schema")
public class AuctionListingEntity extends BaseEntity {

    @Column(name = "source_record_id", nullable = false, unique = true, length = 100)
    private String sourceRecordId;
    @Column(name = "source_first_seen_at") private OffsetDateTime sourceFirstSeenAt;
    @Column(name = "source_last_seen_at") private OffsetDateTime sourceLastSeenAt;
    @Column(name = "stock_number", length = 50) private String stockNumber;
    @Column(name = "item_id", length = 50) private String itemId;
    @Column(name = "salvage_id", length = 50) private String salvageId;
    @Column(name = "external_auction_id", length = 50) private String externalAuctionId;
    @Column(name = "model_year", nullable = false) private Short modelYear;
    @Column(nullable = false, length = 100) private String make;
    @Column(nullable = false, length = 100) private String model;
    @Column(length = 100) private String series;
    @Column(name = "body_style", length = 100) private String bodyStyle;
    @Column(name = "exterior_color", length = 50) private String exteriorColor;
    @Column(length = 255) private String engine;
    private Short cylinders;
    @Column(length = 50) private String transmission;
    @Column(length = 30) private String drivetrain;
    @Column(name = "fuel_type", length = 50) private String fuelType;
    @Column(name = "primary_damage", length = 100) private String primaryDamage;
    @Column(name = "secondary_damage", length = 100) private String secondaryDamage;
    @Column(name = "loss_type", length = 50) private String lossType;
    @Column(name = "title_type", length = 50) private String titleType;
    @Column(name = "title_code", length = 20) private String titleCode;
    @Column(name = "title_state", length = 10) private String titleState;
    @Column(name = "has_keys") private Boolean hasKeys;
    @Column(name = "run_and_drive") private Boolean runAndDrive;
    @Column(name = "starts_description", length = 50) private String startsDescription;
    private Integer mileage;
    @Column(name = "odometer_brand", length = 30) private String odometerBrand;
    @Column(name = "odometer_unit", length = 10) private String odometerUnit;
    @Column(name = "airbag_state", length = 30) private String airbagState;
    @Column(name = "vehicle_grade") private Short vehicleGrade;
    @Column(name = "auction_datetime") private OffsetDateTime auctionDateTime;
    @Column(name = "branch_number", length = 20) private String branchNumber;
    @Column(name = "branch_name", length = 100) private String branchName;
    @Column(name = "location_city", length = 100) private String locationCity;
    @Column(name = "location_state", length = 10) private String locationState;
    @Column(name = "location_latitude") private Double locationLatitude;
    @Column(name = "location_longitude") private Double locationLongitude;
    @Column(name = "provider_type", length = 30) private String providerType;
    @Column(name = "country_of_origin", length = 100) private String countryOfOrigin;
    @Column(length = 17)
        private String vin;
    @Column(name = "current_bid", precision = 12, scale = 2)
    private BigDecimal currentBid;

    protected AuctionListingEntity() {}

    public AuctionListingEntity(String sourceRecordId) {
        this.sourceRecordId = sourceRecordId;
    }

    public void update(
            OffsetDateTime sourceFirstSeenAt, OffsetDateTime sourceLastSeenAt,
            String stockNumber, String itemId, String salvageId, String externalAuctionId,
            Short modelYear, String make, String model, String series, String bodyStyle,
            String exteriorColor, String engine, Short cylinders, String transmission,
            String drivetrain, String fuelType, String primaryDamage, String secondaryDamage,
            String lossType, String titleType, String titleCode, String titleState,
            Boolean hasKeys, Boolean runAndDrive, String startsDescription, Integer mileage,
            String odometerBrand, String odometerUnit, String airbagState, Short vehicleGrade,
            OffsetDateTime auctionDateTime, String branchNumber, String branchName,
            String locationCity, String locationState, Double locationLatitude,
            Double locationLongitude, String providerType, String countryOfOrigin) {
        this.sourceFirstSeenAt = sourceFirstSeenAt;
        this.sourceLastSeenAt = sourceLastSeenAt;
        this.stockNumber = stockNumber;
        this.itemId = itemId;
        this.salvageId = salvageId;
        this.externalAuctionId = externalAuctionId;
        this.modelYear = modelYear;
        this.make = make;
        this.model = model;
        this.series = series;
        this.bodyStyle = bodyStyle;
        this.exteriorColor = exteriorColor;
        this.engine = engine;
        this.cylinders = cylinders;
        this.transmission = transmission;
        this.drivetrain = drivetrain;
        this.fuelType = fuelType;
        this.primaryDamage = primaryDamage;
        this.secondaryDamage = secondaryDamage;
        this.lossType = lossType;
        this.titleType = titleType;
        this.titleCode = titleCode;
        this.titleState = titleState;
        this.hasKeys = hasKeys;
        this.runAndDrive = runAndDrive;
        this.startsDescription = startsDescription;
        this.mileage = mileage;
        this.odometerBrand = odometerBrand;
        this.odometerUnit = odometerUnit;
        this.airbagState = airbagState;
        this.vehicleGrade = vehicleGrade;
        this.auctionDateTime = auctionDateTime;
        this.branchNumber = branchNumber;
        this.branchName = branchName;
        this.locationCity = locationCity;
        this.locationState = locationState;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.providerType = providerType;
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getItemId() { return itemId;}
    public String getExternalAuctionId() { return externalAuctionId;}
    public String getVin() { return vin;}
    public BigDecimal getCurrentBid() { return currentBid;}
    public String getProviderType() { return providerType;}
    public String getSourceRecordId() { return sourceRecordId; }
    public String getStockNumber() { return stockNumber; }
    public Short getModelYear() { return modelYear; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public String getSeries() { return series; }
    public String getBodyStyle() { return bodyStyle; }
    public String getPrimaryDamage() { return primaryDamage; }
    public String getSecondaryDamage() { return secondaryDamage; }
    public String getLossType() { return lossType; }
    public String getTitleType() { return titleType; }
    public String getTitleCode() { return titleCode; }
    public Boolean getHasKeys() { return hasKeys; }
    public Boolean getRunAndDrive() { return runAndDrive; }
    public String getStartsDescription() { return startsDescription; }
    public Integer getMileage() { return mileage; }
    public String getOdometerBrand() { return odometerBrand; }
    public String getAirbagState() { return airbagState; }
    public Short getVehicleGrade() { return vehicleGrade; }
    public OffsetDateTime getAuctionDateTime() { return auctionDateTime; }
    public String getBranchName() { return branchName; }
    public String getLocationCity() { return locationCity; }
    public String getLocationState() { return locationState; }
}
