package org.ulrica.presentation.view;

import java.text.DecimalFormat;

import org.ulrica.application.port.out.RangeCalculationOutputPortInterface;
import org.ulrica.application.port.out.UserOutputPortInterface;
import org.ulrica.domain.valueobject.DrivingEnvironment;
import org.ulrica.domain.valueobject.EfficiencyMode;
import org.ulrica.domain.valueobject.TerrainType;
import org.ulrica.domain.valueobject.WeatherType;

public class RangeCalculationView implements RangeCalculationOutputPortInterface {
    
    private final UserOutputPortInterface userOutputPort;
    private final DecimalFormat decimalFormat;
    
    public RangeCalculationView(UserOutputPortInterface userOutputPort) {
        this.userOutputPort = userOutputPort;
        this.decimalFormat = new DecimalFormat("#0.0");
    }
    
    @Override
    public void showRangeCalculationHeader() {
        userOutputPort.displayLine("\n=== Range Calculator ===");
    }
    
    @Override
    public void showTerrainSelection() {
        userOutputPort.displayLine("Select Terrain Condition:");
        userOutputPort.displayLine("1. Flat");
        userOutputPort.displayLine("2. Hilly");
        userOutputPort.displayLine("3. Mountainous");
        userOutputPort.displayLine("");
        userOutputPort.display("Enter choice (1-3): ");
    }
    
    @Override
    public void showWeatherSelection() {
        userOutputPort.displayLine("\nSelect Weather Condition:");
        userOutputPort.displayLine("1. Sunny");
        userOutputPort.displayLine("2. Cloudy");
        userOutputPort.displayLine("3. Rain");
        userOutputPort.displayLine("4. Snow");
        userOutputPort.displayLine("5. Strong Wind");
        userOutputPort.displayLine("");
        userOutputPort.display("Enter choice (1-5): ");
    }
    
    @Override
    public void showTemperaturePrompt() {
        userOutputPort.display("\nEnter Ambient Temperature (°C): ");
    }
    
    @Override
    public void showDrivingEnvironmentSelection() {
        userOutputPort.displayLine("\nSelect Driving Environment:");
        userOutputPort.displayLine("1. City");
        userOutputPort.displayLine("2. Rural");
        userOutputPort.displayLine("3. Highway");
        userOutputPort.displayLine("");
        userOutputPort.display("Enter choice (1-3): ");
    }
    
    @Override
    public void showEfficiencyModeSelection() {
        userOutputPort.displayLine("\nSelect Efficiency Mode:");
        userOutputPort.displayLine("1. ECO");
        userOutputPort.displayLine("2. NORMAL");
        userOutputPort.displayLine("3. SPORT");
        userOutputPort.displayLine("");
        userOutputPort.display("Enter choice (1-3): ");
    }
    
    @Override
    public void showStateOfChargePrompt() {
        userOutputPort.display("Enter current state of charge (0-100%): ");
    }
    
    @Override
    public void showCalculationResult(
            double estimatedRangeKm,
            double averageConsumptionKwhPer100Km,
            TerrainType terrain,
            WeatherType weather,
            double temperatureCelsius,
            DrivingEnvironment environment,
            double stateOfChargePercent,
            double batteryTemperatureCelsius,
            EfficiencyMode efficiencyMode) {
        
        userOutputPort.displayLine("\n=== Range Calculation Results ===");
        userOutputPort.displayLine("Estimated range: " + decimalFormat.format(estimatedRangeKm) + " km");
        userOutputPort.displayLine("Average consumption: " + decimalFormat.format(averageConsumptionKwhPer100Km) + " kWh/100km");
        userOutputPort.displayLine("");
        userOutputPort.displayLine("Input:");
        userOutputPort.displayLine("- Terrain: " + terrain);
        userOutputPort.displayLine("- Weather: " + weather);
        userOutputPort.displayLine("- Temperature: " + decimalFormat.format(temperatureCelsius) + "°C");
        userOutputPort.displayLine("- Environment: " + environment);
        userOutputPort.displayLine("- Average Speed: " + decimalFormat.format(environment.getAvgSpeedKmh()) + " km/h");
        userOutputPort.displayLine("- Battery SoC: " + decimalFormat.format(stateOfChargePercent) + "%");
        userOutputPort.displayLine("- Battery Temperature: " + decimalFormat.format(batteryTemperatureCelsius) + "°C");
        userOutputPort.displayLine("- Efficiency Mode: " + efficiencyMode);
    }
    
    @Override
    public void showError(String message) {
        userOutputPort.displayLine("\nError: " + message);
    }
    
    @Override
    public void showImpactAnalysis(
            String weatherImpact,
            String terrainImpact,
            String environmentImpact,
            String batteryCondition) {
        
        userOutputPort.displayLine("\nImpact due to Conditions:");
        userOutputPort.displayLine("- Weather impact: " + weatherImpact);
        userOutputPort.displayLine("- Terrain impact: " + terrainImpact);
        userOutputPort.displayLine("- Environment impact: " + environmentImpact);
        userOutputPort.displayLine("- Battery condition: " + batteryCondition);
    }
} 