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
        // When initialized, application should be in WELCOME state
        assertEquals(ApplicationState.WELCOME, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testNavigateToMainMenu() {
        // Act
        navigationUseCase.navigateToMainMenu();
        
        // Assert
        assertEquals(ApplicationState.MAIN_MENU, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testNavigateToCarProfileMenu() {
        // Act
        navigationUseCase.navigateToCarProfileMenu();
        
        // Assert
        assertEquals(ApplicationState.CAR_PROFILE_MENU, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testNavigateToCreateCarProfile() {
        // Act
        navigationUseCase.navigateToCreateCarProfile();
        
        // Assert
        assertEquals(ApplicationState.CREATE_CAR_PROFILE, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testNavigateToEditCarProfile() {
        // Act
        navigationUseCase.navigateToEditCarProfile();
        
        // Assert
        assertEquals(ApplicationState.EDIT_CAR_PROFILE, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testNavigateToDeleteCarProfile() {
        // Act
        navigationUseCase.navigateToDeleteCarProfile();
        
        // Assert
        assertEquals(ApplicationState.DELETE_CAR_PROFILE, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testNavigateToActionMenu() {
        // Act
        navigationUseCase.navigateToActionMenu();
        
        // Assert
        assertEquals(ApplicationState.ACTION_MENU, navigationUseCase.getCurrentState());
        assertTrue(navigationUseCase.shouldContinue());
    }

    @Test
    public void testExit() {
        // Act
        navigationUseCase.exit();
        
        // Assert
        assertEquals(ApplicationState.EXIT, navigationUseCase.getCurrentState());
        assertFalse(navigationUseCase.shouldContinue());
    }
    
    @Test
    public void testNavigationSequence() {
        // Act & Assert - Test a sequence of navigation steps
        
        // Start in WELCOME state
        assertEquals(ApplicationState.WELCOME, navigationUseCase.getCurrentState());
        
        // Navigate to main menu
        navigationUseCase.navigateToMainMenu();
        assertEquals(ApplicationState.MAIN_MENU, navigationUseCase.getCurrentState());
        
        // Navigate to car profile menu
        navigationUseCase.navigateToCarProfileMenu();
        assertEquals(ApplicationState.CAR_PROFILE_MENU, navigationUseCase.getCurrentState());
        
        // Navigate to create car profile
        navigationUseCase.navigateToCreateCarProfile();
        assertEquals(ApplicationState.CREATE_CAR_PROFILE, navigationUseCase.getCurrentState());
        
        // Navigate back to car profile menu
        navigationUseCase.navigateToCarProfileMenu();
        assertEquals(ApplicationState.CAR_PROFILE_MENU, navigationUseCase.getCurrentState());
        
        // Navigate to main menu
        navigationUseCase.navigateToMainMenu();
        assertEquals(ApplicationState.MAIN_MENU, navigationUseCase.getCurrentState());
        
        // Navigate to action menu
        navigationUseCase.navigateToActionMenu();
        assertEquals(ApplicationState.ACTION_MENU, navigationUseCase.getCurrentState());
        
        // Navigate back to main menu
        navigationUseCase.navigateToMainMenu();
        assertEquals(ApplicationState.MAIN_MENU, navigationUseCase.getCurrentState());
        
        // Exit application
        navigationUseCase.exit();
        assertEquals(ApplicationState.EXIT, navigationUseCase.getCurrentState());
        assertFalse(navigationUseCase.shouldContinue());
    }
} 