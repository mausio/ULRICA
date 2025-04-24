package org.ulrica.presentation.view;

import org.ulrica.application.port.out.CarProfileMenuOutputPort;

public class CarProfileMenuView implements CarProfileMenuOutputPort {
    @Override
    public void showCarProfileMenu() {
        System.out.println("=== ULRICA - Car Profile Management ===");
    }

    @Override
    public void showMenuOptions() {
        System.out.println("1. Create new car profile");
        System.out.println("2. Load existing car profile");
        System.out.println("3. Edit car profile");
        System.out.println("4. Delete car profile");
        System.out.println("5. Back to Main Menu");
    }

    @Override
    public void showPrompt() {
        System.out.print("\nEnter your choice: ");
    }
} 