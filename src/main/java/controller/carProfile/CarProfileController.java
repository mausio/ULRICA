package controller.carProfile;

import models.CarProfileModel;

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
