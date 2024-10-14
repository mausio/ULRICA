package controller.carProfile;

import exception.LoadingException;
import models.CarProfileModel;
import models.ConsumptionProfileModel;
import utils.AnsiColorsUtil;
import utils.InputCleanerUtil;
import utils.LoadingScreenAnimation;
import utils.SleepUtil;

import java.util.Scanner;
//TODO: Die Datei ist viel zu überfüllt. Sie sollte mindestens in CarProfileCreatorController und CarProfileListSelectorController refactored werden. Dieser Controller hier koordiniert dann den gesamten Vorgang.

public class CarProfileController {
  
  
  public static void startDialog() {
    System.out.println(
        "Hey! To begin with, let's see if you have any car profiles saved.");
  }
  
  public static Double getConsumptionDialog(Scanner scanner) {
    System.out.print("consumption: (in kWh) ");
    String input = scanner.nextLine();
    if (!input.isEmpty()) {
      return InputCleanerUtil.cleanDoubleFromCharacters(input);
    }
    return null;
  }
  
  public static Double getSpeedDialog(Scanner scanner) {
    System.out.print("speed: (in km/h) ");
    String input = scanner.nextLine();
    if (!input.isEmpty()) {
      return InputCleanerUtil.cleanDoubleFromCharacters(input);
    }
    return null;
  }
  
  public static void printNrOfParamter(int i) {
    System.out.println(AnsiColorsUtil.CYAN.getCode() + "\nNr. " + (i + 1) + ": " + AnsiColorsUtil.WHITE.getCode());
  }
  
  public static void printParameters(double consumption,
                                     double speed) {
    System.out.println(AnsiColorsUtil.MAGENTA.getCode() + "added " + consumption + "kWh @ " + speed + "km/h" + AnsiColorsUtil.WHITE.getCode());
  }
  
  public static boolean additionalParamterDialog(Scanner scanner) {
    System.out.println("\nDo you want add another data point? (y/n) ");
    String input = scanner.nextLine();
    return !InputCleanerUtil.formatYesOrNoToBoolean(input);
  }
  
  public static void startLoadingThreads(
      Thread loadingAnimationThread, Thread loadingJsonThread) {
    loadingAnimationThread.start();
    loadingJsonThread.start();
  }
  
  public static void stopLoadingThreads(Thread loadingAnimationThread,
                                        Thread loadingJsonThread,
                                        LoadingScreenAnimation loadingScreenAnimation)
      throws LoadingException {
    try {
      loadingJsonThread.join();
      loadingScreenAnimation.stop();
      loadingAnimationThread.interrupt();
      loadingAnimationThread.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new LoadingException(e.getMessage());
    }
  }
  
  public static void createCarProfileDialog(Scanner scanner,
                                            CarProfileModel carProfile,
                                            ConsumptionProfileModel consumptionProfile) {
    startDialogCreatingCarProfile();
    
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
    
    createConsumptionProfileDialog(consumptionProfile, scanner);
    
    //TODO: Display the car profile that has just been created
    
    //TODO: Ask if they want to save the car in the file or not
  }
  
  private static void startDialogCreatingCarProfile() {
    System.out.println(AnsiColorsUtil.WHITE.getCode());
    System.out.println("Let's create a new car profile.");
    System.out.println(
        "Please start by entering some details about your car and then progress with the consumption profile of it.");
  }
  
  public static void listCarProfilesDialog(
      CarProfileModel[] carProfiles) {
    
  }
  
  public static boolean createCarProfileOrListCarProfilesDialog() {
    
    return true;
  }
  
  private static void createConsumptionProfileDialog(
      ConsumptionProfileModel consumptionProfile, Scanner scanner) {
    startCreatingConsumptionProfileDialog();
    consumptionProfile.clearParameterList();
    consumptionProfile.createConsumptionProfile(scanner);
    consumptionProfile.performRegression();
  }
  
  private static void startCreatingConsumptionProfileDialog() {
    System.out.println(AnsiColorsUtil.WHITE.getCode());
    System.out.println(
        "Next, we calculate how much the car will probably " + "consume at which speed.");
    System.out.println(
        "For that, please give at least one data point of the " + "average consumption at a given speed" + " (e.g" + ". " + "18kWh @ 110km/h" + ") ");
  }
  
}
