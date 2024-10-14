package org.ulrica;

import controller.carProfile.CarProfileController;
import controller.WelcomeController;
import exception.LoadingException;
import service.SetupService;
import utils.ConsoleInteractorUtil;

//TODO: I should introduce interfaces to classes

public class App {
  public static void main(String[] args) throws LoadingException {
    new SetupService();
    
    ConsoleInteractorUtil.clear();
    new WelcomeController();
    
    ConsoleInteractorUtil.stepper("Step 1: Choose or create a car profile");
    new CarProfileController();
    
//    ConsoleInteractorUtil.stepper("Step 2: Stuff");
  }
}
