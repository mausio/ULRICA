package model.route;

public class ChargingStation {
    private final long id;
    private final double position;
    private final double maxPower;
    private boolean isAvailable;

    public ChargingStation(long id, double position, double maxPower) {
        this.id = id;
        this.position = position;
        this.maxPower = maxPower;
        this.isAvailable = true;
    }

    public long getId() {
        return id;
    }

    public double getPosition() {
        return position;
    }

    public double getMaxPower() {
        return maxPower;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
} 