package org.ulrica.presentation.view;

import org.ulrica.application.port.out.ActionResultOutputPortInterface;
import org.ulrica.application.port.out.UserOutputPortInterface;

public class ActionResultView implements ActionResultOutputPortInterface {
    
    private final UserOutputPortInterface userOutputPort;
    
    public ActionResultView(UserOutputPortInterface userOutputPort) {
        this.userOutputPort = userOutputPort;
    }
    
    @Override
    public void showNotImplemented(String actionName) {
        userOutputPort.displayLine("\n=== " + actionName + " ===");
        userOutputPort.displayLine("This feature is not yet implemented.");
        userOutputPort.displayLine("\nPress Enter to continue...");
    }
    
    @Override
    public void showSuccess(String message) {
        userOutputPort.displayLine("\n=== Success ===");
        userOutputPort.displayLine(message);
        userOutputPort.displayLine("\nPress Enter to continue...");
    }
    
    @Override
    public void showError(String message) {
        userOutputPort.displayLine("\n=== Error ===");
        userOutputPort.displayLine(message);
        userOutputPort.displayLine("\nPress Enter to continue...");
    }
} 