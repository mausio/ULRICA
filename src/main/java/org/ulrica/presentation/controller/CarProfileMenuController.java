package org.ulrica.presentation.controller;

import java.util.Scanner;

import org.ulrica.application.port.in.ShowCarProfileMenuUseCaseInterface;
import org.ulrica.application.port.out.CarProfileMenuOutputPort;
import org.ulrica.presentation.view.CarProfileMenuView;

public class CarProfileMenuController {
    private final ShowCarProfileMenuUseCaseInterface showCarProfileMenuUseCase;
    private final CarProfileMenuOutputPort carProfileMenuView;
    private final Scanner scanner;

    public CarProfileMenuController(ShowCarProfileMenuUseCaseInterface showCarProfileMenuUseCase) {
        this.showCarProfileMenuUseCase = showCarProfileMenuUseCase;
        this.carProfileMenuView = new CarProfileMenuView();
        this.scanner = new Scanner(System.in);
    }

    public void showCarProfileMenu() {
        showCarProfileMenuUseCase.showCarProfileMenu(carProfileMenuView);
        handleUserInput();
    }

    private void handleUserInput() {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                // TODO: Navigate to Create Car Profile
                break;
            case 2:
                // TODO: Navigate to Load Car Profile
                break;
            case 3:
                // TODO: Navigate to Edit Car Profile
                break;
            case 4:
                // TODO: Navigate to Delete Car Profile
                break;
            case 5:
                return; // Back to Main Menu
            default:
                System.out.println("Invalid choice. Please try again.");
                showCarProfileMenu();
        }
    }
} 