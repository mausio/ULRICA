package org.ulrica.presentation.controller;

public class MockDcChargingController extends DcChargingController {
    
    private boolean processDcChargingResult = true;
    private int processCallCount = 0;
    
    public MockDcChargingController() {
        super(null, null, null, null);
    }
    
    @Override
    public boolean processDcChargingCalculation() {
        processCallCount++;
        return processDcChargingResult;
    }
    
    public void setProcessDcChargingResult(boolean result) {
        this.processDcChargingResult = result;
    }
    
    public int getProcessCallCount() {
        return processCallCount;
    }
    
    public void resetCallCount() {
        processCallCount = 0;
    }
} 