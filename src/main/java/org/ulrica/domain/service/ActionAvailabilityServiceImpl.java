package org.ulrica.domain.service;

import org.ulrica.domain.entity.CarProfile;

public class ActionAvailabilityServiceImpl implements ActionAvailabilityService {
    
    private final ProfileSelectionService profileSelectionService;
    
    public ActionAvailabilityServiceImpl(ProfileSelectionService profileSelectionService) {
        this.profileSelectionService = profileSelectionService;
    }
    
    @Override
    public boolean areActionsAvailable() {
        CarProfile selectedProfile = profileSelectionService.getSelectedProfile();
        return selectedProfile != null;
    }
} 