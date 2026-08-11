package com.carauction.controller;

import com.carauction.dto.request.PaperworkRequest;
import com.carauction.dto.response.PaperworkResponse;
import com.carauction.service.PaperworkService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/api/vehicles/{vehicleId}/paperwork") 
public class PaperworkController {
    private final PaperworkService service;

    public PaperworkController(PaperworkService s) {
        service=s;
    }
    
    @PutMapping 
    public PaperworkResponse save(@PathVariable Long vehicleId,@Valid @RequestBody PaperworkRequest r) {
        return service.save(vehicleId,r);
    }
    
    @GetMapping public PaperworkResponse get(@PathVariable Long vehicleId) {
        return service.get(vehicleId);
    }
    
    @DeleteMapping 
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void delete(@PathVariable Long vehicleId) {
        service.delete(vehicleId);
    }}
