package org.ulrica.presentation.controller;

import org.ulrica.application.port.in.ShowWelcomeUseCaseInterface;
import org.ulrica.application.port.out.WelcomeOutputPortInterface;
import org.ulrica.presentation.view.WelcomeView;

public class WelcomeController {
    private final ShowWelcomeUseCaseInterface showWelcomeUseCase;
    private final WelcomeOutputPortInterface welcomeView;

    public WelcomeController(ShowWelcomeUseCaseInterface showWelcomeUseCase) {
        this.showWelcomeUseCase = showWelcomeUseCase;
        this.welcomeView = new WelcomeView();
    }

    public void showWelcome() {
        showWelcomeUseCase.showWelcome(welcomeView);
    }
} 