package com.carauction.controller;
import com.carauction.dto.request.AnalysisRequest;
import com.carauction.dto.response.AnalysisResponse;
import com.carauction.service.AnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/api/vehicles/{vehicleId}/analysis")
public class AnalysisController{
    private final AnalysisService service;
    
    public AnalysisController(AnalysisService s){
        service=s;
    }

    @PutMapping
    public AnalysisResponse analyze(
            @PathVariable 
            Long vehicleId,
            
            @Valid 
            @RequestBody
            AnalysisRequest r){
                return service.analyze(vehicleId,r);
    }

    @GetMapping
    public AnalysisResponse get(@PathVariable Long vehicleId) {
        return service.get(vehicleId);
    }

    @DeleteMapping @ResponseStatus(HttpStatus.NO_CONTENT)

    public void delete(@PathVariable Long vehicleId) {
        service.delete(vehicleId);
    }
}
