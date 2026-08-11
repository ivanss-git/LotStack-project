package com.carauction.service;

import com.carauction.dto.request.HistoryRequest;
import com.carauction.dto.response.HistoryResponse;
import com.carauction.entity.*;
import com.carauction.exception.ResourceNotFoundException;
import com.carauction.repository.VehicleHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HistoryService {

    private final VehicleHistoryRepository repo;
    private final VehicleService vehicles;

    public HistoryService(VehicleHistoryRepository r, VehicleService v) {
        repo = r;
        vehicles = v;
    }

    public HistoryResponse save(Long vehicleId, HistoryRequest r) {
        VehicleHistoryEntity h = repo.findByVehicle_Id(vehicleId).orElseGet(() ->
            new VehicleHistoryEntity(vehicles.require(vehicleId),
                r.previousOwners(),
                r.previousAccidents(),
                r.damageCode(),
                r.classification(),
                r.airbagStatus()
            )
        );

        h.update(
            r.previousOwners(),
            r.previousAccidents(),
            r.damageCode(),
            r.classification(),
            r.airbagStatus()
        );

        return HistoryResponse.from(repo.save(h)
        );
    }
    
    @Transactional(readOnly=true)
    public HistoryResponse get(Long id) { 
        return HistoryResponse.from(repo.findByVehicle_Id(id).orElseThrow(() ->
            new ResourceNotFoundException("History not found for vehicle: " + id))
        );
    }

    public void delete(Long id) {
        repo.delete(repo.findByVehicle_Id(id).orElseThrow(() -> 
            new ResourceNotFoundException("History not found for vehicle: " + id))
        );
    }
}

