package org.ulrica.presentation.view;

import org.ulrica.application.port.out.ActionResultOutputPortInterface;

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
    
    public String getLastSuccessMessage() {
        return lastSuccessMessage;
    }
    
    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
    
    public String getLastNotImplementedAction() {
        return lastNotImplementedAction;
    }
    
    public int getSuccessCount() {
        return successCount;
    }
    
    public int getErrorCount() {
        return errorCount;
    }
    
    public int getNotImplementedCount() {
        return notImplementedCount;
    }
    
    public void reset() {
        lastSuccessMessage = null;
        lastErrorMessage = null;
        lastNotImplementedAction = null;
        successCount = 0;
        errorCount = 0;
        notImplementedCount = 0;
    }
    
    public boolean lastSuccessContains(String text) {
        return lastSuccessMessage != null && lastSuccessMessage.contains(text);
    }
    
    public boolean lastErrorContains(String text) {
        return lastErrorMessage != null && lastErrorMessage.contains(text);
    }
    
    public boolean wasActionNotImplemented(String actionName) {
        return lastNotImplementedAction != null && lastNotImplementedAction.equals(actionName);
    }
} 