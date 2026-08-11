package com.carauction.repository;

import com.carauction.entity.AuctionAnalyzerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AnalysisResultRepository extends JpaRepository<AuctionAnalyzerEntity, Long> {
    
    // Derived query to find financial analysis by the associated vehicle ID
    Optional<AuctionAnalyzerEntity> findByVehicle_Id(Long id);

    Optional<AuctionAnalyzerEntity> findByVehicle_Vin(String vin);

    boolean existsByVehicle_Id(Long id);
}
