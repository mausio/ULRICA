package org.ulrica;

import exception.LoadingException;
import service.SetupService;
import service.WelcomeService;
import service.CarProfileService;
import utils.ConsoleInteractorUtil;

//TODO: I should introduce interfaces to classes

public class App {
  public static void main(String[] args) throws LoadingException {
    new SetupService();
    
    ConsoleInteractorUtil.clear();
    new WelcomeService();
    
    ConsoleInteractorUtil.stepper(
        "Step 1: Choose or create a car profile");
    new CarProfileService();

//    ConsoleInteractorUtil.stepper("Step 2: Stuff");
  }
}
