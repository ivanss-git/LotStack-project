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
    public static VehicleResponse fromEntity(VehicleEntity vehicle) {
        return new VehicleResponse(
            vehicle.getId(),
            vehicle.getVin(),
            vehicle.getModelYear(),
            vehicle.getKeysPresent(),
            vehicle.getOdometer(),
            vehicle.getMake(),
            vehicle.getModel(),
            vehicle.getBodyType(),
            vehicle.getTrim(),
            vehicle.getColor(),
            vehicle.getEngine(),
            vehicle.getTransmission(),
            vehicle.getDrivetrain(),
            vehicle.getCreatedAt(),
            vehicle.getUpdatedAt()
        );
    }
}
