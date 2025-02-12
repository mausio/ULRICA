package service;

import java.util.List;
import java.util.Scanner;

import interfaces.ProfileAction;
import model.CarProfile;
import utils.generalUtils.ConsoleInteractorUtil;


public class ActionService {
    private final Scanner scanner;
    private final CarProfile carProfile;
    private final ActionRegistry actionRegistry;

    public ActionService(CarProfile carProfile) {
        this.scanner = new Scanner(System.in);
        this.carProfile = carProfile;
        this.actionRegistry = ActionRegistry.getInstance();
    }

    public void showMenu() {
        ConsoleInteractorUtil.clear();
        displayMenu();
        handleUserChoice();
    }

    private void displayMenu() {
        System.out.println("\n=== Actions for " + carProfile.getName() + " ===");
        List<ProfileAction> actions = actionRegistry.getAvailableActions();
        
        for (int i = 0; i < actions.size(); i++) {
            System.out.println((i + 1) + ". " + actions.get(i).getDisplayName());
        }
        System.out.println("2. Back to Main Menu");
    }

    private void handleUserChoice() {
        List<ProfileAction> actions = actionRegistry.getAvailableActions();
        
        while (true) {
            System.out.print("\nEnter your choice (1-" + (actions.size() + 1) + "): ");
            String input = scanner.nextLine().trim();

            if (input.equals("2")) {
                return;
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice > 0 && choice <= actions.size()) {
                    ProfileAction selectedAction = actions.get(choice - 1);
                    selectedAction.execute(carProfile);
                    ConsoleInteractorUtil.clear();
                    displayMenu();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
} 