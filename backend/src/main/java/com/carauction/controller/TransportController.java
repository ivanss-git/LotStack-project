package com.carauction.controller;

import com.carauction.dto.request.TransportRequest;
import com.carauction.dto.response.TransportResponse;
import com.carauction.service.TransportService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/api/vehicles/{vehicleId}/transport") 
public class TransportController {
    private final TransportService service;

    public TransportController(TransportService s) {
        service=s;
    }

    @PutMapping 
    public TransportResponse save(@PathVariable Long vehicleId,@Valid @RequestBody TransportRequest r) {
        return service.save(vehicleId,r);
    }

    @GetMapping public TransportResponse get(@PathVariable Long vehicleId) {
        return service.get(vehicleId);
    }
    
    @DeleteMapping 
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void delete(@PathVariable Long vehicleId) {
        service.delete(vehicleId);
    }
}

