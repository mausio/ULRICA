package org.ulrica.presentation.controller;

import org.ulrica.application.port.in.CalculateRangeUseCaseInterface;
import org.ulrica.application.port.out.RangeCalculationOutputPortInterface;
import org.ulrica.application.port.out.UserInputPortInterface;
import org.ulrica.domain.valueobject.DrivingEnvironment;
import org.ulrica.domain.valueobject.EfficiencyMode;
import org.ulrica.domain.valueobject.TerrainType;
import org.ulrica.domain.valueobject.WeatherType;


public class MockRangeCalculationController extends RangeCalculationController {
    
    private boolean processRangeCalculationResult = true;
    private int processCallCount = 0;
    

    public MockRangeCalculationController() {
        super(
            new CalculateRangeUseCaseInterface() {
                @Override
                public boolean calculateRange(TerrainType terrain, WeatherType weather, double temperature, 
                                             DrivingEnvironment environment, EfficiencyMode efficiencyMode, double stateOfCharge) {
                    return true;
                }
            },
            new UserInputPortInterface() {
                @Override
                public String readLine() { return ""; }
                @Override
                public int readInt() { return 0; }
                @Override
                public double readDouble() { return 0.0; }
                @Override
                public boolean readBoolean(String yesOption, String noOption) { return false; }
            },
            new RangeCalculationOutputPortInterface() {
                @Override
                public void showRangeCalculationHeader() {}
                @Override
                public void showTerrainSelection() {}
                @Override
                public void showWeatherSelection() {}
                @Override
                public void showTemperaturePrompt() {}
                @Override
                public void showDrivingEnvironmentSelection() {}
                @Override
                public void showStateOfChargePrompt() {}
                @Override
                public void showEfficiencyModeSelection() {}
                @Override
                public void showCalculationResult(double estimatedRangeKm, double averageConsumptionKwhPer100Km,
                                                 TerrainType terrain, WeatherType weather, double temperatureCelsius,
                                                 DrivingEnvironment environment, double stateOfChargePercent,
                                                 double batteryTemperatureCelsius, EfficiencyMode efficiencyMode) {}
                @Override
                public void showImpactAnalysis(String weatherImpact, String terrainImpact, 
                                              String environmentImpact, String batteryCondition) {}
                @Override
                public void showError(String message) {}
            }
        );
    }
    

    @Override
    public boolean processRangeCalculation() {
        processCallCount++;
        return processRangeCalculationResult;
    }
    
    public void setProcessRangeCalculationResult(boolean result) {
        this.processRangeCalculationResult = result;
    }
    
    public int getProcessCallCount() {
        return processCallCount;
    }
    
    public void resetCallCount() {
        processCallCount = 0;
    }
} 