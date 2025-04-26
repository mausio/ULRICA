package org.ulrica;

import org.ulrica.application.port.in.*;
import org.ulrica.application.port.out.*;
import org.ulrica.application.service.InputValidationService;
import org.ulrica.application.usecase.*;
import org.ulrica.domain.service.*;
import org.ulrica.infrastructure.adapter.ConsoleUserInputAdapter;
import org.ulrica.infrastructure.adapter.ConsoleUserOutputAdapter;
import org.ulrica.infrastructure.persistence.JsonCarProfileRepository;
import org.ulrica.presentation.controller.AcChargingController;
import org.ulrica.presentation.controller.ApplicationControllerWithActionMenu;
import org.ulrica.presentation.controller.DcChargingController;
import org.ulrica.presentation.controller.RangeCalculationController;
import org.ulrica.presentation.view.*;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    // Infrastructure Layer - Scanner, UserInputPortInterface, UserOutputPortInterface, CarProfilePersistencePortInterface für die Eingabe und Ausgabe
    Scanner scanner = new Scanner(System.in);
    UserInputPortInterface userInputPort = new ConsoleUserInputAdapter(
        scanner);
    UserOutputPortInterface userOutputPort = new ConsoleUserOutputAdapter();
    CarProfilePersistencePortInterface repository = new JsonCarProfileRepository();
    
    // Domain Layer: Services
    ProfileSelectionService profileSelectionService = new ProfileSelectionServiceImpl();
    ActionAvailabilityService actionAvailabilityService = new ActionAvailabilityServiceImpl(
        profileSelectionService);
    DcChargingCalculator dcChargingCalculator = new DcChargingCalculator();
    AcChargingCalculator acChargingCalculator = new AcChargingCalculator();
    RangeCalculatorService rangeCalculatorService = new RangeCalculatorService();
    
    
    // Presentation Layey: Alle Views
    WelcomeView welcomeView = new WelcomeView();
    MainMenuView mainMenuView = new MainMenuView(userOutputPort);
    CarProfileView carProfileView = new CarProfileView();
    ActionResultView actionResultView = new ActionResultView(
        userOutputPort);
    DcChargingOutputPortInterface dcChargingView = new DcChargingView(
        userOutputPort);
    AcChargingOutputPortInterface acChargingView = new AcChargingView(
        userOutputPort,
        acChargingCalculator);
    RangeCalculationOutputPortInterface rangeCalculationView = new RangeCalculationView(
        userOutputPort);
    
    // Application Layer - Use Cases
    CalculateDcChargingUseCaseInterface calculateDcChargingUseCase = new CalculateDcChargingInteractor(
        profileSelectionService,
        dcChargingCalculator,
        dcChargingView);
    CalculateAcChargingUseCaseInterface calculateAcChargingUseCase = new CalculateAcChargingInteractor(
        profileSelectionService,
        acChargingCalculator,
        acChargingView);
    NavigationUseCaseInterface navigationUseCase = new NavigationUseCase();
    ShowWelcomeUseCaseInterface showWelcomeUseCase = new ShowWelcomeInteractor();
    ShowCarProfileMenuUseCaseInterface showCarProfileMenuUseCase = new ShowCarProfileMenuInteractor();
    ShowActionMenuUseCaseInterface showActionMenuUseCase = new ShowActionMenuInteractor(
        profileSelectionService);
    CreateCarProfileUseCaseInterface createCarProfileUseCase = new CreateCarProfileInteractor(
        repository);
    InputValidationServiceInterface validationService = new InputValidationService();
    
    // Presentation Layer: Controllers
    DcChargingController dcChargingController = new DcChargingController(
        userInputPort,
        userOutputPort,
        calculateDcChargingUseCase,
        dcChargingView);
    AcChargingController acChargingController = new AcChargingController(
        userInputPort,
        userOutputPort,
        calculateAcChargingUseCase,
        acChargingView);
    CalculateRangeUseCaseInterface calculateRangeUseCase = new CalculateRangeInteractor(
        rangeCalculatorService,
        rangeCalculationView,
        profileSelectionService);
    RangeCalculationController rangeCalculationController = new RangeCalculationController(
        calculateRangeUseCase,
        userInputPort,
        rangeCalculationView);
    
    // Action Interactor mit korrektem Output-Port
    ExecuteActionUseCaseInterface executeActionUseCase = new ExecuteActionInteractor(
        navigationUseCase,
        actionResultView,
        profileSelectionService,
        dcChargingController,
        acChargingController,
        rangeCalculationController);
    
    // Presentation Layer: ApplicationControllerWithActionMenu (eine Erweiterung von ApplicationController, der gelöscht wurde) mit Action-Menu-Unterstützung
    ApplicationControllerWithActionMenu applicationController = new ApplicationControllerWithActionMenu(
        userInputPort,
        userOutputPort,
        repository,
        navigationUseCase,
        profileSelectionService,
        actionAvailabilityService,
        showWelcomeUseCase,
        showCarProfileMenuUseCase,
        showActionMenuUseCase,
        createCarProfileUseCase,
        executeActionUseCase,
        validationService,
        welcomeView,
        mainMenuView,
        carProfileView);
    
    // Main application loop um die Navigation zu handlen
    while (navigationUseCase.shouldContinue()) {
      applicationController.processCurrentState();
    }
    
    scanner.close();
  }
}
   