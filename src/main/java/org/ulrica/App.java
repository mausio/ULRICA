package org.ulrica;

import org.ulrica.application.port.in.CreateCarProfileUseCaseInterface;
import org.ulrica.application.port.in.ShowCarProfileMenuUseCaseInterface;
import org.ulrica.application.port.in.ShowMainMenuUseCaseInterface;
import org.ulrica.application.port.in.ShowWelcomeUseCaseInterface;
import org.ulrica.application.port.out.CarProfilePersistencePortInterface;
import org.ulrica.application.usecase.CreateCarProfileInteractor;
import org.ulrica.application.usecase.ShowCarProfileMenuInteractor;
import org.ulrica.application.usecase.ShowMainMenuInteractor;
import org.ulrica.application.usecase.ShowWelcomeInteractor;
import org.ulrica.infrastructure.persistence.JsonCarProfileRepository;
import org.ulrica.presentation.controller.CarProfileController;
import org.ulrica.presentation.controller.MainMenuController;
import org.ulrica.presentation.controller.WelcomeController;
import org.ulrica.presentation.view.CarProfileView;
import org.ulrica.presentation.view.MainMenuView;
import org.ulrica.presentation.view.WelcomeView;

public class App {
    public static void main(String[] args) {
        // Infrastructure Layer
        CarProfilePersistencePortInterface carProfileRepository = new JsonCarProfileRepository();

        // Application Layer - Use Cases
        CreateCarProfileUseCaseInterface createCarProfileUseCase = new CreateCarProfileInteractor(carProfileRepository);
        ShowCarProfileMenuUseCaseInterface showCarProfileMenuUseCase = new ShowCarProfileMenuInteractor();
        ShowMainMenuUseCaseInterface showMainMenuUseCase = new ShowMainMenuInteractor();
        ShowWelcomeUseCaseInterface showWelcomeUseCase = new ShowWelcomeInteractor();

        // Presentation Layer - Views
        CarProfileView carProfileView = new CarProfileView();
        MainMenuView mainMenuView = new MainMenuView();
        WelcomeView welcomeView = new WelcomeView();

        // Presentation Layer - Controllers
        MainMenuController mainMenuController = new MainMenuController(
            showMainMenuUseCase,
            mainMenuView,
            null // Will be set after CarProfileController creation
        );

        CarProfileController carProfileController = new CarProfileController(
            createCarProfileUseCase,
            carProfileRepository,
            carProfileView,
            mainMenuController
        );

        // Set the CarProfileController in MainMenuController
        mainMenuController.setCarProfileController(carProfileController);

        WelcomeController welcomeController = new WelcomeController(
            showWelcomeUseCase,
            welcomeView
        );

        // Start the application
        welcomeController.showWelcome();
        mainMenuController.showMainMenu();
    }
}
   