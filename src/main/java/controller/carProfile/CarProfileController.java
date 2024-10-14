package controller.carProfile;

import models.CarProfileModel;
import models.ConsumptionProfileModel;
import utils.generalUtils.AnsiColorsUtil;
import utils.generalUtils.InputCleanerUtil;
import utils.generalUtils.SleepUtil;

import java.util.Scanner;
//TODO: Die Datei ist viel zu überfüllt. Sie sollte mindestens in CarProfileCreatorController und CarProfileListSelectorController refactored werden. Dieser Controller hier koordiniert dann den gesamten Vorgang.

public class CarProfileController {
  
  public static void startDialog() {
    System.out.println(
        "Hey! To begin with, let's see if you have any car profiles saved.");
  }
  
  
  public static void listCarProfilesDialog(
      CarProfileModel[] carProfiles) {
    
  }
  
  public static boolean createCarProfileOrListCarProfilesDialog() {
    return true;
  }
  
}
