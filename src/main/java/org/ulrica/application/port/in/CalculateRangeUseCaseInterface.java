package org.ulrica.application.port.in;

import org.ulrica.domain.valueobject.DrivingEnvironment;
import org.ulrica.domain.valueobject.EfficiencyMode;
import org.ulrica.domain.valueobject.TerrainType;
import org.ulrica.domain.valueobject.WeatherType;

public interface CalculateRangeUseCaseInterface {
    
    /**
     * Calculates the range based on the provided parameters
     * 
     * @param terrain              the terrain type
     * @param weather              the weather condition
     * @param temperatureCelsius   the ambient temperature in Celsius
     * @param environment          the driving environment
     * @param efficiencyMode       the efficiency mode
     * @param stateOfChargePercent the current state of charge percentage
     * @return true if calculation was successful, false otherwise
     */
    boolean calculateRange(
            TerrainType terrain,
            WeatherType weather,
            double temperatureCelsius,
            DrivingEnvironment environment,
            EfficiencyMode efficiencyMode,
            double stateOfChargePercent);
} 