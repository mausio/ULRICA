package org.ulrica.presentation.controller;

import org.ulrica.application.port.in.CalculateRangeUseCaseInterface;
import org.ulrica.application.port.out.RangeCalculationOutputPortInterface;
import org.ulrica.application.port.out.UserInputPortInterface;
import org.ulrica.domain.valueobject.DrivingEnvironment;
import org.ulrica.domain.valueobject.EfficiencyMode;
import org.ulrica.domain.valueobject.TerrainType;
import org.ulrica.domain.valueobject.WeatherType;

/**
 * A mock implementation of RangeCalculationController for testing.
 * Allows tracking calls to processRangeCalculation and controlling its result.
 */
public class MockRangeCalculationController extends RangeCalculationController {
    
    private boolean processRangeCalculationResult = true;
    private int processCallCount = 0;
    
    /**
     * Create a mock with mock dependencies
     */
    public MockRangeCalculationController() {
        super(
            // Mock CalculateRangeUseCaseInterface
            new CalculateRangeUseCaseInterface() {
                @Override
                public boolean calculateRange(TerrainType terrain, WeatherType weather, double temperature, 
                                             DrivingEnvironment environment, EfficiencyMode efficiencyMode, double stateOfCharge) {
                    return true;
                }
            },
            // Mock UserInputPortInterface
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
            // Mock RangeCalculationOutputPortInterface
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
    
    /**
     * Override to control the result and track calls
     */
    @Override
    public boolean processRangeCalculation() {
        processCallCount++;
        return processRangeCalculationResult;
    }
    
    /**
     * Set the result that processRangeCalculation should return
     * @param result The result to return
     */
    public void setProcessRangeCalculationResult(boolean result) {
        this.processRangeCalculationResult = result;
    }
    
    /**
     * Get the number of times processRangeCalculation was called
     * @return The call count
     */
    public int getProcessCallCount() {
        return processCallCount;
    }
    
    /**
     * Reset the call counter
     */
    public void resetCallCount() {
        processCallCount = 0;
    }
} 