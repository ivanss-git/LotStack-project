package com.carauction.controller;

import com.carauction.service.AuctionListingImportService;
import com.carauction.service.RuleBasedListingAnalysisService;
import com.carauction.repository.VehicleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final VehicleRepository vehicleRepository;
    private final AuctionListingImportService importer;
    private final RuleBasedListingAnalysisService listingAnalyzer;

    public DatabaseInitializer(
        VehicleRepository vehicleRepository,
        AuctionListingImportService importer,
        RuleBasedListingAnalysisService listingAnalyzer
    ) {
        this.vehicleRepository = vehicleRepository;
        this.importer = importer;
        this.listingAnalyzer = listingAnalyzer;
    }

    @Override
    @Transactional // Ensures an open database transaction for the import processes
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println(">>> Spring Context is fully refreshed and stable. Checking database state...");
        
        try {
            long count = vehicleRepository.count();
            System.out.println(">>> Current vehicle count in database: " + count);

            if (count == 0) {
                System.out.println(">>> Database is empty! Initiating automatic data import...");
                importer.importConfiguredFile();
                
                System.out.println(">>> Running rule-based listing analysis metrics...");
                int analyzedCount = listingAnalyzer.analyzeAll();
                System.out.println(">>> Successfully initialized and analyzed " + analyzedCount + " vehicles automatically!");
            } else {
                System.out.println(">>> Database already contains " + count + " records. Skipping initial setup.");
            }
        } catch (Exception e) {
            System.err.println(">>> Automated database initialization encountered a problem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

