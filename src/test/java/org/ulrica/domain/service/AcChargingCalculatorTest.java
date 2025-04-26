package org.ulrica.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.service.AcChargingCalculator.AcChargingResult;
import org.ulrica.domain.valueobject.BatteryProfile;
import org.ulrica.domain.valueobject.BatteryType;

public class AcChargingCalculatorTest {

    private AcChargingCalculator calculator;
    private CarProfile mockCarProfile;
    private BatteryProfile batteryProfile;

    @Before
    public void setUp() {
        calculator = new AcChargingCalculator();
        
        // Create a battery profile for testing
        batteryProfile = new BatteryProfile(
            BatteryType.NMC,
            80.0,  // 80 kWh capacity
            5.0,   // 5% degradation
            250.0, // 250 kW max DC power
            11.0   // 11 kW max AC power
        );
        
        // Create a mock car profile with the battery profile
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
            .consumptionProfile(new org.ulrica.domain.valueobject.ConsumptionProfile(15, 20, 25))
            .build();
    }

    @Test
    public void testCalculateChargingTime_BasicScenario() {
        // Arrange
        double startingSoc = 20.0;
        double targetSoc = 80.0;
        int connectorType = AcChargingCalculator.WALLBOX;
        double ambientTemperature = 25.0;
        
        // Act
        AcChargingResult result = calculator.calculateChargingTime(
            mockCarProfile, 
            connectorType,
            startingSoc, 
            targetSoc, 
            ambientTemperature
        );
        
        // Assert
        // Energy to add should be 60% of the remaining capacity
        double expectedEnergyToAdd = batteryProfile.getRemainingCapacityKwh() * 0.6;
        assertEquals(expectedEnergyToAdd, result.getEnergyRequiredKwh(), 0.01);
        
        // Check that connector is correctly set
        assertEquals(connectorType, result.getConnectorType());
        
        // Check that the effective power is reasonable
        // For a wallbox with 95% efficiency at optimal temperature
        double expectedEfficiency = 0.95; // 1.0 - 0.05 efficiency loss for wallbox
        double expectedPower = Math.min(11.0, calculator.getConnectorPower(connectorType)) * expectedEfficiency;
        assertEquals(expectedPower, result.getEffectiveChargingPowerKw(), 0.1);
    }
    
    @Test
    public void testCalculateChargingTime_DifferentConnectors() {
        // Arrange
        double startingSoc = 20.0;
        double targetSoc = 80.0;
        double ambientTemperature = 25.0;
        
        // Act - Test different connector types
        AcChargingResult householdResult = calculator.calculateChargingTime(
            mockCarProfile, 
            AcChargingCalculator.HOUSEHOLD_SOCKET,
            startingSoc, 
            targetSoc, 
            ambientTemperature
        );
        
        AcChargingResult campingResult = calculator.calculateChargingTime(
            mockCarProfile, 
            AcChargingCalculator.CAMPING_SOCKET,
            startingSoc, 
            targetSoc, 
            ambientTemperature
        );
        
        AcChargingResult wallboxResult = calculator.calculateChargingTime(
            mockCarProfile, 
            AcChargingCalculator.WALLBOX,
            startingSoc, 
            targetSoc, 
            ambientTemperature
        );
        
        // Assert
        // Higher power connectors should charge faster
        assertTrue(householdResult.getChargingTimeHours() > campingResult.getChargingTimeHours());
        assertTrue(campingResult.getChargingTimeHours() > wallboxResult.getChargingTimeHours());
        
        // Check that connector powers match expectations
        assertEquals(calculator.getConnectorPower(AcChargingCalculator.HOUSEHOLD_SOCKET) * 0.9, 
                 householdResult.getEffectiveChargingPowerKw(), 0.1); // 10% loss
        assertEquals(calculator.getConnectorPower(AcChargingCalculator.CAMPING_SOCKET) * 0.93, 
                 campingResult.getEffectiveChargingPowerKw(), 0.1); // 7% loss
        assertEquals(calculator.getConnectorPower(AcChargingCalculator.WALLBOX) * 0.95, 
                 wallboxResult.getEffectiveChargingPowerKw(), 0.1); // 5% loss
    }
    
