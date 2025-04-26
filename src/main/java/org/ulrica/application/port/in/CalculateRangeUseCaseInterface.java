package org.ulrica.application.port.in;

import org.ulrica.domain.valueobject.DrivingEnvironment;
import org.ulrica.domain.valueobject.EfficiencyMode;
import org.ulrica.domain.valueobject.TerrainType;
import org.ulrica.domain.valueobject.WeatherType;

public interface CalculateRangeUseCaseInterface {
    
    boolean calculateRange(
            TerrainType terrain,
            WeatherType weather,
            double temperatureCelsius,
            DrivingEnvironment environment,
            EfficiencyMode efficiencyMode,
            double stateOfChargePercent);
} 