package org.ulrica.application.port.out;

public interface MainMenuOutputPortInterface {
    void showMainMenu();
    void showNoProfileSelected();
    void showProfileSelected(String profileName);
    void showMenuOptions();
    void showExitOption();
    void showPrompt();
} 