package org.ulrica.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.BatteryProfile;
import org.ulrica.domain.valueobject.BatteryType;
import org.ulrica.domain.valueobject.ConsumptionProfile;
import org.ulrica.domain.valueobject.DrivingEnvironment;
import org.ulrica.domain.valueobject.EfficiencyMode;
import org.ulrica.domain.valueobject.RangeParameters;
import org.ulrica.domain.valueobject.RangeResult;
import org.ulrica.domain.valueobject.TerrainType;
import org.ulrica.domain.valueobject.WeatherType;

public class RangeCalculatorServiceTest {

    private RangeCalculatorService rangeCalculatorService;
    private CarProfile mockCarProfile;
    private RangeParameters parameters;

    @Before
    public void setUp() {
        rangeCalculatorService = new RangeCalculatorService();
        
        // Create battery profile
        BatteryProfile batteryProfile = new BatteryProfile(
            BatteryType.NMC,
            80.0,  // 80 kWh capacity
            5.0,   // 5% degradation
            250.0, // 250 kW max DC power
            11.0   // 11 kW max AC power
        );
        
        // Create consumption profile
        ConsumptionProfile consumptionProfile = new ConsumptionProfile(15.0, 20.0, 25.0);
        
        // Create car profile
        mockCarProfile = new CarProfile.Builder()
            .id("test-id")
            .name("Test EV")
            .manufacturer("Test Manufacturer")
            .model("Test Model")
            .year(2023)
            .hasHeatPump(true)
            .wltpRangeKm(500)
            .maxDcPowerKw(250.0)
            .maxAcPowerKw(11.0)
            .batteryProfile(batteryProfile)
            .consumptionProfile(consumptionProfile)
            .build();
        
        // Create range parameters
        parameters = new RangeParameters(
            TerrainType.FLAT,
            WeatherType.SUNNY,
            20.0, // temperature Celsius
            DrivingEnvironment.HIGHWAY,
            EfficiencyMode.NORMAL,
            80.0  // SOC percent
        );
    }

    @Test
    public void testCalculateRange_Default() {
        // Act
        RangeResult result = rangeCalculatorService.calculateRange(mockCarProfile, parameters);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getEstimatedRangeKm() > 0);
        assertTrue(result.getAverageConsumptionKwhPer100Km() > 0);
    }
    
    @Test
    public void testCalculateRange_WithStrategy() {
        // Arrange
        RangeCalculationStrategyInterface strategy = rangeCalculatorService.getDefaultStrategy();
        
        // Act
        RangeResult result = rangeCalculatorService.calculateRangeWithStrategy(
            mockCarProfile, parameters, strategy);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getEstimatedRangeKm() > 0);
        assertTrue(result.getAverageConsumptionKwhPer100Km() > 0);
    }
    
    @Test
    public void testAvailableStrategies() {
        // Assert that there are at least the default strategies (WLTP and Consumption-based)
        assertTrue(rangeCalculatorService.getAvailableStrategies().size() >= 2);
    }
    
    @Test
    public void testAddStrategy() {
        // Arrange
        int initialCount = rangeCalculatorService.getAvailableStrategies().size();
        
        // Create a mock strategy
        RangeCalculationStrategyInterface mockStrategy = new RangeCalculationStrategyInterface() {
            @Override
            public RangeResult calculateRange(CarProfile carProfile, RangeParameters params) {
                return new RangeResult(
                    300.0, // Estimated range in km
                    20.0,  // Average consumption in kWh/100km
                    "No weather impact",
                    "No terrain impact",
                    "No environment impact",
                    "Battery in good condition"
                );
            }
            
            @Override
            public String getName() {
                return "MockStrategy";
            }
            
            @Override
            public String getDescription() {
                return "A mock strategy for testing";
            }
        };
        
        // Act
        rangeCalculatorService.addStrategy(mockStrategy);
        
        // Assert
        assertEquals(initialCount + 1, rangeCalculatorService.getAvailableStrategies().size());
    }
    
    @Test
    public void testSetDefaultStrategy() {
        // Arrange
        RangeCalculationStrategyInterface initialDefault = rangeCalculatorService.getDefaultStrategy();
        
        // Create a mock strategy
        RangeCalculationStrategyInterface mockStrategy = new RangeCalculationStrategyInterface() {
            @Override
            public RangeResult calculateRange(CarProfile carProfile, RangeParameters params) {
                return new RangeResult(
                    300.0, // Estimated range in km
                    20.0,  // Average consumption in kWh/100km
                    "No weather impact",
                    "No terrain impact",
                    "No environment impact",
                    "Battery in good condition"
                );
            }
            
            @Override
            public String getName() {
                return "NewDefaultStrategy";
            }
            
            @Override
            public String getDescription() {
                return "A mock strategy for testing as default";
            }
        };
        
        // Act
        rangeCalculatorService.setDefaultStrategy(mockStrategy);
        
        // Assert
        assertEquals("NewDefaultStrategy", rangeCalculatorService.getDefaultStrategy().getName());
        assertTrue(rangeCalculatorService.getDefaultStrategy() != initialDefault);
    }
    
    @Test
    public void testCompareStrategies() {
        // Get available strategies
        RangeCalculationStrategyInterface wltpStrategy = null;
        RangeCalculationStrategyInterface consumptionStrategy = null;
        
        for (RangeCalculationStrategyInterface strategy : rangeCalculatorService.getAvailableStrategies()) {
            if (strategy instanceof WltpBasedRangeCalculationStrategy) {
                wltpStrategy = strategy;
            }
            if (strategy instanceof ConsumptionBasedRangeCalculationStrategy) {
                consumptionStrategy = strategy;
            }
        }
        
        // Make sure both strategies were found
        assertNotNull("WLTP strategy should be available", wltpStrategy);
        assertNotNull("Consumption strategy should be available", consumptionStrategy);
        
        // Act
        RangeResult wltpResult = rangeCalculatorService.calculateRangeWithStrategy(
            mockCarProfile, parameters, wltpStrategy);
        RangeResult consumptionResult = rangeCalculatorService.calculateRangeWithStrategy(
            mockCarProfile, parameters, consumptionStrategy);
        
        // Assert
        assertNotNull(wltpResult);
        assertNotNull(consumptionResult);
        
        // Verify that results have reasonable values
        assertTrue(wltpResult.getEstimatedRangeKm() > 0);
        assertTrue(consumptionResult.getEstimatedRangeKm() > 0);
    }
} 