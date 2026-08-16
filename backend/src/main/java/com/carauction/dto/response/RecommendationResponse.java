package com.carauction.dto.response;

import java.time.OffsetDateTime;
import java.util.List;

public record RecommendationResponse(
        String sourceRecordId,
        String stockNumber,
        Short year,
        String make,
        String model,
        String series,
        String bodyStyle,
        Integer mileage,
        String primaryDamage,
        String secondaryDamage,
        String titleType,
        String titleCode,
        Boolean runAndDrive,
        Boolean hasKeys,
        String airbagState,
        OffsetDateTime auctionDateTime,
        String branchName,
        String location,
        int opportunityScore,
        String riskLevel,
        List<String> reasons
) {
}
