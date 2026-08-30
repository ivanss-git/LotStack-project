package com.carauction.service;

import com.carauction.dto.response.RankedListingResponse;
import com.carauction.dto.response.RankingResponse;
import com.carauction.entity.AuctionListingAnalysisEntity;
import com.carauction.repository.AuctionListingAnalysisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ListingRankingService {

    private final AuctionListingAnalysisRepository repository;

    public ListingRankingService(
        AuctionListingAnalysisRepository repository
    ) {
        this.repository = repository;
    }

    public RankingResponse rankings(
        RankingMetric metric,
        int requestedLimit
    ) {
        int limit = Math.max(
            1,
            Math.min(requestedLimit, 100)
        );

        List<AuctionListingAnalysisEntity> qualified =
            repository.findAll()
                .stream()
                .filter(this::isQualified)
                .toList();

        Comparator<AuctionListingAnalysisEntity> comparator =
            comparator(metric);

        List<RankedListingResponse> globalTop =
            qualified.stream()
                .sorted(comparator)
                .limit(limit)
                .map(RankedListingResponse::from)
                .toList();

        Map<String, List<RankedListingResponse>> siteTop =
            qualified.stream()
                .collect(Collectors.groupingBy(
                    this::provider
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue()
                        .stream()
                        .sorted(comparator)
                        .limit(limit)
                        .map(RankedListingResponse::from)
                        .toList(),
                    (first, second) -> first,
                    LinkedHashMap::new
                ));

        return new RankingResponse(
            OffsetDateTime.now(ZoneOffset.UTC),
            metric,
            siteTop.size(),
            qualified.size(),
            globalTop,
            siteTop
        );
    }

    private boolean isQualified(
        AuctionListingAnalysisEntity result
    ) {
        return result.getEstimatedPurchasePrice() != null
            && result
                .getEstimatedPurchasePrice()
                .signum() > 0
            && result.getExpectedProfit() != null
            && result.getRoiPercent() != null;
    }

    private Comparator<AuctionListingAnalysisEntity> comparator(
        RankingMetric metric
    ) {
        Comparator<AuctionListingAnalysisEntity> primary =
            switch (metric) {
                case ROI -> Comparator.comparing(
                    AuctionListingAnalysisEntity::getRoiPercent,
                    Comparator.nullsLast(
                        BigDecimal::compareTo
                    )
                );

                case PROFIT -> Comparator.comparing(
                    AuctionListingAnalysisEntity::getExpectedProfit,
                    Comparator.nullsLast(
                        BigDecimal::compareTo
                    )
                );

                case OPPORTUNITY -> Comparator.comparing(
                    AuctionListingAnalysisEntity::getOpportunityScore,
                    Comparator.nullsLast(
                        Integer::compareTo
                    )
                );
            };

        return primary
            .thenComparing(
                AuctionListingAnalysisEntity::getOpportunityScore,
                Comparator.nullsLast(
                    Integer::compareTo
                )
            )
            .thenComparing(
                AuctionListingAnalysisEntity::getExpectedProfit,
                Comparator.nullsLast(
                    BigDecimal::compareTo
                )
            )
            .reversed();
    }

    private String provider(
        AuctionListingAnalysisEntity result
    ) {
        String provider =
            result.getListing().getProviderType();

        return provider == null || provider.isBlank()
            ? "UNKNOWN"
            : provider.toUpperCase(Locale.ROOT);
    }
}