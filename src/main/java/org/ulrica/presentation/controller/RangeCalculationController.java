package org.ulrica.presentation.controller;

import java.util.Objects;

import org.ulrica.application.port.in.CalculateRangeUseCaseInterface;
import org.ulrica.application.port.out.RangeCalculationOutputPortInterface;
import org.ulrica.application.port.out.UserInputPortInterface;
import org.ulrica.domain.valueobject.DrivingEnvironment;
import org.ulrica.domain.valueobject.EfficiencyMode;
import org.ulrica.domain.valueobject.TerrainType;
import org.ulrica.domain.valueobject.WeatherType;

public class RangeCalculationController {
    
    private final CalculateRangeUseCaseInterface calculateRangeUseCase;
    private final UserInputPortInterface userInputPort;
    private final RangeCalculationOutputPortInterface outputPort;
    
    public RangeCalculationController(
            CalculateRangeUseCaseInterface calculateRangeUseCase,
            UserInputPortInterface userInputPort,
            RangeCalculationOutputPortInterface outputPort) {
        this.calculateRangeUseCase = Objects.requireNonNull(calculateRangeUseCase, "Calculate range use case cannot be null");
        this.userInputPort = Objects.requireNonNull(userInputPort, "User input port cannot be null");
        this.outputPort = Objects.requireNonNull(outputPort, "Output port cannot be null");
    }
    
    public boolean processRangeCalculation() {
        try {
            outputPort.showRangeCalculationHeader();
            
            
            TerrainType terrain = getTerrainType();
            
            
            WeatherType weather = getWeatherType();
            
            
            double temperature = getTemperature();
            
            
            DrivingEnvironment environment = getDrivingEnvironment();
            
            
            double stateOfCharge = getStateOfCharge();
            
            
            EfficiencyMode efficiencyMode = getEfficiencyMode();
            
            
            return calculateRangeUseCase.calculateRange(
                    terrain,
                    weather,
                    temperature,
                    environment,
                    efficiencyMode,
                    stateOfCharge
            );
        } catch (Exception e) {
            outputPort.showError("An error occurred: " + e.getMessage());
            return false;
        }
    }
    
    private TerrainType getTerrainType() {
        outputPort.showTerrainSelection();
        int choice = userInputPort.readInt();
        
        return switch (choice) {
            case 1 -> TerrainType.FLAT;
            case 2 -> TerrainType.HILLY;
            case 3 -> TerrainType.MOUNTAINOUS;
            default -> throw new IllegalArgumentException("Invalid terrain choice: " + choice);
        };
    }
    
    private WeatherType getWeatherType() {
        outputPort.showWeatherSelection();
        int choice = userInputPort.readInt();
        
        return switch (choice) {
            case 1 -> WeatherType.SUNNY;
            case 2 -> WeatherType.CLOUDY;
            case 3 -> WeatherType.RAIN;
            case 4 -> WeatherType.SNOW;
            case 5 -> WeatherType.STRONG_WIND;
            default -> throw new IllegalArgumentException("Invalid weather choice: " + choice);
        };
    }
    
    private double getTemperature() {
        outputPort.showTemperaturePrompt();
        return userInputPort.readDouble();
    }
    
    private DrivingEnvironment getDrivingEnvironment() {
        outputPort.showDrivingEnvironmentSelection();
        int choice = userInputPort.readInt();
        
        return switch (choice) {
            case 1 -> DrivingEnvironment.CITY;
            case 2 -> DrivingEnvironment.RURAL;
            case 3 -> DrivingEnvironment.HIGHWAY;
            default -> throw new IllegalArgumentException("Invalid environment choice: " + choice);
        };
    }
    
    private double getStateOfCharge() {
        outputPort.showStateOfChargePrompt();
        double soc = userInputPort.readDouble();
        
        if (soc < 0 || soc > 100) {
            throw new IllegalArgumentException("State of charge must be between 0 and 100");
        }
        
        return soc;
    }
    
    private EfficiencyMode getEfficiencyMode() {
        outputPort.showEfficiencyModeSelection();
        int choice = userInputPort.readInt();
        
        return switch (choice) {
            case 1 -> EfficiencyMode.ECO;
            case 2 -> EfficiencyMode.NORMAL;
            case 3 -> EfficiencyMode.SPORT;
            default -> throw new IllegalArgumentException("Invalid efficiency mode choice: " + choice);
        };
    }
} 