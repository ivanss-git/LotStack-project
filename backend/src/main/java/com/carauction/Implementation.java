package backend.src.main.java.com.carauction;

public class Implementation implements Interface {
    
    @Override
    public double marketValue(Car car) {
        double value = car.baseValue;
        int age = 2025 - car.year;

        if(age <= 5) {
            value *= .80;
        } else if(age <= 10) {
            value *= .65;
        } else if(age <= 15) {
            value *= .45;
        } else {
            value *= .25;
        }

        if(car.mileage <= 50000) {
            value *= 1.5;
        } else if(car.mileage <= 75000) {
            value *= 1.0;
        } else if(car.mileage <= 90000) {
            value *= .90;
        } else if(car.mileage <= 115000) {
            value *= .75;
        } else {
            value *= .60;
        }

        value *= titleFactor(car);
        return Math.max(1000,value);

    }
        
    @Override
    public double repairCost(Car car) {
        double repairCost = 0.0;
        
        switch(car.damageType.toLowerCase()) {
            case "none": 
                repairCost = 0.0;
                break;
            case "rear end":
                repairCost = 500.0;
                break;
                
            case "front end":
                repairCost = 500.0;
                break;
                
            case "minor dent":
                repairCost = 300.0;
                break;
                
            case "frame damage":
                repairCost = 1000.0;
                break;

            case "burn": 
                repairCost = 0.0;
                break;

            case "flood": 
                repairCost = 0.0;
                break;

            default:
                repairCost = 1000.0;
                break;
                
        }
        return repairCost;
    }
    @Override 
    public double titleFactor(Car car) {
        switch(car.titleCode.toLowerCase()) {
            case "salvage":
                return 0.80;
            case "rebuilt":
                return 0.90;
            default:
                return 1.0;
        }
    }
    @Override
    public double maxBid(Car car) {
        double value = marketValue(car);
        double cost = repairCost(car) + car.auctionFees + car.towFee;
        return Math.max(0, value - car.profitGoal - cost);
    }
}

