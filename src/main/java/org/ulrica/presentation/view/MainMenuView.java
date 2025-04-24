package org.ulrica.presentation.view;

import org.ulrica.application.port.out.MainMenuOutputPortInterface;

public class MainMenuView implements MainMenuOutputPortInterface {
    @Override
    public void showMainMenu() {
        System.out.println("=== ULRICA - Main Menu ===");
    }

    @Override
    public void showNoProfileSelected() {
        System.out.println("No car profile selected!");
    }

    @Override
    public void showProfileSelected(String profileName) {
        System.out.println("Currently selected profile: " + profileName);
    }

    @Override
    public void showMenuOptions() {
        System.out.println("1. Car Profile Management");
    }

    @Override
    public void showExitOption() {
        System.out.println("2. Exit");
    }

    @Override
    public void showPrompt() {
        System.out.print("\nEnter your choice: ");
    }
} 