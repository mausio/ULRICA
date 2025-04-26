package org.ulrica.application.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.ulrica.domain.ApplicationState;

public class NavigationUseCaseTest {

    private NavigationUseCase navigationUseCase;

    @Before
    public void setUp() {
        navigationUseCase = new NavigationUseCase();
    }

    @Test
    public void testInitialState() {
        
        assertEquals(ApplicationState.WELCOME, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testNavigateToMainMenu() {
        
        navigationUseCase.navigateToMainMenu();
        
        
        assertEquals(ApplicationState.MAIN_MENU, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testNavigateToCarProfileMenu() {
        
        navigationUseCase.navigateToCarProfileMenu();
        
        
        assertEquals(ApplicationState.CAR_PROFILE_MENU, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testNavigateToCreateCarProfile() {
        
        navigationUseCase.navigateToCreateCarProfile();
        
        
        assertEquals(ApplicationState.CREATE_CAR_PROFILE, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testNavigateToEditCarProfile() {
        
        navigationUseCase.navigateToEditCarProfile();
        
        
        assertEquals(ApplicationState.EDIT_CAR_PROFILE, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testNavigateToDeleteCarProfile() {
        
        navigationUseCase.navigateToDeleteCarProfile();
        
        
        assertEquals(ApplicationState.DELETE_CAR_PROFILE, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testNavigateToActionMenu() {
        
        navigationUseCase.navigateToActionMenu();
        
        
        assertEquals(ApplicationState.ACTION_MENU, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testExit() {
        
        navigationUseCase.exit();
        
        
        assertEquals(ApplicationState.EXIT, navigationUseCase.getCurrentState());
        assertFalse(navigationUseCase.shouldContinue());
    }
    
    @Test
    public void testNavigationSequence() {
        
        
        
        assertEquals(ApplicationState.WELCOME, navigationUseCase.getCurrentState());
        
        
        navigationUseCase.navigateToMainMenu();
        assertEquals(ApplicationState.MAIN_MENU, navigationUseCase.getCurrentState());
        
        
        navigationUseCase.navigateToCarProfileMenu();
        assertEquals(ApplicationState.CAR_PROFILE_MENU, navigationUseCase.getCurrentState());
        
        
        navigationUseCase.navigateToCreateCarProfile();
        assertEquals(ApplicationState.CREATE_CAR_PROFILE, navigationUseCase.getCurrentState());
        
        
        navigationUseCase.navigateToCarProfileMenu();
        assertEquals(ApplicationState.CAR_PROFILE_MENU, navigationUseCase.getCurrentState());
        
        
        navigationUseCase.navigateToMainMenu();
        assertEquals(ApplicationState.MAIN_MENU, navigationUseCase.getCurrentState());
        
        
        navigationUseCase.navigateToActionMenu();
        assertEquals(ApplicationState.ACTION_MENU, navigationUseCase.getCurrentState());
        
        
        navigationUseCase.navigateToMainMenu();
        assertEquals(ApplicationState.MAIN_MENU, navigationUseCase.getCurrentState());
        
        
        navigationUseCase.exit();
        assertEquals(ApplicationState.EXIT, navigationUseCase.getCurrentState());
        assertFalse(navigationUseCase.shouldContinue());
    }
} 