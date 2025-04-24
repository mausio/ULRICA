package org.ulrica.presentation.controller;

import java.util.List;

import org.ulrica.application.port.in.CreateCarProfileUseCaseInterface;
import org.ulrica.application.port.in.ExecuteActionUseCaseInterface;
import org.ulrica.application.port.in.InputValidationServiceInterface;
import org.ulrica.application.port.in.NavigationUseCaseInterface;
import org.ulrica.application.port.in.ShowActionMenuUseCaseInterface;
import org.ulrica.application.port.in.ShowCarProfileMenuUseCaseInterface;
import org.ulrica.application.port.in.ShowMainMenuUseCaseInterface;
import org.ulrica.application.port.in.ShowWelcomeUseCaseInterface;
import org.ulrica.application.port.out.CarProfilePersistencePortInterface;
import org.ulrica.application.port.out.UserInputPortInterface;
import org.ulrica.application.port.out.UserOutputPortInterface;
import org.ulrica.application.service.UserInputService;
import org.ulrica.domain.ApplicationState;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.service.ActionAvailabilityService;
import org.ulrica.domain.service.ProfileSelectionService;
import org.ulrica.presentation.view.ActionMenuView;
import org.ulrica.presentation.view.ActionResultView;
import org.ulrica.presentation.view.CarProfileView;
import org.ulrica.presentation.view.MainMenuView;
import org.ulrica.presentation.view.WelcomeView;

/**
 * Enhanced ApplicationController with support for ActionMenu module.
 * Follows Clean Architecture principles with Controller as part of presentation layer.
 */
public class ApplicationControllerWithActionMenu {
    private final CarProfilePersistencePortInterface repository;
    private final NavigationUseCaseInterface navigationUseCase;
    private final ProfileSelectionService profileSelectionService;
    private final UserInputService userInputService;
    private final ActionAvailabilityService actionAvailabilityService;

    // Use Cases
    private final ShowWelcomeUseCaseInterface showWelcomeUseCase;
    private final ShowMainMenuUseCaseInterface showMainMenuUseCase;
    private final ShowCarProfileMenuUseCaseInterface showCarProfileMenuUseCase;
    private final ShowActionMenuUseCaseInterface showActionMenuUseCase;
    private final CreateCarProfileUseCaseInterface createCarProfileUseCase;
    private final ExecuteActionUseCaseInterface executeActionUseCase;

    // Views
    private final WelcomeView welcomeView;
    private final MainMenuView mainMenuView;
    private final CarProfileView carProfileView;
    private final ActionMenuView actionMenuView;
    private final ActionResultView actionResultView;
    
    // IO 
    private final UserInputPortInterface userInputPort;
    private final UserOutputPortInterface userOutputPort;
    
    // Controllers
    private final ActionMenuController actionMenuController;

    public ApplicationControllerWithActionMenu(
            UserInputPortInterface userInputPort,
            UserOutputPortInterface userOutputPort,
            CarProfilePersistencePortInterface repository,
            NavigationUseCaseInterface navigationUseCase,
            ProfileSelectionService profileSelectionService,
            ActionAvailabilityService actionAvailabilityService,
            ShowWelcomeUseCaseInterface showWelcomeUseCase,
            ShowMainMenuUseCaseInterface showMainMenuUseCase,
            ShowCarProfileMenuUseCaseInterface showCarProfileMenuUseCase,
            ShowActionMenuUseCaseInterface showActionMenuUseCase,
            CreateCarProfileUseCaseInterface createCarProfileUseCase,
            ExecuteActionUseCaseInterface executeActionUseCase,
            InputValidationServiceInterface validationService,
            WelcomeView welcomeView,
            MainMenuView mainMenuView,
            CarProfileView carProfileView,
            ActionMenuView actionMenuView,
            ActionResultView actionResultView) {
        this.userInputPort = userInputPort;
        this.userOutputPort = userOutputPort;
        this.repository = repository;
        this.navigationUseCase = navigationUseCase;
        this.profileSelectionService = profileSelectionService;
        this.actionAvailabilityService = actionAvailabilityService;
        this.showWelcomeUseCase = showWelcomeUseCase;
        this.showMainMenuUseCase = showMainMenuUseCase;
        this.showCarProfileMenuUseCase = showCarProfileMenuUseCase;
        this.showActionMenuUseCase = showActionMenuUseCase;
        this.createCarProfileUseCase = createCarProfileUseCase;
        this.executeActionUseCase = executeActionUseCase;
        this.welcomeView = welcomeView;
        this.mainMenuView = mainMenuView;
        this.carProfileView = carProfileView;
        this.actionMenuView = actionMenuView;
        this.actionResultView = actionResultView;
        this.userInputService = new UserInputService(userInputPort, userOutputPort, validationService);
        
        this.actionMenuController = new ActionMenuController(
                userInputPort, 
                userOutputPort,
                showActionMenuUseCase, 
                executeActionUseCase, 
                actionAvailabilityService);
    }

