package com.carauction.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record PaperworkRequest(
    @NotBlank String titleStatus,
    @Size(min=2,max=2) String titleState,
    String lienStatus,
    Boolean billOfSalePresent,
    @PositiveOrZero BigDecimal auctionFees
) {}
