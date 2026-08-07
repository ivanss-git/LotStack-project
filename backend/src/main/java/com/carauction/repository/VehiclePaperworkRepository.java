package com.carauction.repository;

import com.carauction.entity.VehiclePaperworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VehiclePaperworkRepository extends JpaRepository<VehiclePaperworkEntity, Long> {
    Optional<VehiclePaperworkEntity> findyByVin(String vin);
    Optional<VehiclePaperworkEntity> findByListingId(Long listingId);

    boolean existsByVin(Long listingId);
    
}
