package org.ulrica.domain.valueobject;

import java.util.Objects;

public final class RangeParameters {
    private final TerrainType terrain;
    private final WeatherType weather;
    private final double temperatureCelsius;
    private final DrivingEnvironment environment;
    private final EfficiencyMode efficiencyMode;
    private final double stateOfChargePercent;

    public RangeParameters(
            TerrainType terrain,
            WeatherType weather,
            double temperatureCelsius,
            DrivingEnvironment environment,
            EfficiencyMode efficiencyMode,
            double stateOfChargePercent) {
        
        if (stateOfChargePercent < 0 || stateOfChargePercent > 100) {
            throw new IllegalArgumentException("State of charge must be between 0 and 100 percent");
        }
        
        this.terrain = Objects.requireNonNull(terrain, "Terrain type cannot be null");
        this.weather = Objects.requireNonNull(weather, "Weather type cannot be null");
        this.temperatureCelsius = temperatureCelsius;
        this.environment = Objects.requireNonNull(environment, "Driving environment cannot be null");
        this.efficiencyMode = Objects.requireNonNull(efficiencyMode, "Efficiency mode cannot be null");
        this.stateOfChargePercent = stateOfChargePercent;
    }

    public TerrainType getTerrain() {
        return terrain;
    }

    public WeatherType getWeather() {
        return weather;
    }

    public double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public DrivingEnvironment getEnvironment() {
        return environment;
    }

    public EfficiencyMode getEfficiencyMode() {
        return efficiencyMode;
    }

    public double getStateOfChargePercent() {
        return stateOfChargePercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RangeParameters that = (RangeParameters) o;
        return Double.compare(that.temperatureCelsius, temperatureCelsius) == 0 &&
               Double.compare(that.stateOfChargePercent, stateOfChargePercent) == 0 &&
               terrain == that.terrain &&
               weather == that.weather &&
               environment == that.environment &&
               efficiencyMode == that.efficiencyMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(terrain, weather, temperatureCelsius, environment, efficiencyMode, stateOfChargePercent);
    }

    @Override
    public String toString() {
        return "RangeParameters{" +
                "terrain=" + terrain +
                ", weather=" + weather +
                ", temperatureCelsius=" + temperatureCelsius +
                ", environment=" + environment +
                ", efficiencyMode=" + efficiencyMode +
                ", stateOfChargePercent=" + stateOfChargePercent +
                '}';
    }
} 