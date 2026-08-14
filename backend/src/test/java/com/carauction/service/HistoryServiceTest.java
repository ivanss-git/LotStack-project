package com.carauction.service;

import com.carauction.repository.VehicleHistoryRepository;
import com.carauction.dto.request.HistoryRequest;
import com.carauction.dto.response.HistoryResponse;
import com.carauction.entity.VehicleEntity;
import com.carauction.entity.VehicleHistoryEntity;
import com.carauction.exception.ResourceNotFoundException;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Test Process: Arrange -> Act -> Assert
@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {

    // Creating the fake repository
    @Mock
    private VehicleHistoryRepository repository;

    // Creating the fake dependency to find the parent vehicle
    @Mock
    private VehicleService vehicleService;

    // Creating the real history service with the two mocks injected
    @InjectMocks
    private HistoryService service;

    @Test
    void save_NewHistory_CreatesAndReturnsHistory() {
        Long vehicleId = 1L;
        HistoryRequest request = createRequest();
        VehicleEntity vehicle = createVehicle();

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.empty());

        when(vehicleService.require(vehicleId))
            .thenReturn(vehicle);

        when(repository.save(any(VehicleHistoryEntity.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        HistoryResponse response = service.save(vehicleId, request);

        // Assert
        assertEquals(2, response.previousOwners());
        assertEquals(1, response.previousAccidents());
        assertEquals("MINOR", response.damageCode());
        assertEquals("Clean", response.classification());
        assertEquals("Intact", response.airbagStatus());

        verify(repository).findByVehicle_Id(vehicleId);
        verify(vehicleService).require(vehicleId);
        verify(repository).save(any(VehicleHistoryEntity.class));
    }
    // Save Test
    @Test
    void save_ExistingHistory_UpdatesExistingEntity() {
        // Arrange
        Long vehicleId = 1L;
        VehicleEntity vehicle = createVehicle();

        VehicleHistoryEntity existingHistory =
            new VehicleHistoryEntity(
                vehicle,
                1,
                0,
                "NONE",
                "Unknown",
                "Unknown"
            );

        HistoryRequest updatedRequest = new HistoryRequest(
            3,
            2,
            "MAJOR",
            "Salvage",
            "Deployed"
        );

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.of(existingHistory));

        when(repository.save(existingHistory))
            .thenReturn(existingHistory);

        // Act
        HistoryResponse response =
            service.save(vehicleId, updatedRequest);

        // Assert
        assertEquals(3, response.previousOwners());
        assertEquals(2, response.previousAccidents());
        assertEquals("MAJOR", response.damageCode());
        assertEquals("Salvage", response.classification());
        assertEquals("Deployed", response.airbagStatus());

        verify(repository).save(existingHistory);

        // Existing history means the vehicle does not need to be loaded again.
        verify(vehicleService, never()).require(vehicleId);
    }

    @Test
    void get_ExistingHistory_ReturnsResponse() {
        // Arrange
        Long vehicleId = 1L;

        VehicleHistoryEntity history =
            new VehicleHistoryEntity(
                createVehicle(),
                2,
                1,
                "MINOR",
                "Clean",
                "Intact"
            );

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.of(history));

        // Act
        HistoryResponse response = service.get(vehicleId);

        // Assert
        assertEquals(2, response.previousOwners());
        assertEquals(1, response.previousAccidents());
        assertEquals("Clean", response.classification());

        verify(repository).findByVehicle_Id(vehicleId);
    }

    @Test
    void get_MissingHistory_ThrowsResourceNotFoundException() {
        // Arrange
        Long vehicleId = 99L;

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> service.get(vehicleId)
        );

        // Assert
        assertEquals(
            "History not found for vehicle: 99",
            exception.getMessage()
        );

        verify(repository).findByVehicle_Id(vehicleId);
    }

    @Test
    void delete_ExistingHistory_DeletesEntity() {
        // Arrange
        Long vehicleId = 1L;

        VehicleHistoryEntity history =
            new VehicleHistoryEntity(
                createVehicle(),
                2,
                1,
                "MINOR",
                "Clean",
                "Intact"
            );

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.of(history));

        // Act
        service.delete(vehicleId);

        // Assert
        verify(repository).delete(history);
    }

    @Test
    void delete_MissingHistory_ThrowsResourceNotFoundException() {
        // Arrange
        Long vehicleId = 99L;

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.empty());

        // Act and assert
        assertThrows(
            ResourceNotFoundException.class,
            () -> service.delete(vehicleId)
        );

        verify(repository, never())
            .delete(any(VehicleHistoryEntity.class));
    }

    private HistoryRequest createRequest() {
        return new HistoryRequest(
            2,
            1,
            "MINOR",
            "Clean",
            "Intact"
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
