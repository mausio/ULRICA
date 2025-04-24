package org.ulrica.application.usecase;

import org.ulrica.application.port.in.ShowMainMenuUseCaseInterface;
import org.ulrica.application.port.out.MainMenuOutputPortInterface;

public class ShowMainMenuInteractor implements ShowMainMenuUseCaseInterface {
    private String selectedProfile;

    public ShowMainMenuInteractor() {
        this.selectedProfile = null;
    }

    @Override
    public void showMainMenu(MainMenuOutputPortInterface outputPort) {
        outputPort.showMainMenu();
        
        if (selectedProfile == null) {
            outputPort.showNoProfileSelected();
        } else {
            outputPort.showProfileSelected(selectedProfile);
        }
        
        outputPort.showMenuOptions();
        outputPort.showExitOption();
        outputPort.showPrompt();
    }

    public void setSelectedProfile(String profileName) {
        this.selectedProfile = profileName;
    }
} 