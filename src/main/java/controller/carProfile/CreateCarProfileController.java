package controller.carProfile;

import models.CarProfileModel;
import models.ConsumptionProfileModel;
import utils.generalUtils.AnsiColorsUtil;
import utils.generalUtils.InputCleanerUtil;
import utils.generalUtils.SleepUtil;

import java.util.Scanner;

public class CreateCarProfileController {
  public static void createCarProfileDialog(Scanner scanner,
                                            CarProfileModel carProfile,
                                            ConsumptionProfileModel consumptionProfile) {
    SleepUtil.waitForFSeconds(2.0);
    
    System.out.println(AnsiColorsUtil.RESET.getCode());
    
    System.out.print(
        "How should the car be named? (e.g.\"Sir Charge-A-Lot\")" + ": ");
    String input = scanner.nextLine();
    if (!input.isEmpty()) {
      carProfile.setName(InputCleanerUtil.cleanWhitespacesAround(input));
      //Todo: Error handling for invalid input (should say sth like "sth went wrong; Please try again!")
    }
    
    System.out.print("Which manufacturer is it from? (e.g.\"Opel\"): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      carProfile.setName(InputCleanerUtil.cleanWhitespacesAround(input));
      //Todo: Error handling for invalid input (should say sth like "sth went wrong; Please try again!")
    }
    
    System.out.print("Which model is it? (e.g.\"Corsa-e\"): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      carProfile.setModel(InputCleanerUtil.cleanWhitespacesAround(
          input));
      //Todo: Error handling for invalid input (should say sth like "sth went wrong; Please try again!")
    }
    
    System.out.print("When was it build? (e.g.\"2020\"): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      carProfile.setBuildYear(InputCleanerUtil.cleanIntegerFromCharacters(
          input));
      //Todo: Error handling for invalid input (should say sth like "sth went wrong; Please try again!")
    }
    
    System.out.print(
        "What is the usable size of the battery? (in kWh): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      carProfile.setBatterySize(InputCleanerUtil.cleanDoubleFromCharacters(
          input));
      //Todo: Error handling for invalid input (should say sth like "sth went wrong; Please try again!")
    }
    
    System.out.print("Does your car have a heatpump? (type: y/n): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      carProfile.setHasHeatPump(InputCleanerUtil.formatYesOrNoToBoolean(
          input));
      //Todo: Error handling for invalid input (should say sth like "sth went wrong; Please try again!")
    }
    
    //TODO: Display the car profile that has just been created
    
    //TODO: Ask if they want to save the car in the file or not
  }
  
  public static void startDialogCreatingCarProfile() {
    System.out.println(AnsiColorsUtil.WHITE.getCode());
    System.out.println("Let's create a new car profile.");
    System.out.println(
        "Please start by entering some details about your car and then progress with the consumption profile of it.");
  }
}
