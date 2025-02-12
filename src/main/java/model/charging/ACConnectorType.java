package model.charging;

public enum ACConnectorType {
    HOUSEHOLD_SOCKET(2.3, 0.90),
    CAMPING_SOCKET(3.7, 0.93),
    WALLBOX_11KW(11.0, 0.95);

    private final double maxPowerKW;
    private final double efficiency;

    ACConnectorType(double maxPowerKW, double efficiency) {
        this.maxPowerKW = maxPowerKW;
        this.efficiency = efficiency;
    }

    public double getMaxPowerKW() {
        return maxPowerKW;
    }

    public double getEfficiency() {
        return efficiency;
    }
} 