package com.carauction.service;

import com.carauction.dto.response.ImportResponse;
import com.carauction.entity.AuctionListingEntity;
import com.carauction.repository.AuctionListingRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuctionListingImportService {

    private static final String REDACTED = "[PREMIUM]";
    private static final DateTimeFormatter SOURCE_DATE =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private final AuctionListingRepository repository;
    private final Path csvPath;

    public AuctionListingImportService(
            AuctionListingRepository repository,
            @Value("${app.demo.csv-path:./data/iaai_dataset_download.csv}") String csvPath) {
        this.repository = repository;
        this.csvPath = Path.of(csvPath).normalize();
    }

    @Transactional
    public ImportResponse importConfiguredFile() {
        if (!Files.isRegularFile(csvPath)) {
            throw new IllegalStateException("Demo CSV not found: " + csvPath.toAbsolutePath());
        }

        int read = 0;
        int inserted = 0;
        int updated = 0;
        int skipped = 0;
        List<String> errors = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setIgnoreSurroundingSpaces(true)
                     .get()
                     .parse(reader)) {

            for (CSVRecord row : parser) {
                read++;
                try {
                    String sourceRecordId = required(row, "_primaryKey");
                    AuctionListingEntity listing = repository.findBySourceRecordId(sourceRecordId)
                            .orElseGet(() -> new AuctionListingEntity(sourceRecordId));
                    boolean existing = listing.getId() != null;

                    listing.update(
                            date(row, "_firstSeenAt"), date(row, "_lastSeenAt"),
                            value(row, "stockNumber"), value(row, "itemId"),
                            value(row, "salvageId"), value(row, "auctionId"),
                            shortNumber(row, "year"), required(row, "make"),
                            required(row, "model"), value(row, "series"),
                            value(row, "bodyStyle"), value(row, "exteriorColor"),
                            value(row, "engine"), shortNumber(row, "cylinders"),
                            value(row, "transmission"), value(row, "drivetrain"),
                            value(row, "fuelType"), value(row, "primaryDamage"),
                            value(row, "secondaryDamage"), value(row, "lossType"),
                            value(row, "titleType"), value(row, "titleCode"),
                            value(row, "titleState"), bool(row, "hasKeys"),
                            bool(row, "runAndDrive"), value(row, "startsDesc"),
                            integer(row, "mileage"), value(row, "odometerBrand"),
                            value(row, "odometerUnit"), value(row, "airbagState"),
                            shortNumber(row, "vehicleGrade"), date(row, "auctionDateTime"),
                            value(row, "branchNumber"), value(row, "branchName"),
                            value(row, "locationCity"), value(row, "locationState"),
                            decimal(row, "locationLatitude"), decimal(row, "locationLongitude"),
                            value(row, "providerType"), value(row, "countryOfOrigin"));

                    repository.save(listing);
                    if (existing) updated++; else inserted++;
                } catch (RuntimeException ex) {
                    skipped++;
                    if (errors.size() < 20) {
                        errors.add("CSV row " + row.getRecordNumber() + ": " + ex.getMessage());
                    }
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to read demo CSV: " + csvPath.toAbsolutePath(), ex);
        }

        return new ImportResponse(read, inserted, updated, skipped, List.copyOf(errors));
    }

    private static String required(CSVRecord row, String name) {
        String result = value(row, name);
        if (result == null) throw new IllegalArgumentException(name + " is required");
        return result;
    }

    private static String value(CSVRecord row, String name) {
        String raw = row.get(name);
        if (raw == null) return null;
        String result = raw.trim();
        return result.isEmpty() || REDACTED.equalsIgnoreCase(result) ? null : result;
    }

    private static Boolean bool(CSVRecord row, String name) {
        String result = value(row, name);
        return result == null ? null : Boolean.valueOf(result);
    }

    private static Integer integer(CSVRecord row, String name) {
        String result = value(row, name);
        return result == null ? null : Integer.valueOf(result);
    }

    private static Short shortNumber(CSVRecord row, String name) {
        String result = value(row, name);
        return result == null ? null : Short.valueOf(result);
    }

    private static Double decimal(CSVRecord row, String name) {
        String result = value(row, name);
        return result == null ? null : Double.valueOf(result);
    }

    private static OffsetDateTime date(CSVRecord row, String name) {
        String result = value(row, name);
        return result == null ? null
                : LocalDateTime.parse(result, SOURCE_DATE).atOffset(ZoneOffset.UTC);
    }
}
