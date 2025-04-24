package org.ulrica;

import org.ulrica.application.usecase.ShowCarProfileMenuInteractor;
import org.ulrica.application.usecase.ShowMainMenuInteractor;
import org.ulrica.application.usecase.ShowWelcomeInteractor;
import org.ulrica.presentation.controller.CarProfileMenuController;
import org.ulrica.presentation.controller.MainMenuController;
import org.ulrica.presentation.controller.WelcomeController;

public class App {

    public static void main(String[] args) {
        // Interactors: 
        ShowWelcomeInteractor showWelcomeInteractor = new ShowWelcomeInteractor();
        ShowMainMenuInteractor showMainMenuInteractor = new ShowMainMenuInteractor();
        ShowCarProfileMenuInteractor showCarProfileMenuInteractor = new ShowCarProfileMenuInteractor();

        // Controllers: 
        WelcomeController welcomeController = new WelcomeController(showWelcomeInteractor);
        CarProfileMenuController carProfileMenuController = new CarProfileMenuController(showCarProfileMenuInteractor);
        MainMenuController mainMenuController = new MainMenuController(showMainMenuInteractor, carProfileMenuController);

        // Execution: 
        welcomeController.showWelcome();
        mainMenuController.showMainMenu();
    }
}
   