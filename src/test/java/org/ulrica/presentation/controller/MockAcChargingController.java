package org.ulrica.presentation.controller;

/**
 * A mock implementation of AcChargingController for testing.
 * Allows tracking calls to processAcChargingCalculation and controlling its result.
 */
public class MockAcChargingController extends AcChargingController {
    
    private boolean processAcChargingResult = true;
    private int processCallCount = 0;
    
    /**
     * Create a mock with null dependencies as we'll override the method
     */
    public MockAcChargingController() {
        super(null, null, null, null);
    }
    
    /**
     * Override to control the result and track calls
     */
    @Override
    public boolean processAcChargingCalculation() {
        processCallCount++;
        return processAcChargingResult;
    }
    
    /**
     * Set the result that processAcChargingCalculation should return
     * @param result The result to return
     */
    public void setProcessAcChargingResult(boolean result) {
        this.processAcChargingResult = result;
    }
    
    /**
     * Get the number of times processAcChargingCalculation was called
     * @return The call count
     */
    public int getProcessCallCount() {
        return processCallCount;
    }
    
    /**
     * Reset the call counter
     */
    public void resetCallCount() {
        processCallCount = 0;
    }
} 