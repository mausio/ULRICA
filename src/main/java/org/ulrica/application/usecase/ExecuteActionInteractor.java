package org.ulrica.application.usecase;

import org.ulrica.application.port.in.ExecuteActionUseCaseInterface;
import org.ulrica.application.port.in.NavigationUseCaseInterface;
import org.ulrica.application.port.out.ActionResultOutputPortInterface;
import org.ulrica.domain.service.ProfileSelectionService;

public class ExecuteActionInteractor implements ExecuteActionUseCaseInterface {
    
    private final NavigationUseCaseInterface navigationUseCase;
    private final ActionResultOutputPortInterface actionResultOutputPort;
    private final ProfileSelectionService profileSelectionService;
    
    public ExecuteActionInteractor(
            NavigationUseCaseInterface navigationUseCase,
            ActionResultOutputPortInterface actionResultOutputPort,
            ProfileSelectionService profileSelectionService) {
        this.navigationUseCase = navigationUseCase;
        this.actionResultOutputPort = actionResultOutputPort;
        this.profileSelectionService = profileSelectionService;
    }
    
    @Override
    public boolean executeAction(int actionChoice) {
        if (profileSelectionService.getSelectedProfile() == null) {
            actionResultOutputPort.showError("No car profile selected");
            return false;
        }
        
        switch (actionChoice) {
            case 1:
                // DC Charging Calculator
                actionResultOutputPort.showNotImplemented("DC Charging Calculator");
                return true;
            case 2:
                // AC Charging Calculator
                actionResultOutputPort.showNotImplemented("AC Charging Calculator");
                return true;
            case 3:
                // Range Calculation
                actionResultOutputPort.showNotImplemented("Range Calculation");
                return true;
            case 4:
                // Back to main menu
                navigationUseCase.navigateToMainMenu();
                return true;
            default:
                return false;
        }
    }
} 