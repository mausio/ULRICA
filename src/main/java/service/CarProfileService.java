package service;

import model.CarProfile;
import repository.CarProfileRepository;
import utils.generalUtils.ConsoleInteractorUtil;
import utils.generalUtils.InputCleanerUtil;

import java.util.List;
import java.util.Scanner;

public class CarProfileService {
    private final CarProfileRepository repository;
    private final Scanner scanner;
    private CarProfile selectedProfile;

    public CarProfileService(CarProfileRepository repository) {
        this.repository = repository;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            ConsoleInteractorUtil.clear();
            System.out.println("\nCar Profile Management");
            if (selectedProfile != null) {
                System.out.println("Currently selected profile: " + selectedProfile.getName());
            }
            System.out.println("1. View all car profiles");
            System.out.println("2. Create new car profile");
            System.out.println("3. Delete car profile");
            System.out.println("4. Edit car profile");
            System.out.println("5. Exit");
            System.out.print("\nEnter your choice (1-5): ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    viewAllProfiles();
                    break;
                case "2":
                    createNewProfile();
                    break;
                case "3":
                    deleteProfile();
                    break;
                case "4":
                    editProfile();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewAllProfiles() {
        List<CarProfile> profiles = repository.findAll();
        if (profiles.isEmpty()) {
            System.out.println("\nNo car profiles found.");
            waitForEnter();
            return;
        }

        System.out.println("\nCar Profiles:");
        for (CarProfile profile : profiles) {
            displayProfile(profile);
        }
        
        System.out.print("\nWould you like to select a profile? (yes/no): ");
        if (InputCleanerUtil.formatYesOrNoToBoolean(scanner.nextLine())) {
            selectProfile(profiles);
        } else {
            waitForEnter();
        }
    }

    private void selectProfile(List<CarProfile> profiles) {
        System.out.println("\nSelect a profile:");
        for (int i = 0; i < profiles.size(); i++) {
            System.out.println((i + 1) + ". " + profiles.get(i).getName());
        }

        System.out.print("\nEnter number (1-" + profiles.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < profiles.size()) {
                selectedProfile = profiles.get(choice);
                System.out.println("Selected profile: " + selectedProfile.getName());
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        waitForEnter();
    }

    private void createNewProfile() {
        System.out.println("\nCreate New Car Profile");
        
        System.out.print("Enter profile name: ");
        String name = InputCleanerUtil.cleanWhitespacesAround(scanner.nextLine());

        System.out.print("Enter manufacturer: ");
        String manufacturer = InputCleanerUtil.cleanWhitespacesAround(scanner.nextLine());

        System.out.print("Enter model: ");
        String model = InputCleanerUtil.cleanWhitespacesAround(scanner.nextLine());

        System.out.print("Enter build year: ");
        int buildYear = InputCleanerUtil.cleanIntegerFromCharacters(scanner.nextLine());

        System.out.print("Has heat pump? (yes/no): ");
        boolean hasHeatPump = InputCleanerUtil.formatYesOrNoToBoolean(scanner.nextLine());

        CarProfile newProfile = new CarProfile(name, manufacturer, model, buildYear, hasHeatPump);
        repository.save(newProfile);

        // Automatically select the newly created profile
        selectedProfile = newProfile;
        System.out.println("\nCar profile created and selected successfully!");
        waitForEnter();
    }

    private void deleteProfile() {
        List<CarProfile> profiles = repository.findAll();
        if (profiles.isEmpty()) {
            System.out.println("\nNo car profiles to delete.");
            waitForEnter();
            return;
        }

        System.out.println("\nSelect profile to delete:");
        for (int i = 0; i < profiles.size(); i++) {
            System.out.println((i + 1) + ". " + profiles.get(i).getName());
        }

        System.out.print("\nEnter number (1-" + profiles.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < profiles.size()) {
                String id = profiles.get(choice).getId();
                // Clear selected profile if it's being deleted
                if (selectedProfile != null && selectedProfile.getId().equals(id)) {
                    selectedProfile = null;
                }
                repository.delete(id);
                System.out.println("Profile deleted successfully!");
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        waitForEnter();
    }

    private void editProfile() {
        List<CarProfile> profiles = repository.findAll();
        if (profiles.isEmpty()) {
            System.out.println("\nNo car profiles to edit.");
            waitForEnter();
            return;
        }

        System.out.println("\nSelect profile to edit:");
        for (int i = 0; i < profiles.size(); i++) {
            System.out.println((i + 1) + ". " + profiles.get(i).getName());
        }

        System.out.print("\nEnter number (1-" + profiles.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < profiles.size()) {
                CarProfile profileToEdit = profiles.get(choice);
                editProfileDetails(profileToEdit);
                // Update selected profile if it was edited
                if (selectedProfile != null && selectedProfile.getId().equals(profileToEdit.getId())) {
                    selectedProfile = profileToEdit;
                }
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        waitForEnter();
    }

    private void editProfileDetails(CarProfile profile) {
        System.out.println("\nEditing profile: " + profile.getName());
        System.out.println("(Press Enter to keep current value)");

        System.out.print("Enter new name [" + profile.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            profile.setName(InputCleanerUtil.cleanWhitespacesAround(name));
        }

        System.out.print("Enter new manufacturer [" + profile.getManufacturer() + "]: ");
        String manufacturer = scanner.nextLine();
        if (!manufacturer.isEmpty()) {
            profile.setManufacturer(InputCleanerUtil.cleanWhitespacesAround(manufacturer));
        }

        System.out.print("Enter new model [" + profile.getModel() + "]: ");
        String model = scanner.nextLine();
        if (!model.isEmpty()) {
            profile.setModel(InputCleanerUtil.cleanWhitespacesAround(model));
        }

        System.out.print("Enter new build year [" + profile.getBuildYear() + "]: ");
        String buildYear = scanner.nextLine();
        if (!buildYear.isEmpty()) {
            profile.setBuildYear(InputCleanerUtil.cleanIntegerFromCharacters(buildYear));
        }

        System.out.print("Has heat pump? (yes/no) [" + (profile.isHasHeatPump() ? "yes" : "no") + "]: ");
        String hasHeatPump = scanner.nextLine();
        if (!hasHeatPump.isEmpty()) {
            profile.setHasHeatPump(InputCleanerUtil.formatYesOrNoToBoolean(hasHeatPump));
        }

        repository.update(profile);
        System.out.println("Profile updated successfully!");
    }

    private void displayProfile(CarProfile profile) {
        System.out.println("\n----------------------------------------");
        System.out.println("Name: " + profile.getName());
        System.out.println("Manufacturer: " + profile.getManufacturer());
        System.out.println("Model: " + profile.getModel());
        System.out.println("Build Year: " + profile.getBuildYear());
        System.out.println("Heat Pump: " + (profile.isHasHeatPump() ? "Yes" : "No"));
        System.out.println("----------------------------------------");
    }

    private void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    // Getter for the selected profile
    public CarProfile getSelectedProfile() {
        return selectedProfile;
    }
} 
