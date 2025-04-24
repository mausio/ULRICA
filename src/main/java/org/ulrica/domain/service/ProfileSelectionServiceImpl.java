package org.ulrica.domain.service;

import org.ulrica.domain.entity.CarProfile;

public class ProfileSelectionServiceImpl implements ProfileSelectionService {
    private CarProfile selectedProfile;

    @Override
    public void selectProfile(CarProfile profile) {
        this.selectedProfile = profile;
    }

    @Override
    public CarProfile getSelectedProfile() {
        return selectedProfile;
    }

    @Override
    public void clearSelection() {
        this.selectedProfile = null;
    }
} 