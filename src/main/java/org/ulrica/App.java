package org.ulrica;

import controller.CarProfileController;
import service.SetupService;
import utils.AnsiColorsUtil;

public class App {
  public static void main(String[] args) {
    new SetupService();
//    new WelcomeController();
    
    System.out.println(AnsiColorsUtil.BLUE.getCode() + "Step 1: Load car profile or create a new one\n" + AnsiColorsUtil.WHITE.getCode());
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
