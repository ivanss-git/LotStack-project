package com.carauction.controller;

import com.carauction.dto.request.HistoryRequest;
import com.carauction.dto.response.HistoryResponse;
import com.carauction.service.HistoryService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/api/vehicles/{vehicleId}/history") 
public class HistoryController {
    private final HistoryService service;

    public HistoryController(HistoryService s) {
        service=s;
    }
    
    @PutMapping 
    public HistoryResponse save(@PathVariable Long vehicleId,@Valid @RequestBody HistoryRequest r) {
        return service.save(vehicleId,r);
    }
    
    @GetMapping public HistoryResponse get(@PathVariable Long vehicleId) {
        return service.get(vehicleId);
    }
    
    @DeleteMapping 
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void delete(@PathVariable Long vehicleId) {
        service.delete(vehicleId);
    }
}
