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
    
    @Override
    public void navigateToCreateCarProfile() {
        this.currentState = ApplicationState.CREATE_CAR_PROFILE;
    }
    
    @Override
    public void navigateToEditCarProfile() {
        this.currentState = ApplicationState.EDIT_CAR_PROFILE;
    }
    
    @Override
    public void navigateToDeleteCarProfile() {
        this.currentState = ApplicationState.DELETE_CAR_PROFILE;
    }
    
    @Override
    public void navigateToActionMenu() {
        this.currentState = ApplicationState.ACTION_MENU;
    }
    
    @Override
    public void exit() {
        this.currentState = ApplicationState.EXIT;
    }
    
    @Override
    public ApplicationState getCurrentState() {
        return currentState;
    }
    
    @Override
    public boolean shouldContinue() {
        return currentState != ApplicationState.EXIT;
    }
} 