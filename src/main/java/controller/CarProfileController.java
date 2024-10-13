package controller;

import exception.LoadingException;
import models.CarProfileModel;
import service.SetupService;
import utils.AnsiColorsUtil;
import utils.JsonCarProfilesLoader;
import utils.LoadingScreenAnimation;
import utils.SleepUtil;

public class CarProfileController {
  private final JsonCarProfilesLoader carProfileJsonLoader;
  private final LoadingScreenAnimation loadingScreenAnimation;
  private final Thread loadingAnimationThread;
  private final Thread loadingJsonThread;
  
  public CarProfileController() throws LoadingException {
    carProfileJsonLoader = SetupService.getCarProfileJsonLoader();
    loadingScreenAnimation = SetupService.getLoadingScreenAnimation();
    loadingAnimationThread = new Thread(loadingScreenAnimation);
    loadingJsonThread = new Thread(carProfileJsonLoader);
    
    System.out.println("Hey! To begin with, let's see if you have any car profiles saved.");
    SleepUtil.waitForFSeconds(1.0);
    
    startLoadingThreads();
    
    SleepUtil.waitForFSeconds(3.0);
    //TODO: This sleep for the animation can be deleted later
    
    stopLoadingThreads();
    
    CarProfileModel[] carProfiles = carProfileJsonLoader.getCarProfiles();
    
    
    if (carProfiles == null || carProfiles.length == 0) {
      System.out.print(AnsiColorsUtil.YELLOW.getCode() + "→ No car profiles could be found;\n  Continuing with creating a new car profile." + AnsiColorsUtil.WHITE.getCode());
    } else {
      //TODO: HIER GEHT ES WEITER
      //TODO: There are no car profiles in the JSON yet, so add some car profiles and extend from here on.
    }
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
