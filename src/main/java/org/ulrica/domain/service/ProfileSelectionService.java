package org.ulrica.domain.service;

import org.ulrica.domain.entity.CarProfile;

public interface ProfileSelectionService {
    void selectProfile(CarProfile profile);
    CarProfile getSelectedProfile();
    void clearSelection();
} 