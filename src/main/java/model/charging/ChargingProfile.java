package model.charging;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class ChargingProfile {
    private final String name;
    private final NavigableMap<Integer, Double> socToPowerMap;
    private final double batteryCapacity;
    private final double temperatureCoefficient;
    private final double degradationFactor;
    private final String batteryChemistry;
    private final double optimalTemperature;
    private final double maxTemperature;
    private final double minTemperature;

    public ChargingProfile(String name, double batteryCapacity, String batteryChemistry) {
        this.name = name;
        this.batteryCapacity = batteryCapacity;
        this.socToPowerMap = new TreeMap<>();
        this.batteryChemistry = batteryChemistry;
        
        switch (batteryChemistry.toUpperCase()) {
            case "LFP":
                this.optimalTemperature = 25.0;
                this.maxTemperature = 45.0;
                this.minTemperature = 0.0;
                this.temperatureCoefficient = 0.8;
                break;
            case "NMC":
                this.optimalTemperature = 20.0;
                this.maxTemperature = 40.0;
                this.minTemperature = -10.0;
                this.temperatureCoefficient = 1.0;
                break;
            default:
                this.optimalTemperature = 20.0;
                this.maxTemperature = 40.0;
                this.minTemperature = -10.0;
                this.temperatureCoefficient = 1.0;
        }
        this.degradationFactor = 1.0;
    }

    public void addChargingPoint(int soc, double power) {
        socToPowerMap.put(soc, power);
    }

    public double getPowerAtSoc(int soc, double temperature) {
        Map.Entry<Integer, Double> entry = socToPowerMap.floorEntry(soc);
        if (entry == null) {
            Map.Entry<Integer, Double> firstEntry = socToPowerMap.firstEntry();
            return firstEntry != null ? adjustPowerForConditions(firstEntry.getValue(), temperature) : 0.0;
        }
        return adjustPowerForConditions(entry.getValue(), temperature);
    }

    private double adjustPowerForConditions(double basePower, double temperature) {
        double tempFactor = calculateTemperatureFactor(temperature);
        return basePower * tempFactor * degradationFactor;
    }

    private double calculateTemperatureFactor(double temperature) {
        if (temperature < minTemperature || temperature > maxTemperature) {
            return 0.1;
        }
        
        if (temperature >= optimalTemperature - 5 && temperature <= optimalTemperature + 5) {
            return 1.0;
        }

        double factor;
        if (temperature < optimalTemperature) {
            factor = 0.5 + (0.5 * (temperature - minTemperature) / (optimalTemperature - minTemperature));
        } else {
            factor = 1.0 - (0.5 * (temperature - optimalTemperature) / (maxTemperature - optimalTemperature));
        }
        return Math.max(0.1, factor * temperatureCoefficient);
    }

    public String getName() {
        return name;
    }

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public String getBatteryChemistry() {
        return batteryChemistry;
    }

    public double getOptimalTemperature() {
        return optimalTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getTemperatureCoefficient() {
        return temperatureCoefficient;
    }

    public double getDegradationFactor() {
        return degradationFactor;
    }
} 