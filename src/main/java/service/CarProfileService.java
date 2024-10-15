package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.ConsumptionProfileController;
import controller.GsonController;
import controller.LoadingScreenAnimationController;
import controller.SystemMessagesController;
import controller.carProfile.CarProfileController;
import controller.carProfile.CreateCarProfileController;
import exception.LoadingException;
import models.CarProfileModel;
import models.ConsumptionProfileModel;
import utils.carProfileUtils.CarProfileThreadsUtil;
import utils.carProfileUtils.CarProfilesJsonLoaderUtil;
import utils.generalUtils.GsonUtil;

import java.util.Scanner;

public class CarProfileService {
  private  Scanner scanner;
  private  CarProfileModel carProfile;
  private  ConsumptionProfileModel consumptionProfile;
  private  Gson gson;
  private  LoadingScreenAnimationController loadingScreenAnimation;
  private  Thread loadingAnimationThread;
  private  Thread loadingJsonThread;
  private  CarProfilesJsonLoaderUtil carProfileJsonLoader;
  
  public CarProfileService() throws LoadingException {
    carProfileJsonLoader = new CarProfilesJsonLoaderUtil(
        "./src/main/java/resources/carProfiles.json");
    carProfile = new CarProfileModel();
    consumptionProfile = new ConsumptionProfileModel();
    loadingScreenAnimation = new LoadingScreenAnimationController();
    loadingAnimationThread = new Thread(loadingScreenAnimation);
    loadingJsonThread = new Thread(carProfileJsonLoader);
    scanner = new Scanner(System.in);
    gson = new GsonBuilder().setPrettyPrinting().create();
    
    //TODO: Der Step1 sollte damit beginnen, dass erst gecheckt wird, ob sich überhaupt cars in der JSON file befinden; Wenn nicht, dann einfach mit Creation fortfahren; Wenn doch, dann fragen, ob man ein car profile auswählen will oder lieber ein neues erzeugt
    
    CarProfileController.startDialog();
    
    // SleepUtil.waitForFSeconds(1.0);
    
    CarProfileThreadsUtil.startLoadingThreads(loadingAnimationThread,
                                              loadingJsonThread);
    // SleepUtil.waitForFSeconds(1.0);
    CarProfileThreadsUtil.stopLoadingThreads(loadingAnimationThread,
                                             loadingJsonThread,
                                             loadingScreenAnimation);
    
    CarProfileModel[] carProfiles = carProfileJsonLoader.getCarProfiles();
    
    if (carProfiles == null || carProfiles.length <= 1) {
      //TODO: SystemMessages auslagern
      SystemMessagesController.urgentMessage(
          "No car profiles could be found;\n  Continuing with creating a new car profile.");
      // SleepUtil.waitForFSeconds(1.0);
      createNewCarProfile();
      //TODO: Display the created JSON car profile
      //TODO: Let user decide if they want to save the profile permanently
    } else {
      if (CarProfileController.createCarProfileOrListCarProfilesDialog()) {
        CarProfileController.listCarProfilesDialog(carProfiles);
        //a to'do For later, after adding some car profiles to the json file
        //TODO: There are no car profiles in the JSON file yet, so add some car profiles FIRST and extend from here on.
      } else {
        createNewCarProfile();
      }
    }
    
  }
  
  private void showSingleCarProfile() {
    GsonController.printJsonString(GsonUtil.objectToGsonString(
        carProfile,
        gson));
  }
  
  private void createNewCarProfile() {
    CreateCarProfileController.startDialogCreatingCarProfile();
    CreateCarProfileController.createCarProfileDialog(carProfile);
    createConsumptionProfileDialog(consumptionProfile, scanner);
    showSingleCarProfile();
    saveCarProfile();
  }
  
  private void createConsumptionProfileDialog(
      ConsumptionProfileModel consumptionProfile, Scanner scanner) {
    ConsumptionProfileController.startCreatingConsumptionProfileDialog();
    consumptionProfile.clearParameterList();
    consumptionProfile.createConsumptionProfile(scanner);
    consumptionProfile.performRegression();
    carProfile.setConsumptionProfile(consumptionProfile);
  }
  
  private void saveCarProfile() {
    CreateCarProfileController.saveDialog(scanner);
    
  }
}
