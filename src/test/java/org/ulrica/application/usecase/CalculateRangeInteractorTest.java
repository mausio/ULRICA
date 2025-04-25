package org.ulrica.application.usecase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.ulrica.application.port.out.RangeCalculationOutputPortInterface;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.service.ProfileSelectionService;
import org.ulrica.domain.service.RangeCalculatorService;
import org.ulrica.domain.valueobject.BatteryProfile;
import org.ulrica.domain.valueobject.BatteryType;
import org.ulrica.domain.valueobject.ConsumptionProfile;
import org.ulrica.domain.valueobject.DrivingEnvironment;
import org.ulrica.domain.valueobject.EfficiencyMode;
import org.ulrica.domain.valueobject.RangeParameters;
import org.ulrica.domain.valueobject.RangeResult;
import org.ulrica.domain.valueobject.TerrainType;
import org.ulrica.domain.valueobject.WeatherType;

public class CalculateRangeInteractorTest {
    
    private RangeCalculatorService rangeCalculatorService;
    private RangeCalculationOutputPortInterface outputPort;
    private ProfileSelectionService profileSelectionService;
    private CarProfile carProfile;
    
    private CalculateRangeInteractor interactor;
    
    @Before
    public void setUp() {
        rangeCalculatorService = new MockRangeCalculatorService();
        outputPort = new MockRangeCalculationOutputPort();
        profileSelectionService = new MockProfileSelectionService();
        
        // Create a mock car profile using builder pattern
        carProfile = new CarProfile.Builder()
                .id("mock-id")
                .name("Mock Car")
                .manufacturer("Mock Manufacturer")
                .model("Mock Model")
                .year(2023)
                .batteryProfile(new BatteryProfile(BatteryType.NMC, 75.0, 0.0, 150.0, 11.0))
                .consumptionProfile(new ConsumptionProfile(15.0, 18.0, 22.0))
                .build();
        
        ((MockProfileSelectionService) profileSelectionService).setSelectedProfile(carProfile);
        
        interactor = new CalculateRangeInteractor(rangeCalculatorService, outputPort, profileSelectionService);
    }
    
    @Test
    public void testCalculateRange_Success() {
        // Given test parameters
        TerrainType terrain = TerrainType.FLAT;
        WeatherType weather = WeatherType.SUNNY;
        double temperature = 20.0;
        DrivingEnvironment environment = DrivingEnvironment.HIGHWAY;
        EfficiencyMode efficiencyMode = EfficiencyMode.NORMAL;
        double stateOfCharge = 80.0;
        
        // When
        boolean result = interactor.calculateRange(
                terrain,
                weather,
                temperature,
                environment,
                efficiencyMode,
                stateOfCharge
        );
        
        // Then
        assertTrue(result);
        assertTrue(((MockRangeCalculationOutputPort) outputPort).calculationResultShown);
        assertTrue(((MockRangeCalculationOutputPort) outputPort).impactAnalysisShown);
    }
    
    @Test
    public void testCalculateRange_InvalidSoC() {
        // Given
        TerrainType terrain = TerrainType.FLAT;
        WeatherType weather = WeatherType.SUNNY;
        double temperature = 20.0;
        DrivingEnvironment environment = DrivingEnvironment.HIGHWAY;
        EfficiencyMode efficiencyMode = EfficiencyMode.NORMAL;
        double stateOfCharge = 101.0; // Invalid SoC
        
        // When
        boolean result = interactor.calculateRange(
                terrain,
                weather,
                temperature,
                environment,
                efficiencyMode,
                stateOfCharge
        );
        
        // Then
        assertFalse(result);
        assertTrue(((MockRangeCalculationOutputPort) outputPort).errorShown);
    }
    
    @Test
    public void testCalculateRange_NoProfileSelected() {
        // Given
        ((MockProfileSelectionService) profileSelectionService).setSelectedProfile(null);
        TerrainType terrain = TerrainType.FLAT;
        WeatherType weather = WeatherType.SUNNY;
        double temperature = 20.0;
        DrivingEnvironment environment = DrivingEnvironment.HIGHWAY;
        EfficiencyMode efficiencyMode = EfficiencyMode.NORMAL;
        double stateOfCharge = 80.0;
        
        // When
        boolean result = interactor.calculateRange(
                terrain,
                weather,
                temperature,
                environment,
                efficiencyMode,
                stateOfCharge
        );
        
        // Then
        assertFalse(result);
        assertTrue(((MockRangeCalculationOutputPort) outputPort).errorShown);
    }
    
    // Simple mock implementations
    private class MockRangeCalculatorService extends RangeCalculatorService {
        @Override
        public RangeResult calculateRange(CarProfile carProfile, RangeParameters parameters) {
            return new RangeResult(
                    250.0, 
                    16.5, 
                    "Weather impact", 
                    "Terrain impact", 
                    "Environment impact", 
                    "Battery condition"
            );
        }
    }
    
    private class MockRangeCalculationOutputPort implements RangeCalculationOutputPortInterface {
        boolean calculationResultShown = false;
        boolean impactAnalysisShown = false;
        boolean errorShown = false;
        
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
        public void showEfficiencyModeSelection() {}
        
        @Override
        public void showStateOfChargePrompt() {}
        
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
            calculationResultShown = true;
        }
        
        @Override
        public void showError(String message) {
            errorShown = true;
        }
        
        @Override
        public void showImpactAnalysis(
                String weatherImpact,
                String terrainImpact,
                String environmentImpact,
                String batteryCondition) {
            impactAnalysisShown = true;
        }
    }
    
    private class MockProfileSelectionService implements ProfileSelectionService {
        private CarProfile selectedProfile;
        
        public void setSelectedProfile(CarProfile profile) {
            this.selectedProfile = profile;
        }
        
        @Override
        public CarProfile getSelectedProfile() {
            return selectedProfile;
        }
        
        @Override
        public void selectProfile(CarProfile profile) {
            this.selectedProfile = profile;
        }
        
        @Override
        public void clearSelection() {
            this.selectedProfile = null;
        }
    }
} 