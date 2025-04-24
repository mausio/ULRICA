package org.ulrica.application.usecase;

import org.ulrica.application.port.in.ShowActionMenuUseCaseInterface;
import org.ulrica.application.port.out.ActionMenuOutputPortInterface;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.service.ProfileSelectionService;

public class ShowActionMenuInteractor implements ShowActionMenuUseCaseInterface {
    
    private final ProfileSelectionService profileSelectionService;
    
    public ShowActionMenuInteractor(ProfileSelectionService profileSelectionService) {
        this.profileSelectionService = profileSelectionService;
    }
    
    @Override
    public void showActionMenu(ActionMenuOutputPortInterface actionMenuOutput) {
        CarProfile selectedProfile = profileSelectionService.getSelectedProfile();
        
        if (selectedProfile == null) {
            throw new IllegalStateException("Action menu cannot be shown without a selected car profile");
        }
        
        actionMenuOutput.showActionMenuHeader();
        actionMenuOutput.showSelectedProfile(selectedProfile);
        actionMenuOutput.showActionOptions();
        actionMenuOutput.showPrompt();
    }
} 