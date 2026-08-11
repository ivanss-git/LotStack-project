package com.carauction.service;

import com.carauction.dto.request.PaperworkRequest;
import com.carauction.dto.response.PaperworkResponse;
import com.carauction.entity.*;
import com.carauction.exception.ResourceNotFoundException;
import com.carauction.repository.VehiclePaperworkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PaperworkService {

    private final VehiclePaperworkRepository repo;
    private final VehicleService vehicles;

    public PaperworkService(VehiclePaperworkRepository r, VehicleService v) {
        repo = r;
        vehicles = v; 
    }

    public PaperworkResponse save(Long id, PaperworkRequest r) {
        VehiclePaperworkEntity p = repo.findByVehicle_Id(id).orElseGet(() ->
            new VehiclePaperworkEntity(
                vehicles.require(id),
                r.titleStatus(),
                r.titleState(),
                r.lienStatus(),
                r.billOfSalePresent(),
                r.auctionFees()
            )
        );

        p.update(r.titleStatus(),
                r.titleState(),
                r.lienStatus(),
                r.billOfSalePresent(),
                r.auctionFees()
        );

        return PaperworkResponse.from(repo.save(p));
    }

    @Transactional(readOnly=true)
    public PaperworkResponse get(Long id){
        return PaperworkResponse.from(repo.findByVehicle_Id(id).orElseThrow(()->
            new ResourceNotFoundException("Paperwork not found for vehicle: "+id))
        );
    }

    public void delete(Long id) {
        repo.delete(repo.findByVehicle_Id(id).orElseThrow(()->
            new ResourceNotFoundException("Paperwork not found for vehicle: "+id))
        );
    }
}
