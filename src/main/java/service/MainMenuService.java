package service;

import java.util.Scanner;

import model.CarProfile;
import model.state.ApplicationState;
import utils.generalUtils.ConsoleInteractorUtil;

public class MainMenuService {
    private final Scanner scanner;
    private final CarProfileService carProfileService;

    public MainMenuService(CarProfileService carProfileService) {
        this.scanner = new Scanner(System.in);
        this.carProfileService = carProfileService;
    }

    public ApplicationState showMenu() {
        ConsoleInteractorUtil.clear();
        System.out.println("\n=== ULRICA - Main Menu ===");
        
        CarProfile selectedProfile = carProfileService.getSelectedProfile();
        if (selectedProfile != null) {
            System.out.println("\u001B[34mCurrently selected profile: " + selectedProfile.getName() + "\u001B[0m");
            System.out.println("1. Car Profile Management");
            System.out.println("2. Actions Menu");
            System.out.println("3. Exit");
            
            System.out.print("\nEnter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    return ApplicationState.PROFILE_MANAGEMENT;
                case "2":
                    return ApplicationState.ACTION_MENU;
                case "3":
                    return ApplicationState.EXIT;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    waitForEnter();
                    return ApplicationState.MAIN_MENU;
            }
        } else {
            System.out.println("\u001B[31mNo car profile selected!\u001B[0m");
            System.out.println("1. Car Profile Management");
            System.out.println("2. Exit");
            
            System.out.print("\nEnter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    return ApplicationState.PROFILE_MANAGEMENT;
                case "2":
                    return ApplicationState.EXIT;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    waitForEnter();
                    return ApplicationState.MAIN_MENU;
            }
        }
    }

    private void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
} 