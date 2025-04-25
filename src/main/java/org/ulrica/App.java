package org.ulrica;

import java.util.Scanner;

import org.ulrica.application.port.in.CalculateAcChargingUseCaseInterface;
import org.ulrica.application.port.in.CalculateDcChargingUseCaseInterface;
import org.ulrica.application.port.in.CalculateRangeUseCaseInterface;
import org.ulrica.application.port.in.CreateCarProfileUseCaseInterface;
import org.ulrica.application.port.in.ExecuteActionUseCaseInterface;
import org.ulrica.application.port.in.InputValidationServiceInterface;
import org.ulrica.application.port.in.NavigationUseCaseInterface;
import org.ulrica.application.port.in.ShowActionMenuUseCaseInterface;
import org.ulrica.application.port.in.ShowCarProfileMenuUseCaseInterface;
import org.ulrica.application.port.in.ShowMainMenuUseCaseInterface;
import org.ulrica.application.port.in.ShowWelcomeUseCaseInterface;
import org.ulrica.application.port.out.AcChargingOutputPortInterface;
import org.ulrica.application.port.out.CarProfilePersistencePortInterface;
import org.ulrica.application.port.out.DcChargingOutputPortInterface;
import org.ulrica.application.port.out.RangeCalculationOutputPortInterface;
import org.ulrica.application.port.out.UserInputPortInterface;
import org.ulrica.application.port.out.UserOutputPortInterface;
import org.ulrica.application.service.InputValidationService;
import org.ulrica.application.usecase.CalculateAcChargingInteractor;
import org.ulrica.application.usecase.CalculateDcChargingInteractor;
import org.ulrica.application.usecase.CalculateRangeInteractor;
import org.ulrica.application.usecase.CreateCarProfileInteractor;
import org.ulrica.application.usecase.ExecuteActionInteractor;
import org.ulrica.application.usecase.NavigationUseCase;
import org.ulrica.application.usecase.ShowActionMenuInteractor;
import org.ulrica.application.usecase.ShowCarProfileMenuInteractor;
import org.ulrica.application.usecase.ShowMainMenuInteractor;
import org.ulrica.application.usecase.ShowWelcomeInteractor;
import org.ulrica.domain.service.AcChargingCalculator;
import org.ulrica.domain.service.ActionAvailabilityService;
import org.ulrica.domain.service.ActionAvailabilityServiceImpl;
import org.ulrica.domain.service.DcChargingCalculator;
import org.ulrica.domain.service.ProfileSelectionService;
import org.ulrica.domain.service.ProfileSelectionServiceImpl;
import org.ulrica.domain.service.RangeCalculatorService;
import org.ulrica.infrastructure.adapter.ConsoleUserInputAdapter;
import org.ulrica.infrastructure.adapter.ConsoleUserOutputAdapter;
import org.ulrica.infrastructure.persistence.JsonCarProfileRepository;
import org.ulrica.presentation.controller.AcChargingController;
import org.ulrica.presentation.controller.ApplicationControllerWithActionMenu;
import org.ulrica.presentation.controller.DcChargingController;
import org.ulrica.presentation.controller.RangeCalculationController;
import org.ulrica.presentation.view.AcChargingView;
import org.ulrica.presentation.view.ActionMenuView;
import org.ulrica.presentation.view.ActionResultView;
import org.ulrica.presentation.view.CarProfileView;
import org.ulrica.presentation.view.DcChargingView;
import org.ulrica.presentation.view.MainMenuView;
import org.ulrica.presentation.view.RangeCalculationView;
import org.ulrica.presentation.view.WelcomeView;

