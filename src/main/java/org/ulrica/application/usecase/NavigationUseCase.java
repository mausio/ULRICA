package org.ulrica.application.usecase;

import org.ulrica.application.port.in.NavigationUseCaseInterface;
import org.ulrica.domain.ApplicationState;

public class NavigationUseCase implements NavigationUseCaseInterface {
    private ApplicationState currentState;
    
    public NavigationUseCase() {
        this.currentState = ApplicationState.WELCOME;
    }
    
    @Override
    public void navigateToCarProfileMenu() {
        this.currentState = ApplicationState.CAR_PROFILE_MENU;
    }
    
    @Override
    public void navigateToMainMenu() {
        this.currentState = ApplicationState.MAIN_MENU;
    }
    
    public void navigateToCreateCarProfile() {
        this.currentState = ApplicationState.CREATE_CAR_PROFILE;
    }
    
    public void navigateToEditCarProfile() {
        this.currentState = ApplicationState.EDIT_CAR_PROFILE;
    }
    
    public void navigateToDeleteCarProfile() {
        this.currentState = ApplicationState.DELETE_CAR_PROFILE;
    }
    
    public void exit() {
        this.currentState = ApplicationState.EXIT;
    }
    
    public ApplicationState getCurrentState() {
        return currentState;
    }
    
    public boolean shouldContinue() {
        return currentState != ApplicationState.EXIT;
    }
} 