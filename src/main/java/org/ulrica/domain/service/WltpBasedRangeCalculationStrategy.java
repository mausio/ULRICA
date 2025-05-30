package org.ulrica.domain.service;

import java.util.Objects;

import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.DrivingEnvironment;
import org.ulrica.domain.valueobject.RangeParameters;
import org.ulrica.domain.valueobject.RangeResult;
import org.ulrica.domain.valueobject.TerrainType;
import org.ulrica.domain.valueobject.WeatherType;

public class WltpBasedRangeCalculationStrategy implements RangeCalculationStrategyInterface {

    @Override
    public RangeResult calculateRange(CarProfile carProfile, RangeParameters parameters) {
        Objects.requireNonNull(carProfile, "Car profile cannot be null!!!");
        Objects.requireNonNull(parameters, "Range parameters cannot be null");
        
        double baseWltpRange = carProfile.getWltpRangeKm();
        double batteryCapacity = carProfile.getBatteryProfile().getRemainingCapacityKwh();
        double currentSoC = parameters.getStateOfChargePercent();
        
        double baseConsumption = (batteryCapacity * 100.0) / baseWltpRange;
        
        double modeConsumption = baseConsumption * parameters.getEfficiencyMode().getConsumptionFactor();
        
        double terrainFactor = calculateTerrainFactor(parameters.getTerrain());
        double terrainConsumption = modeConsumption * terrainFactor;
        
        double weatherFactor = calculateWeatherFactor(parameters.getWeather(), parameters.getTemperatureCelsius());
        double weatherConsumption = terrainConsumption * weatherFactor;
        
        double environmentFactor = calculateEnvironmentFactor(parameters.getEnvironment());
        double finalConsumption = weatherConsumption * environmentFactor;
        
        double availableEnergy = batteryCapacity * (currentSoC / 100.0);
        double estimatedRange = (availableEnergy * 100.0) / finalConsumption;
        
        String weatherImpact = generateWeatherImpactDescription(parameters.getWeather(), parameters.getTemperatureCelsius());
        String terrainImpact = generateTerrainImpactDescription(parameters.getTerrain());
        String environmentImpact = generateEnvironmentImpactDescription(parameters.getEnvironment());
        String batteryCondition = generateBatteryConditionDescription(parameters.getStateOfChargePercent(), parameters.getTemperatureCelsius());
        
        return new RangeResult(
                estimatedRange,
                finalConsumption,
                weatherImpact,
                terrainImpact,
                environmentImpact,
                batteryCondition
        );
    }
    
    private double calculateTerrainFactor(TerrainType terrain) {
        return switch(terrain) {
            case FLAT -> 1.0;
            case HILLY -> 1.15;
            case MOUNTAINOUS -> 1.35;
        };
    }
    
    private double calculateWeatherFactor(WeatherType weather, double temperature) {
        double weatherMultiplier = switch(weather) {
            case SUNNY -> 0.9;
            case CLOUDY -> 0.95;
            case RAIN -> 1.1;
            case SNOW -> 1.3;
            case STRONG_WIND -> 1.2;
        };
        
        double tempFactor;
        if (temperature < -10) {
            tempFactor = 1.4;
        } else if (temperature < 0) {
            tempFactor = 1.2;
        } else if (temperature < 10) {
            tempFactor = 1.0;
        } else if (temperature <= 25) {
            tempFactor = 0.9;
        } else if (temperature <= 35) {
            tempFactor = 0.95;
        } else {
            tempFactor = 1.1;
        }
        
        return weatherMultiplier * tempFactor;
    }
    
    private double calculateEnvironmentFactor(DrivingEnvironment environment) {
        return switch(environment) {
            case CITY -> 0.8;     
            case RURAL -> 1.0;    
            case HIGHWAY -> 1.1; 
        };
    }
    
    private String generateWeatherImpactDescription(WeatherType weather, double temperature) {
        StringBuilder impact = new StringBuilder();
        
        switch(weather) {
            case SUNNY:
                impact.append("Minimal impact - Ideal weather conditions");
                break;
            case CLOUDY:
                impact.append("Slight impact - Cloud cover has minimal effect on efficiency");
                break;
            case RAIN:
                impact.append("Moderate impact - Rain increases rolling resistance");
                break;
            case SNOW:
                impact.append("Severe impact - Snow conditions significantly reduce range");
                break;
            case STRONG_WIND:
                impact.append("Significant impact - Strong winds increase air resistance");
                break;
        }
        
        if (temperature < 0) {
            impact.append(", Cold temperature reduces battery efficiency");
        } else if (temperature > 30) {
            impact.append(", High temperature requires additional cooling");
        }
        
        return impact.toString();
    }
    
    private String generateTerrainImpactDescription(TerrainType terrain) {
        return switch(terrain) {
            case FLAT -> "Minimal impact - Flat terrain optimal for efficiency";
            case HILLY -> "Moderate impact - Hills affect energy consumption";
            case MOUNTAINOUS -> "Significant impact - Mountainous terrain substantially increases consumption";
        };
    }
    
    private String generateEnvironmentImpactDescription(DrivingEnvironment environment) {
        return switch(environment) {
            case CITY -> "Stop-and-go traffic at " + environment.getAvgSpeedKmh() + " km/h - Benefits from regenerative braking";
            case RURAL -> "Medium speed driving at " + environment.getAvgSpeedKmh() + " km/h - Moderate air resistance";
            case HIGHWAY -> "Constant high speed at " + environment.getAvgSpeedKmh() + " km/h - Increased air resistance";
        };
    }
    
    private String generateBatteryConditionDescription(double soc, double temperature) {
        StringBuilder condition = new StringBuilder();
        
        if (soc > 80) {
            condition.append("High SoC (").append(soc).append("%) - Optimal operating range");
        } else if (soc > 40) {
            condition.append("Medium SoC (").append(soc).append("%) - Good operating range");
        } else if (soc > 20) {
            condition.append("Low SoC (").append(soc).append("%) - Consider charging soon");
        } else {
            condition.append("Very low SoC (").append(soc).append("%) - Critical level, charge immediately");
        }
        
        condition.append(", ");
        
        if (temperature < -10) {
            condition.append("Battery temperature very cold (").append(temperature).append("°C) - Severely reduced efficiency");
        } else if (temperature < 0) {
            condition.append("Battery temperature cold (").append(temperature).append("°C) - Reduced efficiency");
        } else if (temperature < 10) {
            condition.append("Battery temperature cool (").append(temperature).append("°C) - Slightly reduced efficiency");
        } else if (temperature <= 30) {
            condition.append("Battery temperature optimal (").append(temperature).append("°C) - Maximum efficiency");
        } else if (temperature <= 40) {
            condition.append("Battery temperature warm (").append(temperature).append("°C) - Slightly reduced efficiency");
        } else {
            condition.append("Battery temperature hot (").append(temperature).append("°C) - Reduced efficiency, potential for degradation");
        }
        
        return condition.toString();
    }

    @Override
    public String getName() {
        return "WLTP-based Range Calculation";
    }

    @Override
    public String getDescription() {
        return "Calculates range based on the car's WLTP rating adjusted for external conditions";
    }
} 