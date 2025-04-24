package org.ulrica.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ulrica.domain.entity.CarProfile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActionAvailabilityServiceTest {

    private ProfileSelectionService profileSelectionService;
    private ActionAvailabilityService actionAvailabilityService;
    private CarProfile mockCarProfile;

    @BeforeEach
    public void setup() {
        profileSelectionService = mock(ProfileSelectionService.class);
        actionAvailabilityService = new ActionAvailabilityServiceImpl(profileSelectionService);
        mockCarProfile = mock(CarProfile.class);
    }

    @Test
    public void areActionsAvailable_withSelectedProfile_returnsTrue() {
        // Arrange
        when(profileSelectionService.getSelectedProfile()).thenReturn(mockCarProfile);
        
        // Act
        boolean result = actionAvailabilityService.areActionsAvailable();
        
        // Assert
        assertTrue(result);
    }

    @Test
    public void areActionsAvailable_withNoSelectedProfile_returnsFalse() {
        // Arrange
        when(profileSelectionService.getSelectedProfile()).thenReturn(null);
        
        // Act
        boolean result = actionAvailabilityService.areActionsAvailable();
        
        // Assert
        assertFalse(result);
    }
} 