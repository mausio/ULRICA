package controller;

import utils.generalUtils.AnsiColorsUtil;
import utils.generalUtils.InputCleanerUtil;

import java.util.Scanner;

public class ConsumptionProfileController {
  
  public static void printEstimatedConsumptionAtSpeed(
      double estimatedConsumption, double speed) {
    System.out.println("\nEstimated consumption of " + estimatedConsumption + "kWh @ " + speed + "km/h");
  }
  
  public static void printModel(String s, double a, double b) {
    System.out.println(AnsiColorsUtil.MAGENTA.getCode());
    System.out.printf(s, a, b);
    System.out.print(AnsiColorsUtil.WHITE.getCode());
  }
  
  public static void startCreatingConsumptionProfileDialog() {
    System.out.println(AnsiColorsUtil.WHITE.getCode());
    System.out.println(
        "Next, we calculate how much the car will probably " + "consume at which speed.");
    System.out.println(
        "For that, please give at least two data points of the " + "average consumption at a given speed" + " (e.g" + ". " + "18kWh @ 110km/h" + ") ");
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
}
