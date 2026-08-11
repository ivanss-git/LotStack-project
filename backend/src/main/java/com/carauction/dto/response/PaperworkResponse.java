package com.carauction.dto.response;
import com.carauction.entity.VehiclePaperworkEntity; 
import java.math.BigDecimal;

public record PaperworkResponse(
    Long id,
    Long vehicleId,
    String titleStatus,
    String titleState,
    String lienStatus,
    Boolean billOfSalePresent,
    BigDecimal auctionFees
    ) {
        public static PaperworkResponse from(VehiclePaperworkEntity p) {
            return new PaperworkResponse(
                p.getId(),
                p.getVehicle().getId(),
                p.getTitleStatus(),
                p.getTitleState(),
                p.getLienStatus(),
                p.getBillOfSalePresent(),
                p.getAuctionFees()
            );
        }
    }
