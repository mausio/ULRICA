package org.ulrica;

import org.ulrica.application.controller.ApplicationController;
import org.ulrica.application.port.out.CarProfilePersistencePortInterface;
import org.ulrica.infrastructure.persistence.JsonCarProfileRepository;

public class App {
    public static void main(String[] args) {
        // Infrastructure Layer
        CarProfilePersistencePortInterface repository = new JsonCarProfileRepository();

        // Application Layer
        ApplicationController appController = new ApplicationController(repository);

        // Main loop
        while (appController.shouldContinue()) {
            appController.processCurrentState();
        }
    }
}
   