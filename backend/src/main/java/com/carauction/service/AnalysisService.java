package com.carauction.service;

import com.carauction.dto.request.AnalysisRequest;
import com.carauction.dto.response.AnalysisResponse;
import com.carauction.entity.*;
import com.carauction.exception.ResourceNotFoundException;
import com.carauction.repository.AnalysisResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class AnalysisService {  

    private final AnalysisResultRepository repo;
    private final VehicleService vehicles;

    public AnalysisService(AnalysisResultRepository r, VehicleService v) {
        repo = r;
        vehicles = v;
    }
    
    public AnalysisResponse analyze(Long id, AnalysisRequest r) {
        BigDecimal other = r.estimatedRepairCost()
                            .add(r.transportCost())
                            .add(r.auctionFees())
                            .add(r.titleAdjustmentCost());

        BigDecimal total = r.purchasePrice().add(other);
        BigDecimal profit = r.estimatedMarketValue().subtract(total);

        BigDecimal max = r.estimatedMarketValue()
                            .subtract(other).
                            subtract(r.profitGoal())
                            .max(BigDecimal.ZERO);

        boolean good = profit.compareTo(r.profitGoal()) >= 0;

        AuctionAnalyzerEntity a = repo.findByVehicle_Id(id).orElseGet(() -> 
            new AuctionAnalyzerEntity(
                vehicles.require(id),
                r.purchasePrice(),
                r.estimatedMarketValue(),
                r.estimatedRepairCost(),
                r.transportCost(),
                r.auctionFees(),
                r.titleAdjustmentCost(),
                r.profitGoal(),
                total, 
                max, 
                profit, 
                good
            )
        );

        a.update(
            r.purchasePrice(),
            r.estimatedMarketValue(), 
            r.estimatedRepairCost(), 
            r.transportCost(), 
            r.auctionFees(), 
            r.titleAdjustmentCost(), 
            r.profitGoal(),
            total,
            max,
            profit,
            good
        );

        return AnalysisResponse.from(repo.save(a));
    }

    @Transactional(readOnly=true)
    public AnalysisResponse get(Long id) {
        return AnalysisResponse.from(repo.findByVehicle_Id(id)
            .orElseThrow(() -> new ResourceNotFoundException("Analysis not found for vehicle: " + id)));
    } 

    public void delete(Long id) {
        repo.delete(repo.findByVehicle_Id(id)
            .orElseThrow(() -> new ResourceNotFoundException("Analysis not found for vehicle: " + id)));
    }
}

