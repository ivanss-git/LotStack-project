package com.carauction.service;

import com.carauction.dto.request.VehicleRequest;
import com.carauction.dto.response.VehicleResponse;
import com.carauction.entity.VehicleEntity;
import com.carauction.exception.DuplicateResourceException;
import com.carauction.exception.ResourceNotFoundException;
import com.carauction.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    // Mock repository
    @Mock
    private VehicleRepository repository;

    // Real service under test
    @InjectMocks
    private VehicleService service;

    @Test
    void create_ValidRequest_NormalizesVinAndReturnsVehicle() {
        // Arrange
        VehicleRequest request = createRequest(
            " 1hgcm82633a004352 "
        );

        when(repository.existsByVin("1HGCM82633A004352"))
            .thenReturn(false);

        when(repository.save(any(VehicleEntity.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        VehicleResponse response = service.create(request);

        // Assert
        assertEquals(
            "1HGCM82633A004352",
            response.vin()
        );

        assertEquals(
            Short.valueOf((short) 2020),
            response.modelYear()
        );

        assertEquals("Honda", response.make());
        assertEquals("Accord", response.model());
        assertEquals("Sedan", response.bodyType());
        assertEquals(45000, response.odometer());
        assertTrue(response.keysPresent());

        verify(repository)
            .existsByVin("1HGCM82633A004352");

        verify(repository)
            .save(any(VehicleEntity.class));
    }

    @Test
    void create_ValidRequest_SavesCorrectEntity() {
        // Arrange
        VehicleRequest request = createRequest(
            "1hgcm82633a004352"
        );

        when(repository.existsByVin("1HGCM82633A004352"))
            .thenReturn(false);

        when(repository.save(any(VehicleEntity.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        service.create(request);

        ArgumentCaptor<VehicleEntity> captor =
            ArgumentCaptor.forClass(VehicleEntity.class);

        verify(repository).save(captor.capture());

        VehicleEntity savedVehicle = captor.getValue();

        // Assert
        assertEquals(
            "1HGCM82633A004352",
            savedVehicle.getVin()
        );

        assertEquals(
            Short.valueOf((short) 2020),
            savedVehicle.getModelYear()
        );

        assertEquals("Honda", savedVehicle.getMake());
        assertEquals("Accord", savedVehicle.getModel());
        assertEquals("Sedan", savedVehicle.getBodyType());
        assertEquals(45000, savedVehicle.getOdometer());
    }

    @Test
    void create_DuplicateVin_ThrowsDuplicateResourceException() {
        // Arrange
        VehicleRequest request = createRequest(
            "1hgcm82633a004352"
        );

        when(repository.existsByVin("1HGCM82633A004352"))
            .thenReturn(true);

        // Act
        DuplicateResourceException exception =
            assertThrows(
                DuplicateResourceException.class,
                () -> service.create(request)
            );

        // Assert
        assertEquals(
            "VIN already exists: 1HGCM82633A004352",
            exception.getMessage()
        );

        verify(repository)
            .existsByVin("1HGCM82633A004352");

        verify(repository, never())
            .save(any(VehicleEntity.class));
    }

    @Test
    void get_ExistingVehicle_ReturnsVehicle() {
        // Arrange
        Long vehicleId = 1L;
        VehicleEntity vehicle = createVehicle();

        when(repository.findById(vehicleId))
            .thenReturn(Optional.of(vehicle));

        // Act
        VehicleResponse response = service.get(vehicleId);

        // Assert
        assertEquals(
            "1HGCM82633A004352",
            response.vin()
        );

        assertEquals("Honda", response.make());
        assertEquals("Accord", response.model());

        verify(repository).findById(vehicleId);
    }

    @Test
    void get_MissingVehicle_ThrowsResourceNotFoundException() {
        // Arrange
        Long vehicleId = 99L;

        when(repository.findById(vehicleId))
            .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception =
            assertThrows(
                ResourceNotFoundException.class,
                () -> service.get(vehicleId)
            );

        // Assert
        assertEquals(
            "Vehicle not found: 99",
            exception.getMessage()
        );

        verify(repository).findById(vehicleId);
    }

    @Test
    void getByVin_ExistingVehicle_NormalizesVinAndReturnsVehicle() {
        // Arrange
        String requestedVin = "1hgcm82633a004352";

        when(repository.findByVin("1HGCM82633A004352"))
            .thenReturn(Optional.of(createVehicle()));

        // Act
        VehicleResponse response =
            service.getByVin(requestedVin);

        // Assert
        assertEquals(
            "1HGCM82633A004352",
            response.vin()
        );

        verify(repository)
            .findByVin("1HGCM82633A004352");
    }

    @Test
    void getByVin_MissingVehicle_ThrowsResourceNotFoundException() {
        // Arrange
        String requestedVin = "1hgcm82633a004352";

        when(repository.findByVin("1HGCM82633A004352"))
            .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception =
            assertThrows(
                ResourceNotFoundException.class,
                () -> service.getByVin(requestedVin)
            );

        // Assert
        assertEquals(
            "Vehicle not found for VIN: "
                + requestedVin,
            exception.getMessage()
        );

        verify(repository)
            .findByVin("1HGCM82633A004352");
    }

    @Test
    void all_MultipleVehicles_ReturnsAllResponses() {
        // Arrange
        VehicleEntity firstVehicle = createVehicle();

        VehicleEntity secondVehicle =
            new VehicleEntity(
                "2HGCM82633A004353",
                (short) 2021,
                false,
                60000,
                "Toyota",
                "Camry",
                "Sedan",
                "LE",
                "White",
                "2.5L",
                "Automatic",
                "FWD"
            );

        when(repository.findAll())
            .thenReturn(List.of(
                firstVehicle,
                secondVehicle
            ));

        // Act
        List<VehicleResponse> responses = service.all();

        // Assert
        assertEquals(2, responses.size());

        assertEquals(
            "1HGCM82633A004352",
            responses.get(0).vin()
        );

        assertEquals(
            "2HGCM82633A004353",
            responses.get(1).vin()
        );

        assertEquals(
            "Toyota",
            responses.get(1).make()
        );

        verify(repository).findAll();
    }

    @Test
    void all_NoVehicles_ReturnsEmptyList() {
        // Arrange
        when(repository.findAll())
            .thenReturn(List.of());

        // Act
        List<VehicleResponse> responses = service.all();

        // Assert
        assertTrue(responses.isEmpty());

        verify(repository).findAll();
    }

    @Test
    void update_ExistingVehicle_UpdatesAndReturnsVehicle() {
        // Arrange
        Long vehicleId = 1L;
        VehicleEntity existingVehicle = createVehicle();

        VehicleRequest updatedRequest =
            new VehicleRequest(
                " 2hgcm82633a004353 ",
                (short) 2022,
                false,
                55000,
                "Toyota",
                "Camry",
                "Coupe",
                "SE",
                "Black",
                "2.5L",
                "Automatic",
                "AWD"
            );

        when(repository.findById(vehicleId))
            .thenReturn(Optional.of(existingVehicle));

        when(repository.findByVin("2HGCM82633A004353"))
            .thenReturn(Optional.empty());

        // Act
        VehicleResponse response =
            service.update(vehicleId, updatedRequest);

        // Assert
        assertEquals(
            "2HGCM82633A004353",
            response.vin()
        );

        assertEquals(
            Short.valueOf((short) 2022),
            response.modelYear()
        );

        assertEquals("Toyota", response.make());
        assertEquals("Camry", response.model());
        assertEquals("Coupe", response.bodyType());
        assertEquals("Black", response.color());
        assertEquals("AWD", response.drivetrain());
        assertEquals(55000, response.odometer());

        verify(repository).findById(vehicleId);

        verify(repository)
            .findByVin("2HGCM82633A004353");
    }

    @Test
    void update_DuplicateVin_ThrowsDuplicateResourceException() {
        // Arrange
        Long vehicleId = 1L;
        VehicleEntity existingVehicle = createVehicle();

        VehicleRequest request = createRequest(
            "2hgcm82633a004353"
        );

        VehicleEntity conflictingVehicle =
            mock(VehicleEntity.class);

        when(conflictingVehicle.getId())
            .thenReturn(2L);

        when(repository.findById(vehicleId))
            .thenReturn(Optional.of(existingVehicle));

        when(repository.findByVin("2HGCM82633A004353"))
            .thenReturn(Optional.of(conflictingVehicle));

        // Act
        DuplicateResourceException exception =
            assertThrows(
                DuplicateResourceException.class,
                () -> service.update(vehicleId, request)
            );

        // Assert
        assertEquals(
            "VIN already exists: 2HGCM82633A004353",
            exception.getMessage()
        );

        verify(repository).findById(vehicleId);

        verify(repository)
            .findByVin("2HGCM82633A004353");
    }

    @Test
    void update_MissingVehicle_ThrowsResourceNotFoundException() {
        // Arrange
        Long vehicleId = 99L;
        VehicleRequest request = createRequest(
            "1HGCM82633A004352"
        );

        when(repository.findById(vehicleId))
            .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception =
            assertThrows(
                ResourceNotFoundException.class,
                () -> service.update(vehicleId, request)
            );

        // Assert
        assertEquals(
            "Vehicle not found: 99",
            exception.getMessage()
        );

        verify(repository).findById(vehicleId);

        verify(repository, never())
            .findByVin(any());
    }

    @Test
    void delete_ExistingVehicle_DeletesVehicle() {
        // Arrange
        Long vehicleId = 1L;
        VehicleEntity vehicle = createVehicle();

        when(repository.findById(vehicleId))
            .thenReturn(Optional.of(vehicle));

        // Act
        service.delete(vehicleId);

        // Assert
        verify(repository).findById(vehicleId);
        verify(repository).delete(vehicle);
    }

    @Test
    void delete_MissingVehicle_ThrowsResourceNotFoundException() {
        // Arrange
        Long vehicleId = 99L;

        when(repository.findById(vehicleId))
            .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception =
            assertThrows(
                ResourceNotFoundException.class,
                () -> service.delete(vehicleId)
            );

        // Assert
        assertEquals(
            "Vehicle not found: 99",
            exception.getMessage()
        );

        verify(repository).findById(vehicleId);

        verify(repository, never())
            .delete(any(VehicleEntity.class));
    }

    @Test
    void require_ExistingVehicle_ReturnsEntity() {
        // Arrange
        Long vehicleId = 1L;
        VehicleEntity vehicle = createVehicle();

        when(repository.findById(vehicleId))
            .thenReturn(Optional.of(vehicle));

        // Act
        VehicleEntity result = service.require(vehicleId);

        // Assert
        assertSame(vehicle, result);

        verify(repository).findById(vehicleId);
    }

    private VehicleRequest createRequest(String vin) {
        return new VehicleRequest(
            vin,
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
