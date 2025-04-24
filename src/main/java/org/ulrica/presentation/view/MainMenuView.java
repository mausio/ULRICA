package org.ulrica.presentation.view;

import org.ulrica.application.port.out.MainMenuOutputPortInterface;
import org.ulrica.application.port.out.UserOutputPortInterface;

public class MainMenuView implements MainMenuOutputPortInterface {
    private final UserOutputPortInterface userOutputPort;
    
    public MainMenuView(UserOutputPortInterface userOutputPort) {
        this.userOutputPort = userOutputPort;
    }
    
    @Override
    public void showMainMenu() {
        userOutputPort.displayLine("\n=== ULRICA - Main Menu ===");
    }

    @Override
    public void showNoProfileSelected() {
        userOutputPort.displayLine("No car profile selected!");
    }

    @Override
    public void showProfileSelected(String profileName) {
        userOutputPort.displayLine("Currently selected profile: " + profileName);
    }

    @Override
    public void showMenuOptions() {
        userOutputPort.displayLine("1. Car Profile Management");
        userOutputPort.displayLine("2. Actions Menu");
        userOutputPort.displayLine("3. Exit");
    }

    @Override
    public void showPrompt() {
        userOutputPort.display("Enter your choice: ");
    }
} 