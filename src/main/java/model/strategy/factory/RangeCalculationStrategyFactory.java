package model.strategy.factory;

import model.strategy.EnhancedRangeCalculationStrategy;
import model.strategy.RangeCalculationStrategy;
import model.strategy.SimpleFactorRangeStrategy;
import model.strategy.efficiency.DrivingEnvironmentFactor.DrivingEnvironment;
import model.strategy.efficiency.TerrainEfficiencyFactor.TerrainType;
import model.strategy.efficiency.WeatherEfficiencyFactor.WeatherCondition;
import model.strategy.efficiency.impl.DefaultBatteryStateEfficiencyFactor;
import model.strategy.efficiency.impl.DefaultDrivingEnvironmentFactor;
import model.strategy.efficiency.impl.DefaultTerrainEfficiencyFactor;
import model.strategy.efficiency.impl.DefaultWeatherEfficiencyFactor;

public class RangeCalculationStrategyFactory {
    public static RangeCalculationStrategy createSimpleStrategy() {
        return new SimpleFactorRangeStrategy();
    }

    public static EnhancedRangeCalculationStrategy createEnhancedStrategy(
            TerrainType terrain,
            WeatherCondition weather,
            double temperature,
            DrivingEnvironment environment,
            double averageSpeed,
            double stateOfCharge,
            double batteryTemperature) {
        
        EnhancedRangeCalculationStrategy strategy = new EnhancedRangeCalculationStrategy();
        
        strategy.addEfficiencyFactor(new DefaultTerrainEfficiencyFactor(terrain));
        strategy.addEfficiencyFactor(new DefaultWeatherEfficiencyFactor(weather, temperature));
        strategy.addEfficiencyFactor(new DefaultDrivingEnvironmentFactor(environment, averageSpeed));
        strategy.addEfficiencyFactor(new DefaultBatteryStateEfficiencyFactor(stateOfCharge, batteryTemperature));
        
        return strategy;
    }
} 