public class App {
    public static void main(String[] args) {
        // Infrastructure Layer - Scanner, UserInputPortInterface, UserOutputPortInterface, CarProfilePersistencePortInterface für die Eingabe und Ausgabe
        Scanner scanner = new Scanner(System.in);
        UserInputPortInterface userInputPort = new ConsoleUserInputAdapter(scanner);
        UserOutputPortInterface userOutputPort = new ConsoleUserOutputAdapter();
        CarProfilePersistencePortInterface repository = new JsonCarProfileRepository();
        
        // Domain Layer - Service für die Profileauswahl
        ProfileSelectionService profileSelectionService = new ProfileSelectionServiceImpl();
        ActionAvailabilityService actionAvailabilityService = new ActionAvailabilityServiceImpl(profileSelectionService);
        
        // Domain Layer - Calculator Services
        DcChargingCalculator dcChargingCalculator = new DcChargingCalculator();
        AcChargingCalculator acChargingCalculator = new AcChargingCalculator();
        RangeCalculatorService rangeCalculatorService = new RangeCalculatorService();
        
        // Application Layer - Use Cases
        NavigationUseCaseInterface navigationUseCase = new NavigationUseCase();
        ShowWelcomeUseCaseInterface showWelcomeUseCase = new ShowWelcomeInteractor();
        ShowMainMenuUseCaseInterface showMainMenuUseCase = new ShowMainMenuInteractor();
        ShowCarProfileMenuUseCaseInterface showCarProfileMenuUseCase = new ShowCarProfileMenuInteractor();
        ShowActionMenuUseCaseInterface showActionMenuUseCase = new ShowActionMenuInteractor(profileSelectionService);
        CreateCarProfileUseCaseInterface createCarProfileUseCase = new CreateCarProfileInteractor(repository);
        InputValidationServiceInterface validationService = new InputValidationService();
        
        // Presentation Layer - Alle Views
        WelcomeView welcomeView = new WelcomeView();
        MainMenuView mainMenuView = new MainMenuView(userOutputPort);
        CarProfileView carProfileView = new CarProfileView();
        ActionMenuView actionMenuView = new ActionMenuView(userOutputPort);
        ActionResultView actionResultView = new ActionResultView(userOutputPort);
        
        // Charging Calculator Views
        DcChargingOutputPortInterface dcChargingView = new DcChargingView(userOutputPort);
        AcChargingOutputPortInterface acChargingView = new AcChargingView(userOutputPort, acChargingCalculator);
        
        // Range Calculator View
        RangeCalculationOutputPortInterface rangeCalculationView = new RangeCalculationView(userOutputPort);
        
        // Charging Calculator Use Cases
        CalculateDcChargingUseCaseInterface calculateDcChargingUseCase = new CalculateDcChargingInteractor(
                profileSelectionService, dcChargingCalculator, dcChargingView);
        CalculateAcChargingUseCaseInterface calculateAcChargingUseCase = new CalculateAcChargingInteractor(
                profileSelectionService, acChargingCalculator, acChargingView);
        
        // Charging Calculator Controllers
        DcChargingController dcChargingController = new DcChargingController(
                userInputPort, userOutputPort, calculateDcChargingUseCase, dcChargingView);
        AcChargingController acChargingController = new AcChargingController(
                userInputPort, userOutputPort, calculateAcChargingUseCase, acChargingView);
        
        // Range Calculator Use Case and Controller
        CalculateRangeUseCaseInterface calculateRangeUseCase = new CalculateRangeInteractor(
                rangeCalculatorService, rangeCalculationView, profileSelectionService);
        RangeCalculationController rangeCalculationController = new RangeCalculationController(
                calculateRangeUseCase, userInputPort, rangeCalculationView);
        
        // Action Interactor mit korrektem Output-Port
        ExecuteActionUseCaseInterface executeActionUseCase = new ExecuteActionInteractor(
                navigationUseCase, 
                actionResultView, 
                profileSelectionService,
                dcChargingController,
                acChargingController,
                rangeCalculationController);
        
        // Presentation Layer - ApplicationControllerWithActionMenu mit Action-Menu-Unterstützung
        ApplicationControllerWithActionMenu applicationController = new ApplicationControllerWithActionMenu(
            userInputPort,
            userOutputPort,
            repository,
            navigationUseCase,
            profileSelectionService,
            actionAvailabilityService,
            showWelcomeUseCase,
            showMainMenuUseCase,
            showCarProfileMenuUseCase,
            showActionMenuUseCase,
            createCarProfileUseCase,
            executeActionUseCase,
            validationService,
            welcomeView,
            mainMenuView,
            carProfileView,
            actionMenuView,
            actionResultView
        );
        
        // Main application loop um die Navigation zu handlen
        while (navigationUseCase.shouldContinue()) {
            applicationController.processCurrentState();
        }
        
        scanner.close();
    }
}
   