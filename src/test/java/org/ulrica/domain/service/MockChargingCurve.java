package org.ulrica.domain.service;

import java.util.HashMap;
import java.util.Map;

import org.ulrica.domain.valueobject.ChargingCurve;

public class MockChargingCurve {
    public static ChargingCurve createSimpleCurve() {
        Map<Double, Double> curvePoints = new HashMap<>();
        
        
        curvePoints.put(0.0, 150.0);   
        curvePoints.put(20.0, 180.0);  
        curvePoints.put(50.0, 100.0);  
        curvePoints.put(80.0, 50.0);   
        curvePoints.put(100.0, 10.0);  
        
        return new ChargingCurve(curvePoints);
    }
    
    public static ChargingCurve createFlatCurve(double powerKw) {
        Map<Double, Double> curvePoints = new HashMap<>();
        
        curvePoints.put(0.0, powerKw);
        curvePoints.put(100.0, powerKw);
        
        return new ChargingCurve(curvePoints);
    }
} 