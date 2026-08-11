package com.carauction.dto.request;

import jakarta.validation.constraints.*;

public record VehicleRequest (
    @NotBlank @Size(min=17,max=17) String vin,
    @NotNull @Min(1886) @Max(2100) Short modelYear,
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
