package org.ulrica.presentation.controller;

import java.util.List;
import java.util.Scanner;

import org.ulrica.application.port.in.CreateCarProfileUseCaseInterface;
import org.ulrica.application.port.in.ProfileSelectionListener;
import org.ulrica.application.port.out.CarProfilePersistencePortInterface;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.BatteryType;
import org.ulrica.presentation.util.InputValidator;
import org.ulrica.presentation.view.CarProfileView;

public class CarProfileController {
    private final CreateCarProfileUseCaseInterface createCarProfileUseCase;
    private final CarProfilePersistencePortInterface persistencePort;
    private final CarProfileView view;
    private final Scanner scanner;
    private final ProfileSelectionListener profileSelectionListener;

    public CarProfileController(
            CreateCarProfileUseCaseInterface createCarProfileUseCase,
            CarProfilePersistencePortInterface persistencePort,
            CarProfileView view,
            ProfileSelectionListener profileSelectionListener) {
        this.createCarProfileUseCase = createCarProfileUseCase;
        this.persistencePort = persistencePort;
        this.view = view;
        this.scanner = new Scanner(System.in);
        this.profileSelectionListener = profileSelectionListener;
    }

    public void showCarProfileMenu() {
        view.showMenu();
        handleUserInput();
    }

    private void handleUserInput() {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                viewAllCarProfiles();
                break;
            case 2:
                createNewCarProfile();
                break;
            case 3:
                deleteCarProfile();
                break;
            case 4:
                editCarProfile();
                break;
            case 5:
                return; // Back to main menu
            default:
                view.showInvalidChoice();
                showCarProfileMenu();
        }
    }

    private void viewAllCarProfiles() {
        List<CarProfile> profiles = persistencePort.findAll();
        if (profiles.isEmpty()) {
            view.showNoProfilesMessage();
            showCarProfileMenu();
            return;
        }
        view.showAllCarProfiles(profiles);
        
        view.askToSelectProfile();
        String response = scanner.nextLine().toLowerCase();
        if (response.equals("yes") || response.equals("y")) {
            selectCarProfile(profiles);
        }
        
        // Return to menu after selection or if user chose not to select
        showCarProfileMenu();
    }

    private void selectCarProfile(List<CarProfile> profiles) {
        view.showProfileSelection(profiles);
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (choice > 0 && choice <= profiles.size()) {
            CarProfile selectedProfile = profiles.get(choice - 1);
            view.showSelectedProfile(selectedProfile);
            profileSelectionListener.onProfileSelected(selectedProfile);
        } else {
            view.showInvalidChoice();
        }
    }

    private void createNewCarProfile() {
        view.showCreateProfileHeader();
        
        String name = getValidatedInput("Enter profile name: ", "name");
        String manufacturer = getValidatedInput("Enter manufacturer: ", "manufacturer");
        String model = getValidatedInput("Enter model: ", "model");
        int year = getValidatedIntInput("Enter build year: ", "year");
        boolean hasHeatPump = getBooleanInput("Has heat pump? (yes/no): ");
        double wltpRangeKm = getValidatedDoubleInput("Enter WLTP range (km): ", "wltp range");
        double maxDcPowerKw = getValidatedDoubleInput("Enter maximum DC charging power (kW): ", "dc charging power");
        double maxAcPowerKw = getValidatedDoubleInput("Enter maximum AC charging power (kW): ", "ac charging power");
        
        BatteryType batteryType = selectBatteryType();
        double batteryCapacityKwh = getValidatedDoubleInput("Enter battery capacity (kWh): ", "battery capacity");
        double batteryDegradationPercent = getValidatedDoubleInput("Enter current battery degradation (%, press Enter for 0): ", "battery degradation");

        view.showConsumptionProfileSetup();
        
        double consumptionAt50Kmh = getValidatedDoubleInput("Enter consumption at 50 km/h (kWh/100km): ", "consumption");
        double consumptionAt100Kmh = getValidatedDoubleInput("Enter consumption at 100 km/h (kWh/100km): ", "consumption");
        double consumptionAt130Kmh = getValidatedDoubleInput("Enter consumption at 130 km/h (kWh/100km): ", "consumption");
        
        // Validate consumption progression
        if (!InputValidator.isValidConsumptionProgression(consumptionAt50Kmh, consumptionAt100Kmh, consumptionAt130Kmh)) {
            System.out.println(InputValidator.getValidationMessage("consumption progression", ""));
            return;
        }
        
        CreateCarProfileUseCaseInterface.CreateCarProfileCommand command = 
            new CreateCarProfileUseCaseInterface.CreateCarProfileCommand(
                name, manufacturer, model, year, hasHeatPump, wltpRangeKm,
                maxDcPowerKw, maxAcPowerKw, batteryType.name(),
                batteryCapacityKwh, batteryDegradationPercent,
                consumptionAt50Kmh, consumptionAt100Kmh, consumptionAt130Kmh
            );
        
        CarProfile carProfile = createCarProfileUseCase.createCarProfile(command);
        view.showProfileCreated(carProfile);
        
        showCarProfileMenu();
    }

    private String getValidatedInput(String prompt, String fieldName) {
        while (true) {
            view.showPrompt(prompt);
            String input = scanner.nextLine().trim();
            if (InputValidator.isValidName(input)) {
                return input;
            }
            System.out.println(InputValidator.getValidationMessage(fieldName, input));
        }
    }

    private int getValidatedIntInput(String prompt, String fieldName) {
        while (true) {
            view.showPrompt(prompt);
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                switch (fieldName) {
                    case "year":
                        if (InputValidator.isValidYear(input)) return input;
                        break;
                    case "battery type":
                        if (InputValidator.isValidBatteryTypeChoice(input)) return input;
                        break;
                    default:
                        return input;
                }
                System.out.println(InputValidator.getValidationMessage(fieldName, String.valueOf(input)));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private double getValidatedDoubleInput(String prompt, String fieldName) {
        while (true) {
            view.showPrompt(prompt);
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty() && fieldName.equals("battery degradation")) {
                    return 0.0;
                }
                double value = Double.parseDouble(input);
                switch (fieldName) {
                    case "battery capacity":
                        if (InputValidator.isValidBatteryCapacity(value)) return value;
                        break;
                    case "battery degradation":
                        if (InputValidator.isValidBatteryDegradation(value)) return value;
                        break;
                    case "dc charging power":
                        if (InputValidator.isValidDcChargingPower(value)) return value;
                        break;
                    case "ac charging power":
                        if (InputValidator.isValidAcChargingPower(value)) return value;
                        break;
                    case "consumption":
                        if (InputValidator.isValidConsumption(value)) return value;
                        break;
                    default:
                        return value;
                }
                System.out.println(InputValidator.getValidationMessage(fieldName, String.valueOf(value)));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private BatteryType selectBatteryType() {
        view.showBatteryTypes();
        int choice = getValidatedIntInput("Select battery type (1-3): ", "battery type");
        while (!InputValidator.isValidBatteryTypeChoice(choice)) {
            System.out.println("Invalid choice. Please select a number between 1 and " + BatteryType.values().length);
            choice = getValidatedIntInput("Select battery type (1-3): ", "battery type");
        }
        return BatteryType.values()[choice - 1];
    }

    private boolean getBooleanInput(String prompt) {
        while (true) {
            view.showPrompt(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) {
                return false;
            }
            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            }
            System.out.println("Invalid input. Please enter 'yes' or 'no': ");
        }
    }

    private void deleteCarProfile() {
        List<CarProfile> profiles = persistencePort.findAll();
        view.showAllCarProfiles(profiles);
        
        if (!profiles.isEmpty()) {
            int choice = getValidatedIntInput("Select profile to delete (1-" + profiles.size() + "): ", "profile selection");
            if (choice > 0 && choice <= profiles.size()) {
                CarProfile profileToDelete = profiles.get(choice - 1);
                persistencePort.delete(profileToDelete.getId());
                view.showProfileDeleted(profileToDelete);
            } else {
                view.showInvalidChoice();
            }
        }
        
        showCarProfileMenu();
    }

    private void editCarProfile() {
        List<CarProfile> profiles = persistencePort.findAll();
        view.showAllCarProfiles(profiles);
        
        if (!profiles.isEmpty()) {
            int choice = getValidatedIntInput("Select profile to edit (1-" + profiles.size() + "): ", "profile selection");
            if (choice > 0 && choice <= profiles.size()) {
                CarProfile profileToEdit = profiles.get(choice - 1);
                // TODO: Implement edit functionality
                view.showEditNotImplemented();
            } else {
                view.showInvalidChoice();
            }
        }
        
        showCarProfileMenu();
    }
} 