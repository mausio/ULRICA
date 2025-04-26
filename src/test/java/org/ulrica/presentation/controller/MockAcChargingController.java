package org.ulrica.presentation.controller;

public class MockAcChargingController extends AcChargingController {
    
    private boolean processAcChargingResult = true;
    private int processCallCount = 0;
    
    public MockAcChargingController() {
        super(null, null, null, null);
    }
    
    @Override
    public boolean processAcChargingCalculation() {
        processCallCount++;
        return processAcChargingResult;
    }
    
    public void setProcessAcChargingResult(boolean result) {
        this.processAcChargingResult = result;
    }
    
    public int getProcessCallCount() {
        return processCallCount;
    }
    
    public void resetCallCount() {
        processCallCount = 0;
    }
} 