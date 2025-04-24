package org.ulrica.presentation.controller;

import java.util.List;
import java.util.Scanner;

import org.ulrica.application.port.in.CreateCarProfileUseCaseInterface;
import org.ulrica.application.port.out.CarProfilePersistencePortInterface;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.BatteryType;
import org.ulrica.presentation.view.CarProfileView;

public class CarProfileController {
    private final CreateCarProfileUseCaseInterface createCarProfileUseCase;
    private final CarProfilePersistencePortInterface persistencePort;
    private final CarProfileView view;
    private final Scanner scanner;

    public CarProfileController(
            CreateCarProfileUseCaseInterface createCarProfileUseCase,
            CarProfilePersistencePortInterface persistencePort,
            CarProfileView view) {
        this.createCarProfileUseCase = createCarProfileUseCase;
        this.persistencePort = persistencePort;
        this.view = view;
        this.scanner = new Scanner(System.in);
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
        view.showAllCarProfiles(profiles);
        
        view.askToSelectProfile();
        String response = scanner.nextLine().toLowerCase();
        if (response.equals("yes")) {
            selectCarProfile(profiles);
        }
        
        showCarProfileMenu();
    }

    private void selectCarProfile(List<CarProfile> profiles) {
        view.showProfileSelection(profiles);
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (choice > 0 && choice <= profiles.size()) {
            CarProfile selectedProfile = profiles.get(choice - 1);
            view.showSelectedProfile(selectedProfile);
        } else {
            view.showInvalidChoice();
        }
    }

    private void createNewCarProfile() {
        view.showCreateProfileHeader();
        
        String name = getInput("Enter profile name: ");
        String manufacturer = getInput("Enter manufacturer: ");
        String model = getInput("Enter model: ");
        int year = getIntInput("Enter build year: ");
        boolean hasHeatPump = getBooleanInput("Has heat pump? (yes/no): ");
        double wltpRangeKm = getDoubleInput("Enter WLTP range (km): ");
        double maxDcPowerKw = getDoubleInput("Enter maximum DC charging power (kW): ");
        double maxAcPowerKw = getDoubleInput("Enter maximum AC charging power (kW): ");
        
        BatteryType batteryType = selectBatteryType();
        double batteryCapacityKwh = getDoubleInput("Enter battery capacity (kWh): ");
        double batteryDegradationPercent = getDoubleInput("Enter current battery degradation (%, press Enter for 0): ");
        
        double consumptionAt50Kmh = getDoubleInput("Enter consumption at 50 km/h (kWh/100km): ");
        double consumptionAt100Kmh = getDoubleInput("Enter consumption at 100 km/h (kWh/100km): ");
        double consumptionAt130Kmh = getDoubleInput("Enter consumption at 130 km/h (kWh/100km): ");
        
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

    private BatteryType selectBatteryType() {
        view.showBatteryTypes();
        int choice = getIntInput("Select battery type (1-3): ");
        return BatteryType.values()[choice - 1];
    }

    private void deleteCarProfile() {
        List<CarProfile> profiles = persistencePort.findAll();
        view.showAllCarProfiles(profiles);
        
        if (!profiles.isEmpty()) {
            int choice = getIntInput("Select profile to delete (1-" + profiles.size() + "): ");
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
            int choice = getIntInput("Select profile to edit (1-" + profiles.size() + "): ");
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

    private String getInput(String prompt) {
        view.showPrompt(prompt);
        return scanner.nextLine();
    }

    private int getIntInput(String prompt) {
        view.showPrompt(prompt);
        return scanner.nextInt();
    }

    private double getDoubleInput(String prompt) {
        view.showPrompt(prompt);
        return scanner.nextDouble();
    }

    private boolean getBooleanInput(String prompt) {
        view.showPrompt(prompt);
        String response = scanner.nextLine().toLowerCase();
        return response.equals("yes") || response.equals("y");
    }
} 