    public void processCurrentState() {
        ApplicationState currentState = navigationUseCase.getCurrentState();
        
        switch(currentState) {
            case WELCOME:
                handleWelcome();
                break;
            case MAIN_MENU:
                handleMainMenu();
                break;
            case CAR_PROFILE_MENU:
                handleCarProfileMenu();
                break;
            case CREATE_CAR_PROFILE:
                handleCreateCarProfile();
                break;
            case EDIT_CAR_PROFILE:
                handleEditCarProfile();
                break;
            case DELETE_CAR_PROFILE:
                handleDeleteCarProfile();
                break;
            case ACTION_MENU:
                handleActionMenu();
                break;
            default:
                break;
        }
    }

    private void handleWelcome() {
        showWelcomeUseCase.showWelcome(welcomeView);
        navigationUseCase.navigateToMainMenu();
    }

    private void handleMainMenu() {
        CarProfile selectedProfile = profileSelectionService.getSelectedProfile();
        
        mainMenuView.showMainMenu();
        if (selectedProfile != null) {
            mainMenuView.showProfileSelected(selectedProfile.getName());
        } else {
            mainMenuView.showNoProfileSelected();
        }
        mainMenuView.showMenuOptions();
        mainMenuView.showPrompt();
        
        int choice = userInputPort.readInt();

        switch (choice) {
            case 1:
                navigationUseCase.navigateToCarProfileMenu();
                break;
            case 2:
                if (actionAvailabilityService.areActionsAvailable()) {
                    navigationUseCase.navigateToActionMenu();
                } else {
                    userOutputPort.displayLine("No car profile selected. Please select a profile first.");
                    userInputPort.readLine(); // Wait for user to press Enter
                }
                break;
            case 3:
                navigationUseCase.exit();
                break;
            default:
                userOutputPort.displayLine("Invalid choice. Please try again.");
        }
    }

    private void handleCarProfileMenu() {
        showCarProfileMenuUseCase.showCarProfileMenu(carProfileView);
        
        int choice = userInputPort.readInt();

        switch (choice) {
            case 1:
                viewAllCarProfiles();
                break;
            case 2:
                navigationUseCase.navigateToCreateCarProfile();
                break;
            case 3:
                navigationUseCase.navigateToDeleteCarProfile();
                break;
            case 4:
                navigationUseCase.navigateToEditCarProfile();
                break;
            case 5:
                navigationUseCase.navigateToMainMenu();
                break;
            default:
                userOutputPort.displayLine("Invalid choice. Please try again.");
        }
    }
    
    private void handleActionMenu() {
        actionMenuController.processActionMenu();
        userInputPort.readLine(); // Wait for user to press Enter
        navigationUseCase.navigateToMainMenu();
    }

    private void viewAllCarProfiles() {
        List<CarProfile> profiles = repository.findAll();
        if (profiles.isEmpty()) {
            carProfileView.showNoProfilesMessage();
            return;
        }
        carProfileView.showAllCarProfiles(profiles);
        
        carProfileView.askToSelectProfile();
        String response = userInputPort.readLine().toLowerCase();
        if (response.equals("yes") || response.equals("y")) {
            selectCarProfile(profiles);
        }
    }

    private void selectCarProfile(List<CarProfile> profiles) {
        carProfileView.showProfileSelection(profiles);
        int choice = userInputPort.readInt();
        
        if (choice > 0 && choice <= profiles.size()) {
            CarProfile selectedProfile = profiles.get(choice - 1);
            profileSelectionService.selectProfile(selectedProfile);
            carProfileView.showSelectedProfile(selectedProfile);
        } else {
            carProfileView.showInvalidChoice();
        }
    }

