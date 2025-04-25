package org.ulrica.application.port.out;

import org.ulrica.domain.valueobject.DrivingEnvironment;
import org.ulrica.domain.valueobject.EfficiencyMode;
import org.ulrica.domain.valueobject.TerrainType;
import org.ulrica.domain.valueobject.WeatherType;

public interface RangeCalculationOutputPortInterface {
    
    void showRangeCalculationHeader();
    
    void showTerrainSelection();
    
    void showWeatherSelection();
    
    void showTemperaturePrompt();
    
    void showDrivingEnvironmentSelection();
    
    void showEfficiencyModeSelection();
    
    void showStateOfChargePrompt();
    
    void showCalculationResult(
            double estimatedRangeKm,
            double averageConsumptionKwhPer100Km,
            TerrainType terrain,
            WeatherType weather,
            double temperatureCelsius,
            DrivingEnvironment environment,
            double stateOfChargePercent,
            double batteryTemperatureCelsius,
            EfficiencyMode efficiencyMode);
    
    void showError(String message);
    
    void showImpactAnalysis(
            String weatherImpact,
            String terrainImpact,
            String environmentImpact,
            String batteryCondition);
} 