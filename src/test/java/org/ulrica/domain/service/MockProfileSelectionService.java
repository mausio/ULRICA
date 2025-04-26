package org.ulrica.domain.service;

import org.ulrica.domain.entity.CarProfile;

/**
 * A mock implementation of ProfileSelectionService for testing.
 * This allows us to control the behavior of the service in unit tests.
 */
public class MockProfileSelectionService implements ProfileSelectionService {
    
    private CarProfile selectedProfile;
    private int selectionCount = 0;
    private int clearCount = 0;
    
    /**
     * Create a new instance with no selected profile
     */
    public MockProfileSelectionService() {
        this.selectedProfile = null;
    }
    
    /**
     * Create a new instance with a pre-selected profile
     * @param initialProfile The profile to start with
     */
    public MockProfileSelectionService(CarProfile initialProfile) {
        this.selectedProfile = initialProfile;
    }

    @Override
    public CarProfile getSelectedProfile() {
        return selectedProfile;
    }

    @Override
    public void selectProfile(CarProfile profile) {
        this.selectedProfile = profile;
        this.selectionCount++;
    }

    @Override
    public void clearSelection() {
        this.selectedProfile = null;
        this.clearCount++;
    }
    
    /**
     * Get the number of times a profile was selected
     * @return The count of profile selections
     */
    public int getSelectionCount() {
        return selectionCount;
    }
    
    /**
     * Get the number of times the selection was cleared
     * @return The count of selection clears
     */
    public int getClearCount() {
        return clearCount;
    }
    
    /**
     * Check if a profile is currently selected
     * @return true if a profile is selected, false otherwise
     */
    public boolean hasSelectedProfile() {
        return selectedProfile != null;
    }
} 