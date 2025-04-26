package org.ulrica.domain.service;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.service.DcChargingCalculator.DcChargingResult;
import org.ulrica.domain.valueobject.BatteryProfile;
import org.ulrica.domain.valueobject.BatteryType;
import org.ulrica.domain.valueobject.ChargingCurve;

public class DcChargingCalculatorTest {

    private DcChargingCalculator calculator;
    private CarProfile mockCarProfile;
    private BatteryProfile batteryProfile;

    @Before
    public void setUp() {
        calculator = new DcChargingCalculator();
        
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
        double maxStationPower = 250.0;
        double ambientTemperature = 25.0;
        
        // Act
        DcChargingResult result = calculator.calculateChargingTime(
            mockCarProfile, 
            startingSoc, 
            targetSoc, 
            maxStationPower, 
            ambientTemperature
        );
        
        // Assert
        // Energy to add should be 60% of the remaining capacity
        double expectedEnergyToAdd = batteryProfile.getRemainingCapacityKwh() * 0.6;
        assertEquals(expectedEnergyToAdd, result.getEnergyToAddKwh(), 0.01);
        
        // Check that charging time is reasonable
        // With 250kW station power, perfect temperature, and 30% power reduction (for 60-80% SoC),
        // effective power should be around 175kW
        double expectedPower = 250.0 * 1.0 * (1 - 30.0/100.0);
        assertEquals(expectedPower, result.getEffectivePowerKw(), 0.1);
        
        // Check that temperature calculations are correct
        // Battery temp should be ambient + 5.9°C
        assertEquals(ambientTemperature + 5.9, result.getEndTemperatureCelsius(), 0.01);
    }
    
    @Test
    public void testCalculateChargingTime_WithChargingCurve() {
        // Arrange
        double startingSoc = 20.0;
        double targetSoc = 80.0;
        double maxStationPower = 250.0;
        double ambientTemperature = 25.0;
        
        // Create a mock charging curve
        Map<Double, Double> curvePoints = new HashMap<>();
        curvePoints.put(0.0, 150.0);   // 150 kW at 0% SoC
        curvePoints.put(20.0, 180.0);  // 180 kW at 20% SoC
        curvePoints.put(50.0, 100.0);  // 100 kW at 50% SoC
        curvePoints.put(80.0, 50.0);   // 50 kW at 80% SoC
        curvePoints.put(100.0, 10.0);  // 10 kW at 100% SoC
        ChargingCurve chargingCurve = new ChargingCurve(curvePoints);
        
        // Create a car profile with the charging curve
        CarProfile carWithCurve = new CarProfile.Builder()
            .id("test-id-curve")
            .name("Test EV with Curve")
            .manufacturer("Test Manufacturer")
            .model("Test Model")
            .year(2023)
            .hasHeatPump(true)
            .wltpRangeKm(500)
            .maxDcPowerKw(250.0)
            .maxAcPowerKw(11.0)
            .batteryProfile(batteryProfile)
            .consumptionProfile(new org.ulrica.domain.valueobject.ConsumptionProfile(15, 20, 25))
            .chargingCurve(chargingCurve)
            .build();
        
        // Act
        DcChargingResult result = calculator.calculateChargingTime(
            carWithCurve, 
            startingSoc, 
            targetSoc, 
            maxStationPower, 
            ambientTemperature
        );
        
        // Assert
        // Energy to add should be 60% of the remaining capacity
        double expectedEnergyToAdd = batteryProfile.getRemainingCapacityKwh() * 0.6;
        assertEquals(expectedEnergyToAdd, result.getEnergyToAddKwh(), 0.01);
        
        // With the charging curve, we can't easily predict the exact charging time,
        // but we can check that the values are in a reasonable range
        assertTrue(result.getChargingTimeHours() > 0);
        assertTrue(result.getEffectivePowerKw() > 0);
    }
    
    @Test
    public void testCalculateChargingTime_LowTemperature() {
        // Arrange - test with low temperature to check efficiency reduction
        double startingSoc = 20.0;
        double targetSoc = 60.0;
        double maxStationPower = 250.0;
        double ambientTemperature = -10.0; // Very cold
        
        // Act
        DcChargingResult result = calculator.calculateChargingTime(
            mockCarProfile, 
            startingSoc, 
            targetSoc, 
            maxStationPower, 
            ambientTemperature
        );
        
        // Assert
        // At -10°C ambient, battery temp is -4.1°C
        // This is below the optimal temperature range, so efficiency should be reduced
        double batteryTemp = ambientTemperature + 5.9;
        assertEquals(batteryTemp, result.getEndTemperatureCelsius(), 0.01);
        
        // Check that power is reduced due to temperature
        assertTrue(result.getEffectivePowerKw() < maxStationPower);
    }
    
    @Test
    public void testCalculateChargingTime_HigherSoC() {
        // Arrange - test with higher SoC range to check power reduction
        double startingSoc = 60.0;
        double targetSoc = 90.0;
        double maxStationPower = 250.0;
        double ambientTemperature = 25.0;
        
        // Act
        DcChargingResult result = calculator.calculateChargingTime(
            mockCarProfile, 
            startingSoc, 
            targetSoc, 
            maxStationPower, 
            ambientTemperature
        );
        
        // Assert
        // For SoC > 80%, power reduction should be 60%
        assertEquals(60.0, result.getPowerReductionPercent(), 0.01);
        
        // Check that effective power is reduced appropriately
        double expectedPower = maxStationPower * 1.0 * (1 - 60.0/100.0);
        assertEquals(expectedPower, result.getEffectivePowerKw(), 0.1);
    }
    
    @Test
    public void testCalculateChargingTime_StationPowerLimit() {
        // Arrange - test with station power lower than car's max power
        double startingSoc = 20.0;
        double targetSoc = 60.0;
        double maxStationPower = 50.0; // Much lower than car's 250 kW capability
        double ambientTemperature = 25.0;
        
        // Act
        DcChargingResult result = calculator.calculateChargingTime(
            mockCarProfile, 
            startingSoc, 
            targetSoc, 
            maxStationPower, 
            ambientTemperature
        );
        
        // Assert
        // Effective power should be limited by station, not car
        double expectedPower = maxStationPower * 1.0 * (1 - 5.0/100.0); // 5% reduction for SoC < 60%
        assertEquals(expectedPower, result.getEffectivePowerKw(), 0.1);
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
                invalidStartingSoc, 
                targetSoc, 
                250.0, 
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
                startingSoc, 
                invalidTargetSoc, 
                250.0, 
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
                startingSoc, 
                targetSoc, 
                250.0, 
                25.0
            );
        });
    }
    
    @Test
    public void testValidateInputParameters_InvalidStationPower() {
        // Arrange
        double startingSoc = 20.0;
        double targetSoc = 80.0;
        double invalidPower = -50.0;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateChargingTime(
                mockCarProfile, 
                startingSoc, 
                targetSoc, 
                invalidPower, 
                25.0
            );
        });
    }
    
    @Test
    public void testValidateInputParameters_InvalidTemperature() {
        // Arrange
        double startingSoc = 20.0;
        double targetSoc = 80.0;
        double invalidTemperature = -30.0; // Below minimum allowed
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateChargingTime(
                mockCarProfile, 
                startingSoc, 
                targetSoc, 
                250.0, 
                invalidTemperature
            );
        });
    }
} 