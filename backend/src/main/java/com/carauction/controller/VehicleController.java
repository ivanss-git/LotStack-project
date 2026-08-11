package com.carauction.controller;

import com.carauction.dto.request.VehicleRequest;
import com.carauction.dto.response.VehicleResponse;
import com.carauction.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService s) {
        service = s;
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> create(@Valid @RequestBody VehicleRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r));
    }

    @GetMapping("/{id}")
    public VehicleResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<VehicleResponse> all() {
        return service.all();
    }

    @GetMapping("/vin/{vin}")
    public VehicleResponse vin(@PathVariable String vin) {
        return service.getByVin(vin);
    }

    @PatchMapping("/{id}")
    public VehicleResponse update(@PathVariable Long id, @Valid @RequestBody VehicleRequest r) {
        return service.update(id, r);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
