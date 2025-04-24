package org.ulrica.application.port.in;

import org.ulrica.domain.ApplicationState;

public interface NavigationUseCaseInterface {
    void navigateToCarProfileMenu();
    void navigateToMainMenu();
    void navigateToCreateCarProfile();
    void navigateToEditCarProfile();
    void navigateToDeleteCarProfile();
    void exit();
    ApplicationState getCurrentState();
    boolean shouldContinue();
} 