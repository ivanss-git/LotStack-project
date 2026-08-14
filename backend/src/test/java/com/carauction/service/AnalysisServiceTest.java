package com.carauction.service;

import com.carauction.dto.request.AnalysisRequest;
import com.carauction.dto.response.AnalysisResponse;
import com.carauction.entity.AuctionAnalyzerEntity;
import com.carauction.entity.VehicleEntity;
import com.carauction.repository.AnalysisResultRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalysisServiceTest {

    @Mock
    private AnalysisResultRepository repository;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private AnalysisService service;

    @Test
    void analyze_NewAnalysis_CalculatesAndSavesResult() {
        // Arrange
        Long vehicleId = 1L;

        AnalysisRequest request = new AnalysisRequest(
            new BigDecimal("10000"), // purchase price
            new BigDecimal("16000"), // market value
            new BigDecimal("1000"),  // repair cost
            new BigDecimal("500"),   // transport
            new BigDecimal("750"),   // auction fees
            new BigDecimal("250"),   // title adjustment
            new BigDecimal("2000")   // profit goal
        );

        VehicleEntity vehicle = createVehicle();

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.empty());

        when(vehicleService.require(vehicleId))
            .thenReturn(vehicle);

        when(repository.save(any(AuctionAnalyzerEntity.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        AnalysisResponse response = service.analyze(vehicleId, request);

        // Assert
        assertEquals(0, new BigDecimal("12500")
            .compareTo(response.totalCost()));

        assertEquals(0, new BigDecimal("3500")
            .compareTo(response.expectedProfit()));

        assertEquals(0, new BigDecimal("11500")
            .compareTo(response.maxBid()));

        assertTrue(response.goodPurchase());
        assertEquals("Good Purchase", response.recommendation());

        verify(repository).findByVehicle_Id(vehicleId);
        verify(vehicleService).require(vehicleId);
        verify(repository).save(any(AuctionAnalyzerEntity.class));
    }

    @Test
    void analyze_MaxBidWouldBeNegative_SetsMaxBidToZero() {
        // Arrange
        Long vehicleId = 1L;

        AnalysisRequest request = new AnalysisRequest(
            new BigDecimal("10000"),
            new BigDecimal("2000"),
            new BigDecimal("3000"),
            new BigDecimal("1000"),
            new BigDecimal("500"),
            new BigDecimal("500"),
            new BigDecimal("2000")
        );

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.empty());

        when(vehicleService.require(vehicleId))
            .thenReturn(createVehicle());

        when(repository.save(any(AuctionAnalyzerEntity.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        AnalysisResponse response = service.analyze(vehicleId, request);

        // Assert
        assertEquals(0, BigDecimal.ZERO.compareTo(response.maxBid()));
    }

    @Test
    void analyze_NewAnalysis_SavesCalculatedEntity() {
        // Arrange
        Long vehicleId = 1L;

        AnalysisRequest request = new AnalysisRequest(
            new BigDecimal("10000"),
            new BigDecimal("16000"),
            new BigDecimal("1000"),
            new BigDecimal("500"),
            new BigDecimal("750"),
            new BigDecimal("250"),
            new BigDecimal("2000")
        );

        when(repository.findByVehicle_Id(vehicleId))
            .thenReturn(Optional.empty());

        when(vehicleService.require(vehicleId))
            .thenReturn(createVehicle());

        when(repository.save(any(AuctionAnalyzerEntity.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        service.analyze(vehicleId, request);

        // Capture the entity passed to the repository
        ArgumentCaptor<AuctionAnalyzerEntity> captor =
            ArgumentCaptor.forClass(AuctionAnalyzerEntity.class);

        verify(repository).save(captor.capture());

        AuctionAnalyzerEntity savedEntity = captor.getValue();

        // Assert
        assertEquals(
            0,
            new BigDecimal("12500").compareTo(savedEntity.getTotalCost())
        );

        assertEquals(
            0,
            new BigDecimal("3500").compareTo(savedEntity.getExpectedProfit())
        );

        assertTrue(savedEntity.getGoodPurchase());
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