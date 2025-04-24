package org.ulrica.presentation.controller;

import java.util.Scanner;

import org.ulrica.application.port.in.ShowMainMenuUseCaseInterface;
import org.ulrica.application.port.out.MainMenuOutputPortInterface;
import org.ulrica.presentation.view.MainMenuView;

public class MainMenuController {
    private final ShowMainMenuUseCaseInterface showMainMenuUseCase;
    private final MainMenuOutputPortInterface mainMenuView;
    private final Scanner scanner;
    private final CarProfileMenuController carProfileMenuController;

    public MainMenuController(ShowMainMenuUseCaseInterface showMainMenuUseCase, CarProfileMenuController carProfileMenuController) {
        this.showMainMenuUseCase = showMainMenuUseCase;
        this.mainMenuView = new MainMenuView();
        this.scanner = new Scanner(System.in);
        this.carProfileMenuController = carProfileMenuController;
    }

    public void showMainMenu() {
        showMainMenuUseCase.showMainMenu(mainMenuView);
        handleUserInput();
    }

    private void handleUserInput() {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                carProfileMenuController.showCarProfileMenu();
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