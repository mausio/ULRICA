package org.ulrica.presentation.view;

import org.ulrica.application.port.out.ActionResultOutputPortInterface;

/**
 * A mock implementation of ActionResultOutputPortInterface for testing.
 * Tracks calls to methods and stores the most recent values for verification.
 */
public class MockActionResultView implements ActionResultOutputPortInterface {
    
    private String lastSuccessMessage;
    private String lastErrorMessage;
    private String lastNotImplementedAction;
    private int successCount = 0;
    private int errorCount = 0;
    private int notImplementedCount = 0;
    
    @Override
    public void showSuccess(String message) {
        this.lastSuccessMessage = message;
        successCount++;
    }
    
    @Override
    public void showError(String message) {
        this.lastErrorMessage = message;
        errorCount++;
    }
    
    @Override
    public void showNotImplemented(String actionName) {
        this.lastNotImplementedAction = actionName;
        notImplementedCount++;
    }
    
    /**
     * Get the most recent success message
     * @return The last success message or null if none
     */
    public String getLastSuccessMessage() {
        return lastSuccessMessage;
    }
    
    /**
     * Get the most recent error message
     * @return The last error message or null if none
     */
    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
    
    /**
     * Get the most recent not implemented action name
     * @return The last not implemented action or null if none
     */
    public String getLastNotImplementedAction() {
        return lastNotImplementedAction;
    }
    
    /**
     * Get count of success messages shown
     * @return Number of success calls
     */
    public int getSuccessCount() {
        return successCount;
    }
    
    /**
     * Get count of error messages shown
     * @return Number of error calls
     */
    public int getErrorCount() {
        return errorCount;
    }
    
    /**
     * Get count of not implemented messages shown
     * @return Number of not implemented calls
     */
    public int getNotImplementedCount() {
        return notImplementedCount;
    }
    
    /**
     * Reset all counters and messages
     */
    public void reset() {
        lastSuccessMessage = null;
        lastErrorMessage = null;
        lastNotImplementedAction = null;
        successCount = 0;
        errorCount = 0;
        notImplementedCount = 0;
    }
    
    /**
     * Check if a specific success message was shown
     * @param text Text to check for in the last success message
     * @return true if the text is contained in the last success message
     */
    public boolean lastSuccessContains(String text) {
        return lastSuccessMessage != null && lastSuccessMessage.contains(text);
    }
    
    /**
     * Check if a specific error message was shown
     * @param text Text to check for in the last error message
     * @return true if the text is contained in the last error message
     */
    public boolean lastErrorContains(String text) {
        return lastErrorMessage != null && lastErrorMessage.contains(text);
    }
    
    /**
     * Check if a specific action was marked as not implemented
     * @param actionName Action name to check for
     * @return true if the action name matches the last not implemented action
     */
    public boolean wasActionNotImplemented(String actionName) {
        return lastNotImplementedAction != null && lastNotImplementedAction.equals(actionName);
    }
} 