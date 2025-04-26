package org.ulrica.presentation.controller;

/**
 * A mock implementation of DcChargingController for testing.
 * Allows tracking calls to processDcChargingCalculation and controlling its result.
 */
public class MockDcChargingController extends DcChargingController {
    
    private boolean processDcChargingResult = true;
    private int processCallCount = 0;
    
    /**
     * Create a mock with null dependencies as we'll override the method
     */
    public MockDcChargingController() {
        super(null, null, null, null);
    }
    
    /**
     * Override to control the result and track calls
     */
    @Override
    public boolean processDcChargingCalculation() {
        processCallCount++;
        return processDcChargingResult;
    }
    
    /**
     * Set the result that processDcChargingCalculation should return
     * @param result The result to return
     */
    public void setProcessDcChargingResult(boolean result) {
        this.processDcChargingResult = result;
    }
    
    /**
     * Get the number of times processDcChargingCalculation was called
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