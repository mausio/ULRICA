package org.ulrica.application.usecase;

import org.ulrica.application.port.in.ShowCarProfileMenuUseCaseInterface;
import org.ulrica.application.port.out.CarProfileMenuOutputPort;

public class ShowCarProfileMenuInteractor implements ShowCarProfileMenuUseCaseInterface {
    @Override
    public void showCarProfileMenu(CarProfileMenuOutputPort outputPort) {
        outputPort.showCarProfileMenu();
        outputPort.showMenuOptions();
        outputPort.showPrompt();
    }
} 