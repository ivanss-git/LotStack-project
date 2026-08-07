package com.carauction.repository;

import com.carauction.entity.VehicleHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VehicleHistoryRepository extends JpaRepository<VehicleHistoryEntity, Long> {
    
    // Traverses Java field: history.listing -> vehicles.vin
    Optional<VehicleHistoryEntity> findByListingVin(String vin);
    
    // Traverses Java field: history.listing -> vehicles.id
    Optional<VehicleHistoryEntity> findByListingId(Long listingId);
    
    boolean existsByListingId(Long listingId);
}
