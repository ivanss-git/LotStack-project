package com.carauction.service;

import com.carauction.dto.request.TransportRequest;
import com.carauction.dto.response.TransportResponse;
import com.carauction.entity.VehicleEntity;
import com.carauction.entity.VehicleTransportEstimateEntity;
import com.carauction.exception.ResourceNotFoundException;
import com.carauction.repository.VehicleTransportEstimateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransportServiceTest {

    // Mock repository
    @Mock
    private VehicleTransportEstimateRepository repository;

    // Mock dependency used to retrieve the parent vehicle
    @Mock
    private VehicleService vehicleService;

    // Real service under test
    @InjectMocks
    private TransportService service;

    @Test
    void save_NewTransport_CreatesAndReturnsTransport() {
        // Arrange
        Long vehicleId = 1L;
        TransportRequest request = createRequest();
        VehicleEntity vehicle = createVehicle();

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.empty());

        when(vehicleService.require(vehicleId))
            .thenReturn(vehicle);

        when(repository.save(
            any(VehicleTransportEstimateEntity.class)
        )).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TransportResponse response =
            service.save(vehicleId, request);

        // Assert
        assertEquals(
            "Dallas, TX",
            response.pickupLocation()
        );

        assertEquals(
            250,
            response.distanceInMiles()
        );

        assertEquals(
            "Running",
            response.operability()
        );

        assertEquals(
            "Open",
            response.trailerType()
        );

        assertEquals(
            "Sedan",
            response.vehicleType()
        );

        assertBigDecimalEquals(
            "50.00",
            response.additionalFees()
        );

        assertBigDecimalEquals(
            "500.00",
            response.estimatedCost()
        );

        verify(repository).findByVehicle_Id(vehicleId);
        verify(vehicleService).require(vehicleId);

        verify(repository).save(
            any(VehicleTransportEstimateEntity.class)
        );
    }

    @Test
    void save_ExistingTransport_UpdatesAndReturnsTransport() {
        // Arrange
        Long vehicleId = 1L;
        VehicleEntity vehicle = createVehicle();

        VehicleTransportEstimateEntity existingTransport =
            new VehicleTransportEstimateEntity(
                vehicle,
                "Austin, TX",
                100,
                "Not Running",
                "Enclosed",
                "SUV",
                new BigDecimal("100.00"),
                new BigDecimal("800.00")
            );

        TransportRequest request = createRequest();

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.of(existingTransport));

        when(repository.save(
            any(VehicleTransportEstimateEntity.class)
        )).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TransportResponse response =
            service.save(vehicleId, request);

        // Assert
        assertEquals(
            "Dallas, TX",
            response.pickupLocation()
        );

        assertEquals(
            250,
            response.distanceInMiles()
        );

        assertEquals(
            "Running",
            response.operability()
        );

        assertEquals(
            "Open",
            response.trailerType()
        );

        assertEquals(
            "Sedan",
            response.vehicleType()
        );

        assertBigDecimalEquals(
            "50.00",
            response.additionalFees()
        );

        assertBigDecimalEquals(
            "500.00",
            response.estimatedCost()
        );

        verify(repository).findByVehicle_Id(vehicleId);

        verify(repository).save(
            any(VehicleTransportEstimateEntity.class)
        );

        // Existing transport already has its vehicle.
        verify(vehicleService, never()).require(vehicleId);
    }

    @Test
    void get_ExistingTransport_ReturnsTransport() {
        // Arrange
        Long vehicleId = 1L;

        VehicleTransportEstimateEntity transport =
            createTransportEntity();

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.of(transport));

        // Act
        TransportResponse response =
            service.get(vehicleId);

        // Assert
        assertEquals(
            "Dallas, TX",
            response.pickupLocation()
        );

        assertEquals(
            250,
            response.distanceInMiles()
        );

        assertEquals(
            "Running",
            response.operability()
        );

        assertBigDecimalEquals(
            "500.00",
            response.estimatedCost()
        );

        verify(repository).findByVehicle_Id(vehicleId);
    }

    @Test
    void get_MissingTransport_ThrowsResourceNotFoundException() {
        // Arrange
        Long vehicleId = 99L;

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception =
            assertThrows(
                ResourceNotFoundException.class,
                () -> service.get(vehicleId)
            );

        // Assert
        assertEquals(
            "Transport estimate not found for vehicle: 99",
            exception.getMessage()
        );

        verify(repository).findByVehicle_Id(vehicleId);
    }

    @Test
    void delete_ExistingTransport_DeletesTransport() {
        // Arrange
        Long vehicleId = 1L;

        VehicleTransportEstimateEntity transport =
            createTransportEntity();

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.of(transport));

        // Act
        service.delete(vehicleId);

        // Assert
        verify(repository).findByVehicle_Id(vehicleId);
        verify(repository).delete(transport);
    }

    @Test
    void delete_MissingTransport_ThrowsResourceNotFoundException() {
        // Arrange
        Long vehicleId = 99L;

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception =
            assertThrows(
                ResourceNotFoundException.class,
                () -> service.delete(vehicleId)
            );

        // Assert
        assertEquals(
            "Transport estimate not found for vehicle: 99",
            exception.getMessage()
        );

        verify(repository).findByVehicle_Id(vehicleId);

        verify(repository, never()).delete(
            any(VehicleTransportEstimateEntity.class)
        );
    }

    private TransportRequest createRequest() {
        return new TransportRequest(
            "Dallas, TX",
            250,
            "Running",
            "Open",
            "Sedan",
            new BigDecimal("50.00"),
            new BigDecimal("500.00")
        );
    }

    private VehicleTransportEstimateEntity
            createTransportEntity() {

        return new VehicleTransportEstimateEntity(
            createVehicle(),
            "Dallas, TX",
            250,
            "Running",
            "Open",
            "Sedan",
            new BigDecimal("50.00"),
            new BigDecimal("500.00")
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

    private void assertBigDecimalEquals(
        String expected,
        BigDecimal actual
    ) {
        assertEquals(
            0,
            new BigDecimal(expected).compareTo(actual)
        );
    }
}