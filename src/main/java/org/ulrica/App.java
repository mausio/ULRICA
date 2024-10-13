package org.ulrica;

import controller.CarProfileController;
import controller.WelcomeController;
import service.SetupService;
import utils.ConsoleInteractorUtil;
import utils.LoadingScreenAnimation;
import utils.SleepUtil;

public class App {
  public static void main(String[] args) {
    new SetupService();
    new WelcomeController();
    
    new CarProfileController();
    
//    Scanner scanner = new Scanner(System.in);
//    CarProfileModel carProfile = new CarProfileModel();
//    ConsumptionProfileModel consumptionProfile = new ConsumptionProfileModel();
//    Gson gson = new GsonBuilder().setPrettyPrinting().create();

//    consumptionProfile.createConsumptionProfile(scanner);
//
//    carProfile.setConsumptionProfile(consumptionProfile);
//
//    consumptionProfile.estimateConsumption(80.0);
//    String jsonCarProfile = gson.toJson(carProfile);
//    System.out.println("\n" + jsonCarProfile);
  }
}
