package com.carauction.dto.response;

import com.carauction.entity.VehicleHistoryEntity;

public record HistoryResponse(
    Long id,
    Long vehicleId,
    Integer previousOwners,
    Integer previousAccidents,
    String damageCode,
    String classification,
    String airbagStatus
) {
    public static HistoryResponse from(VehicleHistoryEntity h) {
        return new HistoryResponse(
            h.getId(),
            h.getVehicle().getId(),
            h.getPreviousOwners(),
            h.getPreviousAccidents(),
            h.getDamageCode(),
            h.getClassification(),
            h.getAirbagStatus()
        );
    }
}
