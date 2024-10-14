package service;

import controller.SystemMessagesController;
import controller.carProfile.CarProfileController;
import controller.carProfile.ConsumptionProfileController;
import controller.carProfile.CreateCarProfileController;
import exception.LoadingException;
import models.CarProfileModel;
import models.ConsumptionProfileModel;
import utils.carProfileUtils.CarProfileThreadsUtil;
import utils.carProfileUtils.CarProfilesJsonLoaderUtil;
import utils.generalUtils.LoadingScreenAnimationUtil;
import utils.generalUtils.SleepUtil;

import java.util.Scanner;

public class CarProfileService {
  private static Scanner scanner;
  private static CarProfileModel carProfile;
  private static ConsumptionProfileModel consumptionProfile;
  private LoadingScreenAnimationUtil loadingScreenAnimation;
  private Thread loadingAnimationThread;
  private Thread loadingJsonThread;
  private CarProfilesJsonLoaderUtil carProfileJsonLoader;
  private CreateCarProfileController createCarProfile;
  
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
    
    CarProfileThreadsUtil.startLoadingThreads(loadingAnimationThread,
                                              loadingJsonThread);
    SleepUtil.waitForFSeconds(3.0);
    CarProfileThreadsUtil.stopLoadingThreads(loadingAnimationThread,
                                             loadingJsonThread,
                                             loadingScreenAnimation);
    
    CarProfileModel[] carProfiles = carProfileJsonLoader.getCarProfiles();
    
    if (carProfiles == null || carProfiles.length <= 1) {
      //TODO: SystemMessages auslagern
      SystemMessagesController.urgentMessage(
          "No car profiles could be found;\n  Continuing with creating a new car profile.");
      SleepUtil.waitForFSeconds(2.0);
      startCreatCarProfile();
      
    } else {
      if (CarProfileController.createCarProfileOrListCarProfilesDialog()) {
        CarProfileController.listCarProfilesDialog(carProfiles);
        //a to'do For later, after adding some car profiles to the json file
        //TODO: There are no car profiles in the JSON file yet, so add some car profiles FIRST and extend from here on.
      } else {
        startCreatCarProfile();
      }
    }
    
  }
  
  private static void startCreatCarProfile() {
    CreateCarProfileController.startDialogCreatingCarProfile();
    CreateCarProfileController.createCarProfileDialog(scanner,
                                                      carProfile,
                                                      consumptionProfile);
    createConsumptionProfileDialog(consumptionProfile, scanner);
  }
  
  
  private static void createConsumptionProfileDialog(
      ConsumptionProfileModel consumptionProfile, Scanner scanner) {
    ConsumptionProfileController.startCreatingConsumptionProfileDialog();
    consumptionProfile.clearParameterList();
    consumptionProfile.createConsumptionProfile(scanner);
    consumptionProfile.performRegression();
  }
  
}
