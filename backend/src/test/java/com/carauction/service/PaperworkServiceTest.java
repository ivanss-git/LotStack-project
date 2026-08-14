package com.carauction.service;

import com.carauction.entity.VehicleEntity;
import com.carauction.entity.VehiclePaperworkEntity;
import com.carauction.repository.VehiclePaperworkRepository;
import com.carauction.dto.request.PaperworkRequest;
import com.carauction.dto.response.PaperworkResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
// Arrange, Act, Assert
// We need to create a real service and a mock repo for service tests
@ExtendWith(MockitoExtension.class)
class PaperworkServiceTest {

    // Creating the fake repository
    @Mock
    private VehiclePaperworkRepository repository;

    // Creating the fake service
    @Mock
    private VehicleService vehicleService;

    // Creating the real service
    @InjectMocks
    private PaperworkService service;

    // Test paperwork reponse method params and inner declarations

    //Arrange
    @Test
    void save_NewPaperwork_CreatesAndReturnsPaperwork() {
        Long vehicleId = 1L;
        PaperworkRequest request = createRequest();
        VehicleEntity vehicle = createVehicle();

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.empty());

        when(vehicleService.require(vehicleId))
            .thenReturn(vehicle);

        when(repository.save(any(VehiclePaperworkEntity.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Act - Checks find, require, and save
        PaperworkResponse response = service.save(vehicleId, request);

        // Assert
        assertEquals("Clean", response.titleStatus());
        assertEquals("TX", response.titleState());
        assertEquals("None", response.lienStatus());
        assertTrue(response.billOfSalePresent());

        assertEquals(
            0,
            new BigDecimal("750.00")
            .compareTo(response.auctionFees()
            )
        );

        verify(repository).findByVehicle_Id(vehicleId);
        verify(vehicleService).require(vehicleId);
        verify(repository).save(any(VehiclePaperworkEntity.class));
    }

    private PaperworkRequest createRequest() {
        return new PaperworkRequest(
            "Clean",
            "TX",
            "None",
            true,
            new BigDecimal("750.00")
        );
    }

    private VehicleEntity createVehicle() {
        return new VehicleEntity(
            "1HGCM82633A004352",
            (short) 2020,
            true,
            45000,
            "Honda",
            "Accord",
            "Sedan",
            "EX",
            "Blue",
            "2.0L",
            "Automatic",
            "FWD"
        );
    }
}
