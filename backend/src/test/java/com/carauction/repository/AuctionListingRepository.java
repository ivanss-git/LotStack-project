package com.carauction.repository;

import com.carauction.entity.AuctionListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuctionListingRepository extends JpaRepository<AuctionListingEntity, Long> {
    Optional<AuctionListingEntity> findBySourceRecordId(String sourceRecordId);
}
