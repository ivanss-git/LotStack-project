package com.carauction.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record VehicleCreateRequest(
    @Size(max = 17) String vin,
    @Min(1886) @Max(2100) short modelYear,
    Boolean keysPresent,
    @PositiveOrZero Integer odometer,
    @NotBlank String make,
    @NotBlank String model,
    @NotBlank String bodyType,
    String trim,
    String color,
    String engine,
    String transmission,
    String drivetrain
) {}

   