    private void handleCreateCarProfile() {
        carProfileView.showCreateProfileHeader();
        
        String name = userInputService.getValidatedTextInput("Enter profile name: ", "name");
        String manufacturer = userInputService.getValidatedTextInput("Enter manufacturer: ", "manufacturer");
        String model = userInputService.getValidatedTextInput("Enter model: ", "model");
        int year = userInputService.getValidatedIntInput("Enter build year: ", "year");
        boolean hasHeatPump = userInputService.getBooleanInput("Has heat pump? (yes/no): ");
        double wltpRangeKm = userInputService.getValidatedDoubleInput("Enter WLTP range (km): ", "wltp range");
        double maxDcPowerKw = userInputService.getValidatedDoubleInput("Enter maximum DC charging power (kW): ", "dc charging power");
        double maxAcPowerKw = userInputService.getValidatedDoubleInput("Enter maximum AC charging power (kW): ", "ac charging power");
        
        org.ulrica.domain.valueobject.BatteryType batteryType = userInputService.selectBatteryType();
        double batteryCapacityKwh = userInputService.getValidatedDoubleInput("Enter battery capacity (kWh): ", "battery capacity");
        double batteryDegradationPercent = userInputService.getValidatedDoubleInput("Enter current battery degradation (%): ", "battery degradation");

        carProfileView.showConsumptionProfileSetup();
        
        double consumptionAt50Kmh = userInputService.getValidatedDoubleInput("Enter consumption at 50 km/h (kWh/100km): ", "consumption");
        double consumptionAt100Kmh = userInputService.getValidatedDoubleInput("Enter consumption at 100 km/h (kWh/100km): ", "consumption");
        double consumptionAt130Kmh = userInputService.getValidatedDoubleInput("Enter consumption at 130 km/h (kWh/100km): ", "consumption");
        
        CreateCarProfileUseCaseInterface.CreateCarProfileCommand command = 
            new CreateCarProfileUseCaseInterface.CreateCarProfileCommand(
                name, manufacturer, model, year, hasHeatPump, wltpRangeKm,
                maxDcPowerKw, maxAcPowerKw, batteryType.name(),
                batteryCapacityKwh, batteryDegradationPercent,
                consumptionAt50Kmh, consumptionAt100Kmh, consumptionAt130Kmh
            );
        
        CarProfile carProfile = createCarProfileUseCase.createCarProfile(command);
        carProfileView.showProfileCreated(carProfile);
        userInputPort.readLine(); // Wait for user to press Enter
        
        navigationUseCase.navigateToCarProfileMenu();
    }

    private void handleEditCarProfile() {
        List<CarProfile> profiles = repository.findAll();
        if (profiles.isEmpty()) {
            carProfileView.showNoProfilesMessage();
            navigationUseCase.navigateToCarProfileMenu();
            return;
        }
        
        carProfileView.showAllCarProfiles(profiles);
        int choice = userInputService.getValidatedIntInput("Select profile to edit (1-" + profiles.size() + "): ", "profile selection");
        
        if (choice > 0 && choice <= profiles.size()) {
            // For now, just show the edit not implemented message
            carProfileView.showEditNotImplemented();
        } else {
            carProfileView.showInvalidChoice();
        }
        
        navigationUseCase.navigateToCarProfileMenu();
    }

    private void handleDeleteCarProfile() {
        List<CarProfile> profiles = repository.findAll();
        if (profiles.isEmpty()) {
            carProfileView.showNoProfilesMessage();
            navigationUseCase.navigateToCarProfileMenu();
            return;
        }
        
        carProfileView.showAllCarProfiles(profiles);
        int choice = userInputService.getValidatedIntInput("Select profile to delete (1-" + profiles.size() + "): ", "profile selection");
        
        if (choice > 0 && choice <= profiles.size()) {
            CarProfile profileToDelete = profiles.get(choice - 1);
            repository.delete(profileToDelete.getId());
            carProfileView.showProfileDeleted(profileToDelete);
            
            // If the deleted profile was selected, clear the selection
            if (profileSelectionService.getSelectedProfile() != null && 
                profileSelectionService.getSelectedProfile().getId().equals(profileToDelete.getId())) {
                profileSelectionService.clearSelection();
            }
        } else {
            carProfileView.showInvalidChoice();
        }
        
        navigationUseCase.navigateToCarProfileMenu();
    }
} 