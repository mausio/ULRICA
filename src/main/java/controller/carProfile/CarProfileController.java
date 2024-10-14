package controller.carProfile;

import exception.LoadingException;
import models.CarProfileModel;
import models.ConsumptionProfileModel;
import service.SetupService;
import utils.*;

import java.util.Scanner;

//TODO: Die Datei ist viel zu überfüllt. Sie sollte mindestens in CarProfileCreatorController und CarProfileListSelectorController refactored werden. Dieser Controller hier koordiniert dann den gesamten Vorgang.

public class CarProfileController {
  private static Scanner scanner;
  private final LoadingScreenAnimation loadingScreenAnimation;
  private Thread loadingAnimationThread;
  private Thread loadingJsonThread;
  private CarProfileModel carProfile;
  private ConsumptionProfileModel consumptionProfile;
  
  public CarProfileController() throws LoadingException {
    JsonCarProfilesLoader carProfileJsonLoader = SetupService.getCarProfileJsonLoader();
    carProfile = SetupService.getCarProfile();
    consumptionProfile = SetupService.getConsumptionProfile();
    loadingScreenAnimation = SetupService.getLoadingScreenAnimation();
    loadingAnimationThread = new Thread(loadingScreenAnimation);
    loadingJsonThread = new Thread(carProfileJsonLoader);
    scanner = SetupService.getScanner();
    
    //TODO: Der Step1 sollte damit beginnen, dass erst gecheckt wird, ob sich überhaupt cars in der JSON file befinden; Wenn nicht, dann einfach mit Creation fortfahren; Wenn doch, dann fragen, ob man ein car profile auswählen will oder lieber ein neues erzeugt
    
    
    System.out.println(
        "Hey! To begin with, let's see if you have any car profiles saved.");
    SleepUtil.waitForFSeconds(1.0);
    
    startLoadingThreads();
    
    SleepUtil.waitForFSeconds(3.0);
    //TODO: This sleep for the animation can be deleted later
    
    stopLoadingThreads();
    
    CarProfileModel[] carProfiles = carProfileJsonLoader.getCarProfiles();
    
    if (carProfiles == null || carProfiles.length <= 1) {
      System.out.println(AnsiColorsUtil.YELLOW.getCode() + "→ No car profiles could be found;\n  Continuing with creating a new car profile." + AnsiColorsUtil.WHITE.getCode());
      SleepUtil.waitForFSeconds(2.0);
      createCarProfileDialog();
    } else {
      if (createCarProfileOrListCarProfilesDialog()) {
        listCarProfilesDialog(carProfiles);
        //a to'do For later, after adding some car profiles to the json file
        //TODO: There are no car profiles in the JSON file yet, so add some car profiles FIRST and extend from here on.
      } else {
        createCarProfileDialog();
      }
    }
  }
  
  public static Double getConsumptionDialog() {
    System.out.print("consumption: (in kWh) ");
    String input = scanner.nextLine();
    if (!input.isEmpty()) {
      return InputCleanerUtil.cleanDoubleFromCharacters(input);
    }
    return null;
  }
  
  public static Double getSpeedDialog() {
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
  
  public static boolean anotherParameterTupleDialog() {
    System.out.println("\nDo you want add another data point? (y/n) ");
    String input = scanner.nextLine();
    return !InputCleanerUtil.formatYesOrNoToBoolean(input);
  }
  
  private void createCarProfileDialog() {
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
    
    createConsumptionProfileDialog();
    
    //TODO: Display the car profile that has just been created
    
    //TODO: Ask if they want to save the car in the file or not
  }
  
  private void createConsumptionProfileDialog() {
    startCreatingConsumptionProfileDialog();
    consumptionProfile.clearParameterList();
    consumptionProfile.createConsumptionProfile();
    consumptionProfile.performRegression();
  }
  
  private void startDialogCreatingCarProfile(){
    System.out.println(AnsiColorsUtil.WHITE.getCode());
    System.out.println("Let's create a new car profile.");
    System.out.println("Please start by entering some details about your car and then progress with the consumption profile of it.");
  }
  
  private void startCreatingConsumptionProfileDialog() {
    System.out.println(AnsiColorsUtil.WHITE.getCode());
    System.out.println(
        "Next, we calculate how much the car will probably " + "consume at which speed.");
    System.out.println(
        "For that, please give at least one data point of the " + "average consumption at a given speed" + " (e.g" + ". " + "18kWh @ 110km/h" + ") ");
  }
  
  private void listCarProfilesDialog(CarProfileModel[] carProfiles) {
  
  }
  
  private boolean createCarProfileOrListCarProfilesDialog() {
    
    return true;
  }
  
  
  private void startLoadingThreads() {
    loadingAnimationThread.start();
    loadingJsonThread.start();
  }
  
  private void stopLoadingThreads() throws LoadingException {
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
  
}
