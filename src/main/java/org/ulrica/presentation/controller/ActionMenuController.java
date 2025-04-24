package org.ulrica.presentation.controller;

import org.ulrica.application.port.in.ExecuteActionUseCaseInterface;
import org.ulrica.application.port.in.ShowActionMenuUseCaseInterface;
import org.ulrica.application.port.out.ActionMenuOutputPortInterface;
import org.ulrica.application.port.out.UserInputPortInterface;
import org.ulrica.application.port.out.UserOutputPortInterface;
import org.ulrica.domain.service.ActionAvailabilityService;
import org.ulrica.presentation.view.ActionMenuView;
import org.ulrica.presentation.view.ActionResultView;


public class ActionMenuController {
    
    private final UserInputPortInterface userInputPort;
    private final UserOutputPortInterface userOutputPort;
    private final ShowActionMenuUseCaseInterface showActionMenuUseCase;
    private final ExecuteActionUseCaseInterface executeActionUseCase;
    private final ActionAvailabilityService actionAvailabilityService;
    private final ActionMenuOutputPortInterface actionMenuOutput;
    private final ActionResultView actionResultView;
    
    public ActionMenuController(
            UserInputPortInterface userInputPort,
            UserOutputPortInterface userOutputPort,
            ShowActionMenuUseCaseInterface showActionMenuUseCase,
            ExecuteActionUseCaseInterface executeActionUseCase,
            ActionAvailabilityService actionAvailabilityService) {
        this.userInputPort = userInputPort;
        this.userOutputPort = userOutputPort;
        this.showActionMenuUseCase = showActionMenuUseCase;
        this.executeActionUseCase = executeActionUseCase;
        this.actionAvailabilityService = actionAvailabilityService;
        this.actionMenuOutput = new ActionMenuView(userOutputPort);
        this.actionResultView = new ActionResultView(userOutputPort);
    }
    
    /**
     * Handles the action menu display and interaction.
     * 
     * @return true if an action was executed successfully, false otherwise
     */
    public boolean processActionMenu() {
        if (!actionAvailabilityService.areActionsAvailable()) {
            actionResultView.showError("No car profile selected. Please select a profile first.");
            return false;
        }
        
        try {
            showActionMenuUseCase.showActionMenu(actionMenuOutput);
            int choice = userInputPort.readInt();
            
            boolean actionExecuted = executeActionUseCase.executeAction(choice);
            if (!actionExecuted) {
                actionMenuOutput.showInvalidChoice();
            }
            
            return actionExecuted;
        } catch (Exception e) {
            actionResultView.showError("An error occurred: " + e.getMessage());
            return false;
        }
    }
} 