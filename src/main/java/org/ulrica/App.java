package org.ulrica;

import controller.WelcomeController;
import service.SetupService;

public class App {
  public static void main(String[] args) {
    new SetupService();
    
    new WelcomeController();
    
//    Scanner scanner = new Scanner(System.in);
//    CarProfileModel carProfile = new CarProfileModel();
//    ConsumptionProfileModel consumptionProfile = new ConsumptionProfileModel();
//    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//
//
//    consumptionProfile.createConsumptionProfile(scanner);
//
//    carProfile.setConsumptionProfile(consumptionProfile);
//
//    consumptionProfile.estimateConsumption(80.0);
//    String jsonCarProfile = gson.toJson(carProfile);
//    System.out.println("\n" + jsonCarProfile);
  }
}
