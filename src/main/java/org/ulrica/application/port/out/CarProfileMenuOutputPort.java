package org.ulrica.application.port.out;

import java.util.List;

import org.ulrica.domain.entity.CarProfile;

public interface CarProfileMenuOutputPort {
    void showCarProfileMenu();
    void showMenuOptions();
    void showPrompt();
    void showNoProfilesMessage();
    void showAllCarProfiles(List<CarProfile> profiles);
    void askToSelectProfile();
    void showProfileSelection(List<CarProfile> profiles);
    void showSelectedProfile(CarProfile profile);
    void showInvalidChoice();
    void showCreateProfileHeader();
    void showConsumptionProfileSetup();
    void showBatteryTypes();
    void showPrompt(String prompt);
    void showProfileCreated(CarProfile profile);
    void showProfileDeleted(CarProfile profile);
    void showEditNotImplemented();
} 