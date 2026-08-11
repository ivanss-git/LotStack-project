package com.carauction.repository;

import com.carauction.entity.VehicleHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface VehicleHistoryRepository extends JpaRepository<VehicleHistoryEntity, Long> {
    
    // Traverses Java field: history.listing -> vehicles.id
    Optional<VehicleHistoryEntity> findByVehicle_Id(Long id);
    // Traverses Java field: history.listing -> vehicles.vin
    Optional<VehicleHistoryEntity> findByVehicle_Vin(String vin);
    boolean existsByVehicle_Id(Long id);
}
