package org.ulrica;

import controller.WelcomeController;
import controller.carProfile.CarProfileController;
import exception.LoadingException;
import service.SetupService;
import service.WelcomeService;
import utils.ConsoleInteractorUtil;

//TODO: I should introduce interfaces to classes

public class App {
  public static void main(String[] args) throws LoadingException {
    new SetupService();
    
    ConsoleInteractorUtil.clear();
    new WelcomeService();
    
    ConsoleInteractorUtil.stepper(
        "Step 1: Choose or create a car profile");
    new CarProfileController();

//    ConsoleInteractorUtil.stepper("Step 2: Stuff");
  }
}
