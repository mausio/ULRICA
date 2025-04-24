package org.ulrica.application.port.out;


public interface ActionResultOutputPortInterface {

    void showNotImplemented(String actionName);

    void showSuccess(String message);
    
    void showError(String message);
} 