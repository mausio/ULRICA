package org.ulrica;

import controller.CarProfileController;
import controller.WelcomeController;
import exception.LoadingException;
import service.SetupService;
import utils.AnsiColorsUtil;
import utils.ConsoleInteractorUtil;

//TODO: I should introduce interfaces to classes

public class App {
  public static void main(String[] args) throws LoadingException {
    new SetupService();
    new WelcomeController();
    
    ConsoleInteractorUtil.stepper("Step 1: Choose or create a car profile");
    new CarProfileController();
    
    ConsoleInteractorUtil.stepper("Step 2: Stuff");
  }
}
