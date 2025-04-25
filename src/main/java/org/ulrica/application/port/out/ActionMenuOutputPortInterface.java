package org.ulrica.application.port.out;

import org.ulrica.domain.entity.CarProfile;

public interface ActionMenuOutputPortInterface {

    void showActionMenuHeader();

    void showSelectedProfile(CarProfile profile);

    void displaySelectedProfile(String profileName);

    void showActionOptions();

    void showPrompt();

    void showInvalidChoice();

    void displayActionMenu();
} 