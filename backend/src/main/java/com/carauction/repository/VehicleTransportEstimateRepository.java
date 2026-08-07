package com.carauction.repository;

import com.carauction.entity.VehicleTransportEstimateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface  VehicleTransportEstimateRepository 
extends JpaRepository< VehicleTransportEstimateEntity, Long> {

    Optional<VehicleTransportEstimateEntity> findByListingVin(String vin);

    Optional<VehicleTransportEstimateEntity> findByListingId(Long listing);

    boolean existsByVin(String vin);
  }
