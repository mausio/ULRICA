package org.ulrica.application.usecase;

import org.ulrica.application.port.in.ShowWelcomeUseCaseInterface;
import org.ulrica.application.port.out.WelcomeOutputPortInterface;

public class ShowWelcomeInteractor implements ShowWelcomeUseCaseInterface {
    @Override
    public void showWelcome(WelcomeOutputPortInterface outputPort) {
        outputPort.displayWelcomeMessage();
        outputPort.displayAttentionMessage();
    }
} 