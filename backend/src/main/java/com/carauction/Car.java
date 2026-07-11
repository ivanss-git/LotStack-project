package backend.src.main.java.com.carauction;

public class Car {
    public int year;
    public int mileage;
    public double auctionFees;
    public double towFee;
    public double baseValue;
    public double profitGoal;
    public String titleCode;
    public String make;
    public String damageType;

    public Car(int year,int mileage,double auctionFees,double towFee,double baseValue,double profitGoal, String titleCode, String make, String damageType) {
        this.year = year;
        this.mileage = mileage;
        this.auctionFees = auctionFees;
        this.towFee = towFee;
        this.baseValue = baseValue;
        this.profitGoal = profitGoal;
        this.titleCode = titleCode;
        this.make = make;
        this.damageType = damageType;
    } 
    @Override
    public String toString() {
        return "\nMake: " +make+ ", Year: " +year+ ", Mileage: " +mileage+ ", Title: " +titleCode+ ", Damage Type: " +damageType;

    }

}
