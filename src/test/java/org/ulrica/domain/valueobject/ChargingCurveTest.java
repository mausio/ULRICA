package org.ulrica.domain.valueobject;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Test;

public class ChargingCurveTest {

    @Test
    public void testConstructorAndGetters() {
        
        Map<Double, Double> curvePoints = new HashMap<>();
        curvePoints.put(0.0, 150.0);
        curvePoints.put(20.0, 180.0);
        curvePoints.put(50.0, 100.0);
        curvePoints.put(80.0, 50.0);
        curvePoints.put(100.0, 10.0);
        
        
        ChargingCurve chargingCurve = new ChargingCurve(curvePoints);
        
        
        Map<Double, Double> retrievedPoints = chargingCurve.getCurvePoints();
        assertEquals(curvePoints.size(), retrievedPoints.size());
        for (Map.Entry<Double, Double> entry : curvePoints.entrySet()) {
            assertEquals(entry.getValue(), retrievedPoints.get(entry.getKey()), 0.001);
        }
    }
    
    @Test
    public void testGetChargingPowerAt_ExactPoints() {
        
        Map<Double, Double> curvePoints = new HashMap<>();
        curvePoints.put(0.0, 150.0);
        curvePoints.put(50.0, 100.0);
        curvePoints.put(100.0, 10.0);
        ChargingCurve chargingCurve = new ChargingCurve(curvePoints);
        
        
        assertEquals(150.0, chargingCurve.getChargingPowerAt(0.0), 0.001);
        assertEquals(100.0, chargingCurve.getChargingPowerAt(50.0), 0.001);
        assertEquals(10.0, chargingCurve.getChargingPowerAt(100.0), 0.001);
    }
    
    @Test
    public void testGetChargingPowerAt_Interpolation() {
        
        Map<Double, Double> curvePoints = new HashMap<>();
        curvePoints.put(0.0, 150.0);
        curvePoints.put(50.0, 100.0);
        curvePoints.put(100.0, 10.0);
        ChargingCurve chargingCurve = new ChargingCurve(curvePoints);
        
        
        
        
        
        assertEquals(125.0, chargingCurve.getChargingPowerAt(25.0), 0.001);
        
        
        
        assertEquals(55.0, chargingCurve.getChargingPowerAt(75.0), 0.001);
    }
    
    @Test
    public void testGetChargingPowerAt_OutOfRangeLow() {
        
        TreeMap<Double, Double> curvePoints = new TreeMap<>();
        curvePoints.put(10.0, 180.0);
        curvePoints.put(50.0, 100.0);
        curvePoints.put(90.0, 20.0);
        ChargingCurve chargingCurve = new ChargingCurve(curvePoints);
        
        
        assertEquals(180.0, chargingCurve.getChargingPowerAt(5.0), 0.001);
    }
    
    @Test
    public void testGetChargingPowerAt_OutOfRangeHigh() {
        
        TreeMap<Double, Double> curvePoints = new TreeMap<>();
        curvePoints.put(10.0, 180.0);
        curvePoints.put(50.0, 100.0);
        curvePoints.put(90.0, 20.0);
        ChargingCurve chargingCurve = new ChargingCurve(curvePoints);
        
        
        assertEquals(20.0, chargingCurve.getChargingPowerAt(95.0), 0.001);
    }
    
    @Test
    public void testEqualsAndHashCode() {
        
        Map<Double, Double> curvePoints1 = new HashMap<>();
        curvePoints1.put(0.0, 150.0);
        curvePoints1.put(50.0, 100.0);
        curvePoints1.put(100.0, 10.0);
        
        Map<Double, Double> curvePoints2 = new HashMap<>();
        curvePoints2.put(0.0, 150.0);
        curvePoints2.put(50.0, 100.0);
        curvePoints2.put(100.0, 10.0);
        
        Map<Double, Double> curvePoints3 = new HashMap<>();
        curvePoints3.put(0.0, 150.0);
        curvePoints3.put(50.0, 100.0);
        curvePoints3.put(100.0, 20.0); 
        
        ChargingCurve curve1 = new ChargingCurve(curvePoints1);
        ChargingCurve curve2 = new ChargingCurve(curvePoints2);
        ChargingCurve curve3 = new ChargingCurve(curvePoints3);
        
        
        assertEquals(curve1, curve2);
        assertEquals(curve1.hashCode(), curve2.hashCode());
        assertNotEquals(curve1, curve3);
        assertNotEquals(curve1.hashCode(), curve3.hashCode());
    }
    
    @Test
    public void testInvalidConstructorArguments() {
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ChargingCurve(null);
        });
        
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ChargingCurve(new HashMap<>());
        });
        
        
        Map<Double, Double> invalidBatteryPercent1 = new HashMap<>();
        invalidBatteryPercent1.put(-10.0, 150.0);
        invalidBatteryPercent1.put(50.0, 100.0);
        assertThrows(IllegalArgumentException.class, () -> {
            new ChargingCurve(invalidBatteryPercent1);
        });
        
        
        Map<Double, Double> invalidBatteryPercent2 = new HashMap<>();
        invalidBatteryPercent2.put(0.0, 150.0);
        invalidBatteryPercent2.put(110.0, 100.0);
        assertThrows(IllegalArgumentException.class, () -> {
            new ChargingCurve(invalidBatteryPercent2);
        });
        
        
        Map<Double, Double> invalidPower = new HashMap<>();
        invalidPower.put(0.0, 0.0);
        invalidPower.put(50.0, 100.0);
        assertThrows(IllegalArgumentException.class, () -> {
            new ChargingCurve(invalidPower);
        });
    }
    
    @Test
    public void testInvalidGetChargingPowerAt() {
        
        Map<Double, Double> curvePoints = new HashMap<>();
        curvePoints.put(0.0, 150.0);
        curvePoints.put(50.0, 100.0);
        curvePoints.put(100.0, 10.0);
        ChargingCurve chargingCurve = new ChargingCurve(curvePoints);
        
        
        assertThrows(IllegalArgumentException.class, () -> {
            chargingCurve.getChargingPowerAt(-10.0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            chargingCurve.getChargingPowerAt(110.0);
        });
    }
} 