package com.carauction.repository;

import com.carauction.entity.AuctionListingAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuctionListingAnalysisRepository
    extends JpaRepository<AuctionListingAnalysisEntity, Long> {

    Optional<AuctionListingAnalysisEntity>
        findByListing_Id(Long listingId);
}