package org.ulrica.domain.service;

import java.util.HashMap;
import java.util.Map;

import org.ulrica.domain.valueobject.ChargingCurve;

/**
 * A mock implementation of ChargingCurve for testing purposes.
 */
public class MockChargingCurve {
    /**
     * Creates a simple charging curve with predefined values
     * @return A ChargingCurve instance with test data
     */
    public static ChargingCurve createSimpleCurve() {
        Map<Double, Double> curvePoints = new HashMap<>();
        
        // Define charging power at different SoC percentages
        curvePoints.put(0.0, 150.0);   // 150 kW at 0% SoC
        curvePoints.put(20.0, 180.0);  // 180 kW at 20% SoC
        curvePoints.put(50.0, 100.0);  // 100 kW at 50% SoC
        curvePoints.put(80.0, 50.0);   // 50 kW at 80% SoC
        curvePoints.put(100.0, 10.0);  // 10 kW at 100% SoC
        
        return new ChargingCurve(curvePoints);
    }
    
    /**
     * Creates a flat charging curve (constant power regardless of SoC)
     * @param powerKw The constant power value in kW
     * @return A ChargingCurve instance with constant power
     */
    public static ChargingCurve createFlatCurve(double powerKw) {
        Map<Double, Double> curvePoints = new HashMap<>();
        
        curvePoints.put(0.0, powerKw);
        curvePoints.put(100.0, powerKw);
        
        return new ChargingCurve(curvePoints);
    }
} 