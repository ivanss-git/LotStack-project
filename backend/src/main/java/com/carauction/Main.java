package com.carauction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.carauction.model.Vehicle;
import com.carauction.service.AuctionAnalyzer;

import java.text.NumberFormat;

// Should read, analyze each, and print results

public class Main {
	private static final String DEFAULT_CSV = "cars.csv";
	private static final NumberFormat CF = NumberFormat.getCurrencyInstance(Locale.US);

	public static void main(String[] args) {
		String csvPath = args.length > 0 ? args[0] : DEFAULT_CSV;
		List<Vehicle> cars = new ArrayList<>();

		try(BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
			String line;
			boolean headerSkipped = false;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if(line.isEmpty() || line.startsWith("#")) continue;
				if(!headerSkipped && line.toLowerCase().contains("make,year,mileage")) {
					headerSkipped = true;
					continue;
				}

				String[] d = line.split(",");
				if(d.length < 9) {
					System.err.println("Skipping malformed row: " + line);
					continue;
				}
				
				String make = d[0].trim();
				int year = Integer.parseInt(d[1].trim());
				int mileage = Integer.parseInt(d[2].trim());
				String titleCode = d[3].trim();
				String damageType  = d[4].trim();
				double baseValue = Double.parseDouble(d[5].trim());
				double profitGoal = Double.parseDouble(d[6].trim());
				double auctionFees = Double.parseDouble(d[7].trim());
				double towFee = Double.parseDouble(d[8].trim());

				cars.add(new Vehicle(year, mileage, auctionFees, towFee, baseValue, profitGoal, titleCode, make, damageType));
			}
		} catch (IOException e) {
			System.err.println("Failed to read CSV: " + csvPath);
			e.printStackTrace();
			return;
		}
		
		AuctionAnalyzer analyzer = new AuctionAnalyzer(null, null, null, null);
		for (Vehicle car : cars) {
			double mv = analyzer.marketValue(car);
			double rc = analyzer.repairCost(car);
			double tf = analyzer.titleFactor(car);
			double mb = analyzer.maxBid(car);
			
			System.out.println("---------------------------------------------------------");
			System.out.println(car.toString());
			System.out.printf("%-13s %s%n", "Market Value:", CF.format(mv));
			System.out.printf("%-13s %s%n", "Repair Cost:", CF.format(rc));
			System.out.printf("%-13s %.2f%n", "Title Factor:", tf);
			System.out.printf("%-13s %s%n", "Max Bid:", CF.format(mb));
		}
		if(cars.isEmpty()) {
			System.out.println("No cars loaded. Provide a CSV file path or put cars next to the program. ");
		}
	}
}
   
	
