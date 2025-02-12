package service;

import java.util.List;
import java.util.Scanner;

import interfaces.ProfileAction;
import model.CarProfile;
import utils.generalUtils.ConsoleInteractorUtil;

public class ActionService {
    private final CarProfile selectedProfile;
    private final ActionRegistry actionRegistry;
    private final Scanner scanner;

    public ActionService(CarProfile selectedProfile) {
        this.selectedProfile = selectedProfile;
        this.actionRegistry = new ActionRegistry();
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            ConsoleInteractorUtil.clear();
            System.out.println("\n=== Action Menu ===");
            System.out.println("Selected Profile: " + selectedProfile.getName());
            System.out.println("\nAvailable Actions:");

            List<ProfileAction> actions = actionRegistry.getActions();
            for (int i = 0; i < actions.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, actions.get(i).getDisplayName());
            }
            System.out.printf("%d. Back to Main Menu%n", actions.size() + 1);

            System.out.print("\nSelect an action: ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                break;
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice > 0 && choice < actions.size() + 1) {
                    actions.get(choice - 1).execute(selectedProfile);
                } else if (choice == actions.size() + 1) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
} 