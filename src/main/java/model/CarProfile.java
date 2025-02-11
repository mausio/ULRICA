package model;

import model.strategy.RangeCalculationStrategy;
import model.strategy.SimpleFactorRangeStrategy;

public class CarProfile {
    private String id;
    private String name;
    private String manufacturer;
    private String model;
    private int buildYear;
    private boolean hasHeatPump;
    
    // New attributes
    private double batteryCapacity; // in kWh
    private EfficiencyMode efficiencyMode;
    private double wltpRange; // in km
    private double maxDcChargingPower; // in kW
    private double maxAcChargingPower; // in kW
    private ConsumptionProfile consumptionProfile;
    private BatteryProfile batteryProfile;
    private ChargingProfile chargingProfile;
    private RangeCalculationStrategy rangeCalculationStrategy;

    public enum EfficiencyMode {
        ECO(0.85),      // 15% less consumption than normal
        NORMAL(1.0),    // baseline consumption
        SPORT(1.20);    // 20% more consumption than normal

        private final double consumptionFactor;

        EfficiencyMode(double consumptionFactor) {
            this.consumptionFactor = consumptionFactor;
        }

        public double getConsumptionFactor() {
            return consumptionFactor;
        }
    }

    // Constructor for basic profile
    public CarProfile(String name, String manufacturer, String model, int buildYear, boolean hasHeatPump,
                     double batteryCapacity, double wltpRange,
                     double maxDcChargingPower, double maxAcChargingPower) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.manufacturer = manufacturer;
        this.model = model;
        this.buildYear = buildYear;
        this.hasHeatPump = hasHeatPump;
        this.batteryCapacity = batteryCapacity;
        this.wltpRange = wltpRange;
        this.maxDcChargingPower = maxDcChargingPower;
        this.maxAcChargingPower = maxAcChargingPower;
        this.rangeCalculationStrategy = new SimpleFactorRangeStrategy(); // Default strategy
    }

    // Calculate consumption for a given speed and efficiency mode
    public double calculateConsumption(int speed, EfficiencyMode mode) {
        if (consumptionProfile == null) {
            throw new IllegalStateException("Consumption profile not set");
        }
        Double baseConsumption = consumptionProfile.getConsumptionAtSpeed(speed);
        if (baseConsumption == null) {
            throw new IllegalArgumentException("No consumption data for speed: " + speed);
        }
        return baseConsumption * mode.getConsumptionFactor();
    }

    // Calculate range for a given efficiency mode using the strategy
    public double calculateRange(EfficiencyMode mode) {
        return rangeCalculationStrategy.calculateRange(this, mode);
    }

    // Set the range calculation strategy
    public void setRangeCalculationStrategy(RangeCalculationStrategy strategy) {
        this.rangeCalculationStrategy = strategy;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public int getBuildYear() { return buildYear; }
    public void setBuildYear(int buildYear) { this.buildYear = buildYear; }
    
    public boolean isHasHeatPump() { return hasHeatPump; }
    public void setHasHeatPump(boolean hasHeatPump) { this.hasHeatPump = hasHeatPump; }

    // Getters and setters for new fields
    public double getBatteryCapacity() { return batteryCapacity; }
    public void setBatteryCapacity(double batteryCapacity) { this.batteryCapacity = batteryCapacity; }

    public EfficiencyMode getEfficiencyMode() { return efficiencyMode; }
    public void setEfficiencyMode(EfficiencyMode efficiencyMode) { this.efficiencyMode = efficiencyMode; }

    public double getWltpRange() { return wltpRange; }
    public void setWltpRange(double wltpRange) { this.wltpRange = wltpRange; }

    public double getMaxDcChargingPower() { return maxDcChargingPower; }
    public void setMaxDcChargingPower(double maxDcChargingPower) { this.maxDcChargingPower = maxDcChargingPower; }

    public double getMaxAcChargingPower() { return maxAcChargingPower; }
    public void setMaxAcChargingPower(double maxAcChargingPower) { this.maxAcChargingPower = maxAcChargingPower; }

    public ConsumptionProfile getConsumptionProfile() { return consumptionProfile; }
    public void setConsumptionProfile(ConsumptionProfile consumptionProfile) { this.consumptionProfile = consumptionProfile; }

    public BatteryProfile getBatteryProfile() { return batteryProfile; }
    public void setBatteryProfile(BatteryProfile batteryProfile) { this.batteryProfile = batteryProfile; }

    public ChargingProfile getChargingProfile() { return chargingProfile; }
    public void setChargingProfile(ChargingProfile chargingProfile) { this.chargingProfile = chargingProfile; }
} 