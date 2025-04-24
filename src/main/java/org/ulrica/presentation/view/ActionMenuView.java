package org.ulrica.presentation.view;

import org.ulrica.application.port.out.ActionMenuOutputPortInterface;
import org.ulrica.domain.entity.CarProfile;

public class ActionMenuView implements ActionMenuOutputPortInterface {
    
    private final org.ulrica.application.port.out.UserOutputPortInterface userOutputPort;
    
    public ActionMenuView(org.ulrica.application.port.out.UserOutputPortInterface userOutputPort) {
        this.userOutputPort = userOutputPort;
    }
    
    @Override
    public void showActionMenuHeader() {
        userOutputPort.displayLine("\n=== Action Menu ===");
    }
    
    @Override
    public void showSelectedProfile(CarProfile profile) {
        userOutputPort.displayLine("Selected Profile: " + profile.getName());
        userOutputPort.displayLine("");
    }
    
    @Override
    public void showActionOptions() {
        userOutputPort.displayLine("Available Actions:");
        userOutputPort.displayLine("1. DC (Fast) Charging Calculator");
        userOutputPort.displayLine("2. AC (Slow) Charging Calculator");
        userOutputPort.displayLine("3. Calculate Range with Current Conditions");
        userOutputPort.displayLine("4. Back to Main Menu");
    }
    
    @Override
    public void showPrompt() {
        userOutputPort.display("Select an action: ");
    }
    
    @Override
    public void showInvalidChoice() {
        userOutputPort.displayLine("Invalid choice. Please try again.");
    }
    
    @Override
    public void displayActionMenu() {
        showActionMenuHeader();
        showActionOptions();
        showPrompt();
    }
    
    @Override
    public void displaySelectedProfile(String profileName) {
        userOutputPort.displayLine("Selected Profile: " + profileName);
        userOutputPort.displayLine("");
    }
} 