package com.carauction.service;
import com.carauction.dto.request.VehicleRequest;
import com.carauction.dto.response.VehicleResponse;
import com.carauction.entity.VehicleEntity;
import com.carauction.exception.*;
import com.carauction.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service 
@Transactional
public class VehicleService {   
    private final VehicleRepository repo;
 
    public VehicleService(VehicleRepository repo) {
        this.repo=repo;
    }

    public VehicleResponse create(VehicleRequest r) {
        String vin=r.vin().trim().toUpperCase();

        if(repo.existsByVin(vin)) 
            throw new DuplicateResourceException("VIN already exists: "+vin);

        return VehicleResponse.from(repo.save(entity(r,vin)));
    }

    @Transactional(readOnly=true) 
    public VehicleResponse get(Long id) {
        return VehicleResponse.from(require(id));
    }

    @Transactional(readOnly=true) 
    public VehicleResponse getByVin(String vin) {
        return VehicleResponse.from(repo.findByVin(vin.toUpperCase()).orElseThrow(()->
            new ResourceNotFoundException("Vehicle not found for VIN: "+vin))
        );
    }

    @Transactional(readOnly=true) 
    public List<VehicleResponse> all() {
        return repo.findAll().stream().map(VehicleResponse::from).toList();
    }

    public VehicleResponse update(Long id,VehicleRequest r) {
        VehicleEntity v=require(id);
        String vin=r.vin().trim().toUpperCase();
        repo.findByVin(vin)
            .filter(x->!x.getId().equals(id))
            .ifPresent(x->{throw new DuplicateResourceException("VIN already exists: "+vin);}
        );
        
        v.update(
            vin,
            r.modelYear(),
            r.keysPresent(),
            r.odometer(),
            r.make(),
            r.model(),
            r.bodyType(),
            r.trim(),
            r.color(),
            r.engine(),
            r.transmission(),
            r.drivetrain()
        );
        return VehicleResponse.from(v);
    }
    public void delete(Long id) {
        repo.delete(require(id));
    
    }
    public VehicleEntity require(Long id) {
        return repo.findById(id)
            .orElseThrow(()->
                new ResourceNotFoundException("Vehicle not found: "+id)
        );
    }

    private VehicleEntity entity(VehicleRequest r,String vin) {
        return new VehicleEntity(
            vin,
            r.modelYear(),
            r.keysPresent(),
            r.odometer(),
            r.make(),
            r.model(),
            r.bodyType(),
            r.trim(),
            r.color(),
            r.engine(),
            r.transmission(),
            r.drivetrain()
        );
    }
}
