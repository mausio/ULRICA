package org.ulrica.application.usecase;

import java.util.Objects;

import org.ulrica.application.port.in.CalculateRangeUseCaseInterface;
import org.ulrica.application.port.out.RangeCalculationOutputPortInterface;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.service.ProfileSelectionService;
import org.ulrica.domain.service.RangeCalculatorService;
import org.ulrica.domain.valueobject.DrivingEnvironment;
import org.ulrica.domain.valueobject.EfficiencyMode;
import org.ulrica.domain.valueobject.RangeParameters;
import org.ulrica.domain.valueobject.RangeResult;
import org.ulrica.domain.valueobject.TerrainType;
import org.ulrica.domain.valueobject.WeatherType;

public class CalculateRangeInteractor implements CalculateRangeUseCaseInterface {
    
    private final RangeCalculatorService rangeCalculatorService;
    private final RangeCalculationOutputPortInterface outputPort;
    private final ProfileSelectionService profileSelectionService;
    
    public CalculateRangeInteractor(
            RangeCalculatorService rangeCalculatorService,
            RangeCalculationOutputPortInterface outputPort,
            ProfileSelectionService profileSelectionService) {
        this.rangeCalculatorService = Objects.requireNonNull(rangeCalculatorService, "Range calculator service cannot be null");
        this.outputPort = Objects.requireNonNull(outputPort, "Output port cannot be null");
        this.profileSelectionService = Objects.requireNonNull(profileSelectionService, "Profile selection service cannot be null");
    }
    
    @Override
    public boolean calculateRange(
            TerrainType terrain,
            WeatherType weather,
            double temperatureCelsius,
            DrivingEnvironment environment,
            EfficiencyMode efficiencyMode,
            double stateOfChargePercent) {
        
        try {
            CarProfile carProfile = profileSelectionService.getSelectedProfile();
            if (carProfile == null) {
                outputPort.showError("No car profile selected");
                return false;
            }
            
            if (stateOfChargePercent < 0 || stateOfChargePercent > 100) {
                outputPort.showError("State of charge must be between 0 and 100 percent");
                return false;
            }
            
            RangeParameters parameters = new RangeParameters(
                    terrain,
                    weather,
                    temperatureCelsius,
                    environment,
                    efficiencyMode,
                    stateOfChargePercent
            );
            
            RangeResult result = rangeCalculatorService.calculateRange(carProfile, parameters);
            
            outputPort.showCalculationResult(
                    result.getEstimatedRangeKm(),
                    result.getAverageConsumptionKwhPer100Km(),
                    terrain,
                    weather,
                    temperatureCelsius,
                    environment,
                    stateOfChargePercent,
                    temperatureCelsius,
                    efficiencyMode
            );
            
            outputPort.showImpactAnalysis(
                    result.getWeatherImpactDescription(),
                    result.getTerrainImpactDescription(),
                    result.getEnvironmentImpactDescription(),
                    result.getBatteryConditionDescription()
            );
            
            return true;
        } catch (Exception e) {
            outputPort.showError("Range calculation failed: " + e.getMessage());
            return false;
        }
    }
} 