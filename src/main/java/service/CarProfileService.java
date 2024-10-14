package service;

import controller.carProfile.CarProfileController;
import exception.LoadingException;
import models.CarProfileModel;
import models.ConsumptionProfileModel;
import utils.AnsiColorsUtil;
import utils.JsonCarProfilesLoader;
import utils.LoadingScreenAnimation;
import utils.SleepUtil;

import java.util.Scanner;

public class CarProfileService {
  private static Scanner scanner;
  private LoadingScreenAnimation loadingScreenAnimation;
  private Thread loadingAnimationThread;
  private Thread loadingJsonThread;
  private CarProfileModel carProfile;
  private ConsumptionProfileModel consumptionProfile;
  private JsonCarProfilesLoader carProfileJsonLoader;
  
  public CarProfileService() throws LoadingException {
    carProfileJsonLoader = SetupService.getCarProfileJsonLoader();
    carProfile = SetupService.getCarProfile();
    consumptionProfile = SetupService.getConsumptionProfile();
    loadingScreenAnimation = SetupService.getLoadingScreenAnimation();
    loadingAnimationThread = new Thread(loadingScreenAnimation);
    loadingJsonThread = new Thread(carProfileJsonLoader);
    scanner = SetupService.getScanner();
    
    //TODO: Der Step1 sollte damit beginnen, dass erst gecheckt wird, ob sich überhaupt cars in der JSON file befinden; Wenn nicht, dann einfach mit Creation fortfahren; Wenn doch, dann fragen, ob man ein car profile auswählen will oder lieber ein neues erzeugt
    
    CarProfileController.startDialog();
    
    SleepUtil.waitForFSeconds(1.0);
    
    CarProfileController.startLoadingThreads(loadingAnimationThread,
                                             loadingJsonThread);
    SleepUtil.waitForFSeconds(3.0);
    CarProfileController.stopLoadingThreads(loadingAnimationThread,
                                            loadingJsonThread,
                                            loadingScreenAnimation);
    
    CarProfileModel[] carProfiles = carProfileJsonLoader.getCarProfiles();
    
    if (carProfiles == null || carProfiles.length <= 1) {
      //TODO: SystemMessages auslagern
      System.out.println(AnsiColorsUtil.YELLOW.getCode() + "→ No car profiles could be found;\n  Continuing with creating a new car profile." + AnsiColorsUtil.WHITE.getCode());
      SleepUtil.waitForFSeconds(2.0);
      CarProfileController.createCarProfileDialog(scanner,
                                                  carProfile,
                                                  consumptionProfile);
    } else {
      if (CarProfileController.createCarProfileOrListCarProfilesDialog()) {
        CarProfileController.listCarProfilesDialog(carProfiles);
        //a to'do For later, after adding some car profiles to the json file
        //TODO: There are no car profiles in the JSON file yet, so add some car profiles FIRST and extend from here on.
      } else {
        CarProfileController.createCarProfileDialog(scanner,
                                                    carProfile,
                                                    consumptionProfile);
      }
    }
    
  }
}
