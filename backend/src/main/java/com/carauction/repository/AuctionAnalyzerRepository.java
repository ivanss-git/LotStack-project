package com.carauction.repository;

import com.carauction.entity.AuctionAnalyzerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuctionAnalyzerRepository extends JpaRepository<AuctionAnalyzerEntity, Long> {
    
    // Derived query to find financial analysis by the associated vehicle ID
    Optional<AuctionAnalyzerEntity> findByListingVin(String vin);

    Optional<AuctionAnalyzerEntity> findByListingId(Long listingId);

    boolean eixstsByListingId(Long listingId);
}
