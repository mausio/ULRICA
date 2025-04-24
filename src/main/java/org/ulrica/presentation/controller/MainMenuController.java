package org.ulrica.presentation.controller;

import java.util.Scanner;

import org.ulrica.application.port.in.ShowMainMenuUseCaseInterface;
import org.ulrica.application.port.out.MainMenuOutputPortInterface;
import org.ulrica.domain.entity.CarProfile;

public class MainMenuController {
    private final ShowMainMenuUseCaseInterface showMainMenuUseCase;
    private final MainMenuOutputPortInterface mainMenuView;
    private final Scanner scanner;
    private CarProfileController carProfileController;
    private CarProfile currentProfile;

    public MainMenuController(
            ShowMainMenuUseCaseInterface showMainMenuUseCase,
            MainMenuOutputPortInterface mainMenuView,
            CarProfileController carProfileController) {
        this.showMainMenuUseCase = showMainMenuUseCase;
        this.mainMenuView = mainMenuView;
        this.scanner = new Scanner(System.in);
        this.carProfileController = carProfileController;
        this.currentProfile = null;
    }

    public void setCarProfileController(CarProfileController carProfileController) {
        this.carProfileController = carProfileController;
    }

    public void showMainMenu() {
        mainMenuView.showMainMenu();
        if (currentProfile != null) {
            mainMenuView.showProfileSelected(currentProfile.getName());
        } else {
            mainMenuView.showNoProfileSelected();
        }
        mainMenuView.showMenuOptions();
        mainMenuView.showPrompt();
        handleUserInput();
    }

    public void setCurrentProfile(CarProfile profile) {
        this.currentProfile = profile;
    }

    private void handleUserInput() {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                carProfileController.showCarProfileMenu();
                showMainMenu();
                break;
            case 2:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                showMainMenu();
        }
    }
} 