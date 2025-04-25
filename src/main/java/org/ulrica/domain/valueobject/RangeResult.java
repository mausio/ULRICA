package org.ulrica.domain.valueobject;

import java.util.Objects;

public final class RangeResult {
    private final double estimatedRangeKm;
    private final double averageConsumptionKwhPer100Km;
    private final String weatherImpactDescription;
    private final String terrainImpactDescription;
    private final String environmentImpactDescription;
    private final String batteryConditionDescription;

    public RangeResult(
            double estimatedRangeKm,
            double averageConsumptionKwhPer100Km,
            String weatherImpactDescription,
            String terrainImpactDescription,
            String environmentImpactDescription,
            String batteryConditionDescription) {
        
        if (estimatedRangeKm < 0) {
            throw new IllegalArgumentException("Estimated range must be non-negative");
        }
        if (averageConsumptionKwhPer100Km <= 0) {
            throw new IllegalArgumentException("Average consumption must be positive");
        }
        
        this.estimatedRangeKm = estimatedRangeKm;
        this.averageConsumptionKwhPer100Km = averageConsumptionKwhPer100Km;
        this.weatherImpactDescription = Objects.requireNonNull(weatherImpactDescription, "Weather impact description cannot be null");
        this.terrainImpactDescription = Objects.requireNonNull(terrainImpactDescription, "Terrain impact description cannot be null");
        this.environmentImpactDescription = Objects.requireNonNull(environmentImpactDescription, "Environment impact description cannot be null");
        this.batteryConditionDescription = Objects.requireNonNull(batteryConditionDescription, "Battery condition description cannot be null");
    }

    public double getEstimatedRangeKm() {
        return estimatedRangeKm;
    }

    public double getAverageConsumptionKwhPer100Km() {
        return averageConsumptionKwhPer100Km;
    }

    public String getWeatherImpactDescription() {
        return weatherImpactDescription;
    }

    public String getTerrainImpactDescription() {
        return terrainImpactDescription;
    }

    public String getEnvironmentImpactDescription() {
        return environmentImpactDescription;
    }

    public String getBatteryConditionDescription() {
        return batteryConditionDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RangeResult that = (RangeResult) o;
        return Double.compare(that.estimatedRangeKm, estimatedRangeKm) == 0 &&
               Double.compare(that.averageConsumptionKwhPer100Km, averageConsumptionKwhPer100Km) == 0 &&
               Objects.equals(weatherImpactDescription, that.weatherImpactDescription) &&
               Objects.equals(terrainImpactDescription, that.terrainImpactDescription) &&
               Objects.equals(environmentImpactDescription, that.environmentImpactDescription) &&
               Objects.equals(batteryConditionDescription, that.batteryConditionDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estimatedRangeKm, averageConsumptionKwhPer100Km, weatherImpactDescription,
                terrainImpactDescription, environmentImpactDescription, batteryConditionDescription);
    }

    @Override
    public String toString() {
        return "RangeResult{" +
                "estimatedRangeKm=" + estimatedRangeKm +
                ", averageConsumptionKwhPer100Km=" + averageConsumptionKwhPer100Km +
                ", weatherImpactDescription='" + weatherImpactDescription + '\'' +
                ", terrainImpactDescription='" + terrainImpactDescription + '\'' +
                ", environmentImpactDescription='" + environmentImpactDescription + '\'' +
                ", batteryConditionDescription='" + batteryConditionDescription + '\'' +
                '}';
    }
} 