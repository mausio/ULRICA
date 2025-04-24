package org.ulrica.presentation.controller;

import org.ulrica.application.port.in.ShowWelcomeUseCaseInterface;
import org.ulrica.application.port.out.WelcomeOutputPortInterface;

public class WelcomeController {
    private final ShowWelcomeUseCaseInterface showWelcomeUseCase;
    private final WelcomeOutputPortInterface welcomeView;

    public WelcomeController(
            ShowWelcomeUseCaseInterface showWelcomeUseCase,
            WelcomeOutputPortInterface welcomeView) {
        this.showWelcomeUseCase = showWelcomeUseCase;
        this.welcomeView = welcomeView;
    }

    public void showWelcome() {
        showWelcomeUseCase.showWelcome(welcomeView);
    }
} 