package org.ulrica.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ulrica.application.port.in.NavigationUseCaseInterface;
import org.ulrica.application.port.out.ActionResultOutputPortInterface;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.service.ProfileSelectionService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class ExecuteActionInteractorTest {

    @Mock
    private NavigationUseCaseInterface navigationUseCase;
    
    @Mock
    private ActionResultOutputPortInterface actionResultOutputPort;
    
    @Mock
    private ProfileSelectionService profileSelectionService;
    
    @Mock
    private CarProfile carProfile;
    
    private ExecuteActionInteractor executeActionInteractor;
    
    @BeforeEach
    public void setup() {
        executeActionInteractor = new ExecuteActionInteractor(
                navigationUseCase, 
                actionResultOutputPort, 
                profileSelectionService);
    }
    
    @Test
    public void testExecuteAction_NoSelectedProfile() {
        // Arrange
        when(profileSelectionService.getSelectedProfile()).thenReturn(null);
        
        // Act
        boolean result = executeActionInteractor.executeAction(1);
        
        // Assert
        assertFalse(result);
        verify(actionResultOutputPort).showError("No car profile selected");
    }
    
    @Test
    public void testExecuteAction_ValidOption1() {
        // Arrange
        when(profileSelectionService.getSelectedProfile()).thenReturn(carProfile);
        
        // Act
        boolean result = executeActionInteractor.executeAction(1);
        
        // Assert
        assertTrue(result);
        verify(actionResultOutputPort).showNotImplemented("DC Charging Calculator");
    }
    
    @Test
    public void testExecuteAction_ValidOption2() {
        // Arrange
        when(profileSelectionService.getSelectedProfile()).thenReturn(carProfile);
        
        // Act
        boolean result = executeActionInteractor.executeAction(2);
        
        // Assert
        assertTrue(result);
        verify(actionResultOutputPort).showNotImplemented("AC Charging Calculator");
    }
    
    @Test
    public void testExecuteAction_ValidOption3() {
        // Arrange
        when(profileSelectionService.getSelectedProfile()).thenReturn(carProfile);
        
        // Act
        boolean result = executeActionInteractor.executeAction(3);
        
        // Assert
        assertTrue(result);
        verify(actionResultOutputPort).showNotImplemented("Range Calculation");
    }
    
    @Test
    public void testExecuteAction_BackToMainMenu() {
        // Arrange
        when(profileSelectionService.getSelectedProfile()).thenReturn(carProfile);
        
        // Act
        boolean result = executeActionInteractor.executeAction(4);
        
        // Assert
        assertTrue(result);
        verify(navigationUseCase).navigateToMainMenu();
    }
    
    @Test
    public void testExecuteAction_InvalidOption() {
        // Arrange
        when(profileSelectionService.getSelectedProfile()).thenReturn(carProfile);
        
        // Act
        boolean result = executeActionInteractor.executeAction(99);
        
        // Assert
        assertFalse(result);
        verify(actionResultOutputPort, never()).showNotImplemented("DC Charging Calculator");
        verify(actionResultOutputPort, never()).showNotImplemented("AC Charging Calculator");
        verify(actionResultOutputPort, never()).showNotImplemented("Range Calculation");
        verify(navigationUseCase, never()).navigateToMainMenu();
    }
} 