    @Test
    public void testCalculateChargingTime_LowTemperature() {
        // Arrange - test with low temperature to check efficiency reduction
        double startingSoc = 20.0;
        double targetSoc = 60.0;
        int connectorType = AcChargingCalculator.WALLBOX;
        double ambientTemperature = -10.0; // Very cold
        
        // Act
        AcChargingResult result = calculator.calculateChargingTime(
            mockCarProfile, 
            connectorType,
            startingSoc, 
            targetSoc, 
            ambientTemperature
        );
        
        // Assert
        // At low temperatures, efficiency should be reduced
        assertTrue(result.getTemperatureEfficiencyPercent() < 100.0);
        
        // Charging time should be longer at low temperatures due to reduced efficiency
        double energyToAdd = batteryProfile.getRemainingCapacityKwh() * 0.4; // 40% change
        double normalEfficiency = 0.95; // 95% efficiency for wallbox
        double normalChargeTime = energyToAdd / (calculator.getConnectorPower(connectorType) * normalEfficiency);
        assertTrue(result.getChargingTimeHours() > normalChargeTime);
    }
    
    @Test
    public void testValidateInputParameters_InvalidStartingSoc() {
        // Arrange
        double invalidStartingSoc = -10.0;
        double targetSoc = 80.0;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateChargingTime(
                mockCarProfile, 
                AcChargingCalculator.WALLBOX,
                invalidStartingSoc, 
                targetSoc, 
                25.0
            );
        });
    }
    
    @Test
    public void testValidateInputParameters_InvalidTargetSoc() {
        // Arrange
        double startingSoc = 20.0;
        double invalidTargetSoc = 110.0;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateChargingTime(
                mockCarProfile, 
                AcChargingCalculator.WALLBOX,
                startingSoc, 
                invalidTargetSoc, 
                25.0
            );
        });
    }
    
    @Test
    public void testValidateInputParameters_TargetLessThanStarting() {
        // Arrange
        double startingSoc = 80.0;
        double targetSoc = 70.0; // Less than starting
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateChargingTime(
                mockCarProfile, 
                AcChargingCalculator.WALLBOX,
                startingSoc, 
                targetSoc, 
                25.0
            );
        });
    }
    
    @Test
    public void testValidateInputParameters_InvalidConnectorType() {
        // Arrange
        double startingSoc = 20.0;
        double targetSoc = 80.0;
        int invalidConnector = 5; // Invalid connector type
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateChargingTime(
                mockCarProfile, 
                invalidConnector,
                startingSoc, 
                targetSoc, 
                25.0
            );
        });
    }
    
    @Test
    public void testGetConnectorPower() {
        // Test that connector powers match expected values
        assertEquals(AcChargingCalculator.HOUSEHOLD_SOCKET_POWER_KW, 
                 calculator.getConnectorPower(AcChargingCalculator.HOUSEHOLD_SOCKET), 0.01);
        assertEquals(AcChargingCalculator.CAMPING_SOCKET_POWER_KW, 
                 calculator.getConnectorPower(AcChargingCalculator.CAMPING_SOCKET), 0.01);
        assertEquals(AcChargingCalculator.WALLBOX_POWER_KW, 
                 calculator.getConnectorPower(AcChargingCalculator.WALLBOX), 0.01);
    }
    
    @Test
    public void testGetConnectorName() {
        // Test that connector names are not empty
        assertTrue(calculator.getConnectorName(AcChargingCalculator.HOUSEHOLD_SOCKET).length() > 0);
        assertTrue(calculator.getConnectorName(AcChargingCalculator.CAMPING_SOCKET).length() > 0);
        assertTrue(calculator.getConnectorName(AcChargingCalculator.WALLBOX).length() > 0);
    }
} 