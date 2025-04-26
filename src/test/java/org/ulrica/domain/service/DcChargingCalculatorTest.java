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
        double maxStationPower = 250.0;
        double ambientTemperature = 25.0;
        
        
        DcChargingResult result = calculator.calculateChargingTime(
            mockCarProfile, 
            startingSoc, 
            targetSoc, 
            maxStationPower, 
            ambientTemperature
        );
        
        
        
        double expectedEnergyToAdd = batteryProfile.getRemainingCapacityKwh() * 0.6;
        assertEquals(expectedEnergyToAdd, result.getEnergyToAddKwh(), 0.01);
        
        
        
        
        double expectedPower = 250.0 * 1.0 * (1 - 30.0/100.0);
        assertEquals(expectedPower, result.getEffectivePowerKw(), 0.1);
        
        
        
        assertEquals(ambientTemperature + 5.9, result.getEndTemperatureCelsius(), 0.01);
    }
    
    @Test
    public void testCalculateChargingTime_WithChargingCurve() {
        
        double startingSoc = 20.0;
        double targetSoc = 80.0;
        double maxStationPower = 250.0;
        double ambientTemperature = 25.0;
        
        
        Map<Double, Double> curvePoints = new HashMap<>();
        curvePoints.put(0.0, 150.0);   
        curvePoints.put(20.0, 180.0);  
        curvePoints.put(50.0, 100.0);  
        curvePoints.put(80.0, 50.0);   
        curvePoints.put(100.0, 10.0);  
        ChargingCurve chargingCurve = new ChargingCurve(curvePoints);
        
        
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
        
        
        DcChargingResult result = calculator.calculateChargingTime(
            carWithCurve, 
            startingSoc, 
            targetSoc, 
            maxStationPower, 
            ambientTemperature
        );
        
        
        
        double expectedEnergyToAdd = batteryProfile.getRemainingCapacityKwh() * 0.6;
        assertEquals(expectedEnergyToAdd, result.getEnergyToAddKwh(), 0.01);
        
        
        
        assertTrue(result.getChargingTimeHours() > 0);
        assertTrue(result.getEffectivePowerKw() > 0);
    }
    
    @Test
    public void testCalculateChargingTime_LowTemperature() {
        
        double startingSoc = 20.0;
        double targetSoc = 60.0;
        double maxStationPower = 250.0;
        double ambientTemperature = -10.0; 
        
        
        DcChargingResult result = calculator.calculateChargingTime(
            mockCarProfile, 
            startingSoc, 
            targetSoc, 
            maxStationPower, 
            ambientTemperature
        );
        
        
        
        
        double batteryTemp = ambientTemperature + 5.9;
        assertEquals(batteryTemp, result.getEndTemperatureCelsius(), 0.01);
        
        
        assertTrue(result.getEffectivePowerKw() < maxStationPower);
    }
    
    @Test
    public void testCalculateChargingTime_HigherSoC() {
        
        double startingSoc = 60.0;
        double targetSoc = 90.0;
        double maxStationPower = 250.0;
        double ambientTemperature = 25.0;
        
        
        DcChargingResult result = calculator.calculateChargingTime(
            mockCarProfile, 
            startingSoc, 
            targetSoc, 
            maxStationPower, 
            ambientTemperature
        );
        
        
        
        assertEquals(60.0, result.getPowerReductionPercent(), 0.01);
        
        
        double expectedPower = maxStationPower * 1.0 * (1 - 60.0/100.0);
        assertEquals(expectedPower, result.getEffectivePowerKw(), 0.1);
    }
    
    @Test
    public void testCalculateChargingTime_StationPowerLimit() {
        
        double startingSoc = 20.0;
        double targetSoc = 60.0;
        double maxStationPower = 50.0; 
        double ambientTemperature = 25.0;
        
        
        DcChargingResult result = calculator.calculateChargingTime(
            mockCarProfile, 
            startingSoc, 
            targetSoc, 
            maxStationPower, 
            ambientTemperature
        );
        
        
        
        double expectedPower = maxStationPower * 1.0 * (1 - 5.0/100.0); 
        assertEquals(expectedPower, result.getEffectivePowerKw(), 0.1);
    }
    
    @Test
    public void testValidateInputParameters_InvalidStartingSoc() {
        
        double invalidStartingSoc = -10.0;
        double targetSoc = 80.0;
        
        
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
        
        double startingSoc = 20.0;
        double invalidTargetSoc = 110.0;
        
        
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
        
        double startingSoc = 80.0;
        double targetSoc = 70.0; 
        
        
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
        
        double startingSoc = 20.0;
        double targetSoc = 80.0;
        double invalidPower = -50.0;
        
        
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
        
        double startingSoc = 20.0;
        double targetSoc = 80.0;
        double invalidTemperature = -30.0; 
        
        
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