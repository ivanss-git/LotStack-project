package com.carauction.dto.request;

import jakarta.validation.constraints.*;

public record HistoryRequest(
    @PositiveOrZero Integer previousOwners,
    @PositiveOrZero Integer previousAccidents,
    String damageCode,
    @NotBlank String classification,
    String airbagStatus
) {}
