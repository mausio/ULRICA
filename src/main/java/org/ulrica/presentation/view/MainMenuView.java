package org.ulrica.presentation.view;

import org.ulrica.application.port.out.MainMenuOutputPortInterface;
import org.ulrica.presentation.util.AnsiColors;

public class MainMenuView implements MainMenuOutputPortInterface {
    @Override
    public void showMainMenu() {
        System.out.println("\n=== ULRICA - Main Menu ===");
    }

    @Override
    public void showNoProfileSelected() {
        System.out.println(AnsiColors.BLUE + "No car profile selected!" + AnsiColors.RESET);
    }

    @Override
    public void showProfileSelected(String profileName) {
        System.out.println(AnsiColors.GREEN + "Currently selected profile: " + AnsiColors.RESET + profileName);
    }

    @Override
    public void showMenuOptions() {
        System.out.println("1. Car Profile Management");
        System.out.println("2. Exit");
    }

    @Override
    public void showPrompt() {
        System.out.print("\nEnter your choice: ");
    }
} 