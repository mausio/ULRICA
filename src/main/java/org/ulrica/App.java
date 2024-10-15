package org.ulrica;

import exception.LoadingException;
import service.CarProfileService;
import service.SetupService;
import service.WelcomeService;
import utils.generalUtils.ConsoleInteractorUtil;

//TODO: I should introduce interfaces to classes

public class App {
  public static void main(String[] args) throws LoadingException {
    new SetupService();
    
    new WelcomeService();
    
    ConsoleInteractorUtil.stepper(
        "Step 1: Choose or create a car profile");
    new CarProfileService();

//    ConsoleInteractorUtil.stepper("Step 2: Stuff");
  }
}
