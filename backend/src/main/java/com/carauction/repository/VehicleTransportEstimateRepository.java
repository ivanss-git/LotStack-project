package com.carauction.repository;

import com.carauction.entity.VehicleTransportEstimateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface  VehicleTransportEstimateRepository 
extends JpaRepository< VehicleTransportEstimateEntity, Long> {
    Optional<VehicleTransportEstimateEntity> findByVehicle_Id(Long id);

    Optional<VehicleTransportEstimateEntity> findByVehicle_Vin(String vin);

    boolean existsByVehicle_Id(Long id);
  }
