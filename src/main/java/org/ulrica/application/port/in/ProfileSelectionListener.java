package org.ulrica.application.port.in;

import org.ulrica.domain.entity.CarProfile;

public interface ProfileSelectionListener {
    void onProfileSelected(CarProfile profile);
} 