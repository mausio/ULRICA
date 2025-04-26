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
        
        
        batteryProfile = new BatteryProfile(
            BatteryType.NMC,
            80.0,  
            5.0,   
            250.0, 
            11.0   
        );
        
        
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
        
        double startingSoc = 20.0;
        double targetSoc = 80.0;
        int connectorType = AcChargingCalculator.WALLBOX;
        double ambientTemperature = 25.0;
        
        
        AcChargingResult result = calculator.calculateChargingTime(
            mockCarProfile, 
            connectorType,
            startingSoc, 
            targetSoc, 
            ambientTemperature
        );
        
        
        
        double expectedEnergyToAdd = batteryProfile.getRemainingCapacityKwh() * 0.6;
        assertEquals(expectedEnergyToAdd, result.getEnergyRequiredKwh(), 0.01);
        
        
        assertEquals(connectorType, result.getConnectorType());
        
        
        
        double expectedEfficiency = 0.95; 
        double expectedPower = Math.min(11.0, calculator.getConnectorPower(connectorType)) * expectedEfficiency;
        assertEquals(expectedPower, result.getEffectiveChargingPowerKw(), 0.1);
    }
    
    @Test
    public void testCalculateChargingTime_DifferentConnectors() {
        
        double startingSoc = 20.0;
        double targetSoc = 80.0;
        double ambientTemperature = 25.0;
        
        
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
        
        
        
        assertTrue(householdResult.getChargingTimeHours() > campingResult.getChargingTimeHours());
        assertTrue(campingResult.getChargingTimeHours() > wallboxResult.getChargingTimeHours());
        
        
        assertEquals(calculator.getConnectorPower(AcChargingCalculator.HOUSEHOLD_SOCKET) * 0.9, 
                 householdResult.getEffectiveChargingPowerKw(), 0.1); 
        assertEquals(calculator.getConnectorPower(AcChargingCalculator.CAMPING_SOCKET) * 0.93, 
                 campingResult.getEffectiveChargingPowerKw(), 0.1); 
        assertEquals(calculator.getConnectorPower(AcChargingCalculator.WALLBOX) * 0.95, 
                 wallboxResult.getEffectiveChargingPowerKw(), 0.1); 
    }
    
    @Test
    public void testCalculateChargingTime_LowTemperature() {
        
        double startingSoc = 20.0;
        double targetSoc = 60.0;
        int connectorType = AcChargingCalculator.WALLBOX;
        double ambientTemperature = -10.0; 
        
        
        AcChargingResult result = calculator.calculateChargingTime(
            mockCarProfile, 
            connectorType,
            startingSoc, 
            targetSoc, 
            ambientTemperature
        );
        
        
        
        assertTrue(result.getTemperatureEfficiencyPercent() < 100.0);
        
        
        double energyToAdd = batteryProfile.getRemainingCapacityKwh() * 0.4; 
        double normalEfficiency = 0.95; 
        double normalChargeTime = energyToAdd / (calculator.getConnectorPower(connectorType) * normalEfficiency);
        assertTrue(result.getChargingTimeHours() > normalChargeTime);
    }
    
    @Test
    public void testValidateInputParameters_InvalidStartingSoc() {
        
        double invalidStartingSoc = -10.0;
        double targetSoc = 80.0;
        
        
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
        
        double startingSoc = 20.0;
        double invalidTargetSoc = 110.0;
        
        
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
        
        double startingSoc = 80.0;
        double targetSoc = 70.0; 
        
        
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
        
        double startingSoc = 20.0;
        double targetSoc = 80.0;
        int invalidConnector = 5; 
        
        
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
        
        assertEquals(AcChargingCalculator.HOUSEHOLD_SOCKET_POWER_KW, 
                 calculator.getConnectorPower(AcChargingCalculator.HOUSEHOLD_SOCKET), 0.01);
        assertEquals(AcChargingCalculator.CAMPING_SOCKET_POWER_KW, 
                 calculator.getConnectorPower(AcChargingCalculator.CAMPING_SOCKET), 0.01);
        assertEquals(AcChargingCalculator.WALLBOX_POWER_KW, 
                 calculator.getConnectorPower(AcChargingCalculator.WALLBOX), 0.01);
    }
    
    @Test
    public void testGetConnectorName() {
        
        assertTrue(calculator.getConnectorName(AcChargingCalculator.HOUSEHOLD_SOCKET).length() > 0);
        assertTrue(calculator.getConnectorName(AcChargingCalculator.CAMPING_SOCKET).length() > 0);
        assertTrue(calculator.getConnectorName(AcChargingCalculator.WALLBOX).length() > 0);
    }
} 