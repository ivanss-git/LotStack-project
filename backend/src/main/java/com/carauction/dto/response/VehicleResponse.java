package com.carauction.dto.response;

import com.carauction.entity.VehicleEntity;
import java.time.OffsetDateTime;

public record VehicleResponse(
    Long id,
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
    String drivetrain,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt 
) { 
    public static VehicleResponse from(VehicleEntity v) {
        return new VehicleResponse(
            v.getId(),
            v.getVin(),
            v.getModelYear(),
            v.getKeysPresent(),
            v.getOdometer(),
            v.getMake(),
            v.getModel(),
            v.getBodyType(),
            v.getTrim(),
            v.getColor(),
            v.getEngine(),
            v.getTransmission(),
            v.getDrivetrain(),
            v.getCreatedAt(),
            v.getUpdatedAt()
        );
    }
}
