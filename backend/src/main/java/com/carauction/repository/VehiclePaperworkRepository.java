package com.carauction.repository;

import com.carauction.entity.VehiclePaperworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface VehiclePaperworkRepository extends JpaRepository<VehiclePaperworkEntity, Long> {
    Optional<VehiclePaperworkEntity> findByVehicle_Id(Long id);

    Optional<VehiclePaperworkEntity> findyByVehicle_Vin(String vin);

    boolean existsByVehicle_vin(Long id);
    
}
