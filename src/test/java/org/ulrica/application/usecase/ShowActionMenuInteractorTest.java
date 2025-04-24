package org.ulrica.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.service.ProfileSelectionService;
import org.ulrica.presentation.view.ActionMenuView;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShowActionMenuInteractorTest {

    @Mock
    private ProfileSelectionService profileSelectionService;
    
    @Mock
    private ActionMenuView actionMenuView;
    
    @Mock
    private CarProfile carProfile;
    
    private ShowActionMenuInteractor showActionMenuInteractor;
    
    @BeforeEach
    public void setup() {
        showActionMenuInteractor = new ShowActionMenuInteractor(profileSelectionService);
    }
    
    @Test
    public void testShowActionMenu_WithSelectedProfile() {
        // Arrange
        when(profileSelectionService.getSelectedProfile()).thenReturn(carProfile);
        
        // Act
        showActionMenuInteractor.showActionMenu(actionMenuView);
        
        // Assert
        verify(actionMenuView).showActionMenuHeader();
        verify(actionMenuView).showSelectedProfile(carProfile);
        verify(actionMenuView).showActionOptions();
        verify(actionMenuView).showPrompt();
    }
    
    @Test
    public void testShowActionMenu_NoSelectedProfile() {
        // Arrange
        when(profileSelectionService.getSelectedProfile()).thenReturn(null);
        
        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            showActionMenuInteractor.showActionMenu(actionMenuView);
        });
    }
} 