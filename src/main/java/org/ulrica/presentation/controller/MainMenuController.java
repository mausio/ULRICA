package org.ulrica.presentation.controller;

import java.util.Scanner;

import org.ulrica.application.port.in.NavigationUseCaseInterface;
import org.ulrica.application.port.in.ProfileSelectionListener;
import org.ulrica.application.port.in.ShowMainMenuUseCaseInterface;
import org.ulrica.application.port.out.MainMenuOutputPortInterface;
import org.ulrica.domain.entity.CarProfile;

public class MainMenuController implements ProfileSelectionListener {
    private final ShowMainMenuUseCaseInterface showMainMenuUseCase;
    private final MainMenuOutputPortInterface mainMenuView;
    private final Scanner scanner;
    private final NavigationUseCaseInterface navigationUseCase;
    private CarProfile currentProfile;

    public MainMenuController(
            ShowMainMenuUseCaseInterface showMainMenuUseCase,
            MainMenuOutputPortInterface mainMenuView,
            NavigationUseCaseInterface navigationUseCase) {
        this.showMainMenuUseCase = showMainMenuUseCase;
        this.mainMenuView = mainMenuView;
        this.navigationUseCase = navigationUseCase;
        this.scanner = new Scanner(System.in);
        this.currentProfile = null;
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

    @Override
    public void onProfileSelected(CarProfile profile) {
        this.currentProfile = profile;
    }

    private void handleUserInput() {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                navigationUseCase.navigateToCarProfileMenu();
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