package com.carauction.repository;

import com.carauction.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

// Should only contain data access signatures for db queries
public interface VehicleRepository 
extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findByVin(String vin);

    boolean existsByVin(String vin);

    List<VehicleEntity> findByMakeAndModel(String make, String model);
}
