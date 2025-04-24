package org.ulrica.application.usecase;

import org.ulrica.application.port.in.ExecuteActionUseCaseInterface;
import org.ulrica.application.port.in.NavigationUseCaseInterface;
import org.ulrica.application.port.out.ActionResultOutputPortInterface;
import org.ulrica.domain.service.ProfileSelectionService;
import org.ulrica.presentation.controller.AcChargingController;
import org.ulrica.presentation.controller.DcChargingController;

public class ExecuteActionInteractor implements ExecuteActionUseCaseInterface {
    
    private final NavigationUseCaseInterface navigationUseCase;
    private final ActionResultOutputPortInterface actionResultOutputPort;
    private final ProfileSelectionService profileSelectionService;
    private final DcChargingController dcChargingController;
    private final AcChargingController acChargingController;
    
    public ExecuteActionInteractor(
            NavigationUseCaseInterface navigationUseCase,
            ActionResultOutputPortInterface actionResultOutputPort,
            ProfileSelectionService profileSelectionService,
            DcChargingController dcChargingController,
            AcChargingController acChargingController) {
        this.navigationUseCase = navigationUseCase;
        this.actionResultOutputPort = actionResultOutputPort;
        this.profileSelectionService = profileSelectionService;
        this.dcChargingController = dcChargingController;
        this.acChargingController = acChargingController;
    }
    
    @Override
    public boolean executeAction(int actionChoice) {
        if (profileSelectionService.getSelectedProfile() == null) {
            actionResultOutputPort.showError("No car profile selected");
            return false;
        }
        
        switch (actionChoice) {
            case 1:
                return dcChargingController.processDcChargingCalculation();
            case 2:
                return acChargingController.processAcChargingCalculation();
            case 3:
                // TODO: Range Calculation
                actionResultOutputPort.showNotImplemented("Range Calculation");
                return true;
            case 4:
                navigationUseCase.navigateToMainMenu();
                return true;
            default:
                return false;
        }
    }
} 