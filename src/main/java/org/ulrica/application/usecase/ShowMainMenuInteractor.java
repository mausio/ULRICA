package org.ulrica.application.usecase;

import org.ulrica.application.port.in.ShowMainMenuUseCaseInterface;
import org.ulrica.application.port.out.MainMenuOutputPortInterface;

public class ShowMainMenuInteractor implements ShowMainMenuUseCaseInterface {
    @Override
    public void showMainMenu(MainMenuOutputPortInterface outputPort) {
        outputPort.showMainMenu();
        outputPort.showMenuOptions();
        outputPort.showPrompt();
    }
} 