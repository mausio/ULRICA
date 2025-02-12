package controller;

import java.util.Scanner;

import model.CarProfile;
import model.state.ApplicationState;
import repository.CarProfileRepository;
import service.ActionService;
import service.CarProfileService;
import service.MainMenuService;

public class ApplicationController {
    private final CarProfileService carProfileService;
    private final MainMenuService mainMenuService;
    private ApplicationState currentState;
    private CarProfile selectedProfile;

    public ApplicationController(CarProfileRepository repository) {
        this.carProfileService = new CarProfileService(repository);
        this.mainMenuService = new MainMenuService(carProfileService);
        this.currentState = ApplicationState.MAIN_MENU;
    }

    public void processCurrentState() {
        switch (currentState) {
            case MAIN_MENU:
                handleMainMenu();
                break;
            case PROFILE_MANAGEMENT:
                handleProfileManagement();
                break;
            case ACTION_MENU:
                if (selectedProfile == null) {
                    System.out.println("\nPlease select a car profile first!");
                    waitForEnter();
                    currentState = ApplicationState.MAIN_MENU;
                } else {
                    handleActionMenu();
                }
                break;
            case EXIT:
                break;
        }
    }

    private void handleMainMenu() {
        currentState = mainMenuService.showMenu();
    }

    private void handleProfileManagement() {
        carProfileService.showMenu();
        selectedProfile = carProfileService.getSelectedProfile();
        currentState = ApplicationState.MAIN_MENU;
    }

    private void handleActionMenu() {
        ActionService actionService = new ActionService(selectedProfile);
        actionService.showMenu();
        currentState = ApplicationState.MAIN_MENU;
    }

    private void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
        new Scanner(System.in).nextLine();
    }

    public boolean shouldContinue() {
        return currentState != ApplicationState.EXIT;
    }
} 