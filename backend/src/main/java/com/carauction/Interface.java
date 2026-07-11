package backend.src.main.java.com.carauction;


public interface Interface {
    double marketValue(Car car);
    double repairCost(Car car);
    double titleFactor(Car car);
    double maxBid(Car car);

}
