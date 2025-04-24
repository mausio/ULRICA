package org.ulrica.application.controller;

import java.util.List;
import java.util.Scanner;

import org.ulrica.application.port.in.CreateCarProfileUseCaseInterface;
import org.ulrica.application.port.in.ShowCarProfileMenuUseCaseInterface;
import org.ulrica.application.port.in.ShowMainMenuUseCaseInterface;
import org.ulrica.application.port.in.ShowWelcomeUseCaseInterface;
import org.ulrica.application.port.out.CarProfilePersistencePortInterface;
import org.ulrica.application.usecase.CreateCarProfileInteractor;
import org.ulrica.application.usecase.ShowCarProfileMenuInteractor;
import org.ulrica.application.usecase.ShowMainMenuInteractor;
import org.ulrica.application.usecase.ShowWelcomeInteractor;
import org.ulrica.domain.ApplicationState;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.service.ProfileSelectionService;
import org.ulrica.domain.service.ProfileSelectionServiceImpl;
import org.ulrica.presentation.view.CarProfileView;
import org.ulrica.presentation.view.MainMenuView;
import org.ulrica.presentation.view.WelcomeView;

public class ApplicationController {
    private final CarProfilePersistencePortInterface repository;
    private final Scanner scanner;
    private ApplicationState currentState;
    private final ProfileSelectionService profileSelectionService;

    // Use Cases
    private final ShowWelcomeUseCaseInterface showWelcomeUseCase;
    private final ShowMainMenuUseCaseInterface showMainMenuUseCase;
    private final ShowCarProfileMenuUseCaseInterface showCarProfileMenuUseCase;
    private final CreateCarProfileUseCaseInterface createCarProfileUseCase;

    // Views
    private final WelcomeView welcomeView;
    private final MainMenuView mainMenuView;
    private final CarProfileView carProfileView;

    public ApplicationController(CarProfilePersistencePortInterface repository) {
        this.repository = repository;
        this.scanner = new Scanner(System.in);
        this.currentState = ApplicationState.WELCOME;
        this.profileSelectionService = new ProfileSelectionServiceImpl();

        // Initialize Use Cases
        this.showWelcomeUseCase = new ShowWelcomeInteractor();
        this.showMainMenuUseCase = new ShowMainMenuInteractor();
        this.showCarProfileMenuUseCase = new ShowCarProfileMenuInteractor();
        this.createCarProfileUseCase = new CreateCarProfileInteractor(repository);

        // Initialize Views
        this.welcomeView = new WelcomeView();
        this.mainMenuView = new MainMenuView();
        this.carProfileView = new CarProfileView();
    }

    public boolean shouldContinue() {
        return currentState != ApplicationState.EXIT;
    }

    public void processCurrentState() {
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
        }
    }

    private void handleWelcome() {
        showWelcomeUseCase.showWelcome(welcomeView);
        currentState = ApplicationState.MAIN_MENU;
    }

    private void handleMainMenu() {
        CarProfile selectedProfile = profileSelectionService.getSelectedProfile();
        if (selectedProfile != null) {
            mainMenuView.showProfileSelected(selectedProfile.getName());
        } else {
            mainMenuView.showNoProfileSelected();
        }
        showMainMenuUseCase.showMainMenu(mainMenuView);
        
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                currentState = ApplicationState.CAR_PROFILE_MENU;
                break;
            case 2:
                currentState = ApplicationState.EXIT;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void handleCarProfileMenu() {
        showCarProfileMenuUseCase.showCarProfileMenu(carProfileView);
        
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewAllCarProfiles();
                break;
            case 2:
                currentState = ApplicationState.CREATE_CAR_PROFILE;
                break;
            case 3:
                currentState = ApplicationState.DELETE_CAR_PROFILE;
                break;
            case 4:
                currentState = ApplicationState.EDIT_CAR_PROFILE;
                break;
            case 5:
                currentState = ApplicationState.MAIN_MENU;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void viewAllCarProfiles() {
        List<CarProfile> profiles = repository.findAll();
        if (profiles.isEmpty()) {
            carProfileView.showNoProfilesMessage();
            return;
        }
        carProfileView.showAllCarProfiles(profiles);
        
        carProfileView.askToSelectProfile();
        String response = scanner.nextLine().toLowerCase();
        if (response.equals("yes") || response.equals("y")) {
            selectCarProfile(profiles);
        }
    }

    private void selectCarProfile(List<CarProfile> profiles) {
        carProfileView.showProfileSelection(profiles);
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice > 0 && choice <= profiles.size()) {
            CarProfile selectedProfile = profiles.get(choice - 1);
            profileSelectionService.selectProfile(selectedProfile);
            carProfileView.showSelectedProfile(selectedProfile);
        } else {
            carProfileView.showInvalidChoice();
        }
    }

    private void handleCreateCarProfile() {
        // TODO: Implement create car profile logic
        currentState = ApplicationState.CAR_PROFILE_MENU;
    }

    private void handleEditCarProfile() {
        // TODO: Implement edit car profile logic
        currentState = ApplicationState.CAR_PROFILE_MENU;
    }

    private void handleDeleteCarProfile() {
        // TODO: Implement delete car profile logic
        currentState = ApplicationState.CAR_PROFILE_MENU;
    }
} 