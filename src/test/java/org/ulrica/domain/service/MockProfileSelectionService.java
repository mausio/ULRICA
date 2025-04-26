package org.ulrica.domain.service;

import org.ulrica.domain.entity.CarProfile;

public class MockProfileSelectionService implements ProfileSelectionService {
    
    private CarProfile selectedProfile;
    private int selectionCount = 0;
    private int clearCount = 0;
    
    public MockProfileSelectionService() {
        this.selectedProfile = null;
    }
    
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
    
    public int getSelectionCount() {
        return selectionCount;
    }
    
    public int getClearCount() {
        return clearCount;
    }
    
    public boolean hasSelectedProfile() {
        return selectedProfile != null;
    }
} 