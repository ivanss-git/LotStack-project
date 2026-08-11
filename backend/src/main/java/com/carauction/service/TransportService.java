package com.carauction.service;

import com.carauction.dto.request.TransportRequest;
import com.carauction.dto.response.TransportResponse;
import com.carauction.entity.*;
import com.carauction.exception.*;
import com.carauction.repository.VehicleTransportEstimateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class  TransportService {
    private final VehicleTransportEstimateRepository repo;
    private final VehicleService vehicles;
    
    public TransportService(VehicleTransportEstimateRepository r,VehicleService v){
        repo=r;
        vehicles=v;
    }
    public TransportResponse save(Long id,TransportRequest r){
        VehicleTransportEstimateEntity t=repo.findByVehicle_Id(id).orElseGet(()->
                new VehicleTransportEstimateEntity(
                        vehicles.require(id),
                        r.pickupLocation(),
                        r.distanceInMiles(),
                        r.operability(),
                        r.trailerType(),
                        r.vehicleType(),
                        r.additionalFees(),
                        r.estimatedCost()
                )
        );

        t.update(
                r.pickupLocation(),
                r.distanceInMiles(),
                r.operability(),
                r.trailerType(),
                r.vehicleType(),
                r.additionalFees(),
                r.estimatedCost()
        );

        return TransportResponse.from(repo.save(t));}

    @Transactional(readOnly=true)
    public TransportResponse get(Long id){
        return TransportResponse.from(repo.findByVehicle_Id(id).orElseThrow(()->
            new ResourceNotFoundException("Transport estimate not found for vehicle: "+id)));
    }
    public void delete(Long id){
        repo.delete(repo.findByVehicle_Id(id).orElseThrow(()->
            new ResourceNotFoundException("Transport estimate not found for vehicle: "+id)));
    }
}
