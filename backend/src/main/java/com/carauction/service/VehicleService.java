package com.carauction.service;

import com.carauction.dto.request.VehicleCreateRequest;
import com.carauction.dto.response.VehicleResponse;
import com.carauction.entity.VehicleEntity;
import com.carauction.exception.VehicleNotFoundException;
import com.carauction.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
    
    @Transactional
    public VehicleResponse createdVehicle(VehicleCreateRequest request) {
        VehicleEntity vehicle = new VehicleEntity();

        vehicle.setVin(normalizeVin(request.vin()));
        vehicle.setModelYear(request.modelYear());
        vehicle.setKeysPresent(request.keysPresent());
        vehicle.setOdometer(request.odometer());
        vehicle.setMake(request.make().trim());
        vehicle.setModel(request.model().trim());
        vehicle.setBodyType(request.bodyType().trim().toUpperCase());
        vehicle.setTrim(request.trim());
        vehicle.setColor(request.color());
        vehicle.setEngine(request.engine());
        vehicle.setTransmission(normalize(request.transmission()));
        vehicle.setDrivetrain( normalize(request.drivetrain()));
    
        VehicleEntity savedVehicle = vehicleRepository.save(vehicle);
        return VehicleResponse.fromEntity(savedVehicle);
    }

    @Transactional(readOnly = true) 
    public VehicleResponse getVehicle(Long id) {
        VehicleEntity vehicle = findEntityById(id);
        return VehicleResponse.fromEntity(vehicle);
    }

    @Transactional(readOnly = true) 
    public VehicleResponse getVehicleByVin(String vin) {
        VehicleEntity vehicle = vehicleRepository.findByVin(normalizeVin(vin)).
        orElseThrow(
            () -> new IllegalArgumentException(
                "Vehicle not found with VIN: " + vin
            )
        );
        return VehicleResponse.fromEntity(vehicle);
    }

    @Transactional(readOnly = true) 
    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll()
            .stream()
            .map(VehicleResponse::fromEntity)
            .toList();
    }

    @Transactional
    public VehicleResponse updatedVehicle(
        Long id,
        VehicleCreateRequest request
    ) {
        VehicleEntity vehicle = findEntityById(id);

        vehicle.setVin(normalizeVin(request.vin()));
        vehicle.setModelYear(request.modelYear());
        vehicle.setKeysPresent(request.keysPresent());
        vehicle.setOdometer(request.odometer());
        vehicle.setMake(request.make().trim());
        vehicle.setModel(request.model().trim());
        vehicle.setBodyType(request.bodyType().trim().toUpperCase());
        vehicle.setTrim(request.trim());
        vehicle.setColor(request.color());
        vehicle.setEngine(request.engine());
        vehicle.setTransmission(normalize(request.transmission()));
        vehicle.setDrivetrain(normalize(request.drivetrain()));

        VehicleEntity updatedVehicle = vehicleRepository.save(vehicle);
        return VehicleResponse.fromEntity(updatedVehicle);
    }

    @Transactional
    public void deleteVehicle(Long id) {
        VehicleEntity vehicle = findEntityById(id);
        vehicleRepository.delete(vehicle);
    }

    private VehicleEntity findEntityById(Long id) {
        return vehicleRepository.findById(id)
            .orElseThrow(() -> new VehicleNotFoundException(id));
    }

    private String normalizeVin(String vin) {
        if (vin == null || vin.isBlank()) {
            return null;
        } 
        return vin.trim().toUpperCase();
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim().toUpperCase();
    }
}
