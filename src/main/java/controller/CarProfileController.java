package controller;

import exception.LoadingException;
import service.SetupService;
import utils.JsonDataLoaderUtil;
import utils.LoadingScreenAnimation;
import utils.SleepUtil;

public class CarProfileController {
  JsonDataLoaderUtil carProfileJsonLoader = SetupService.getCarProfileJsonLoader();
  LoadingScreenAnimation loadingScreenAnimation = SetupService.getLoadingScreenAnimation();
  Thread loadingAnimationThread = new Thread(loadingScreenAnimation);
  Thread loadingThread = new Thread();
  
  public CarProfileController() throws LoadingException {
    System.out.print("Hey! To begin with, let's see if you have any car profiles saved.");
    
    startLoadingScreen();
    SleepUtil.waitForFSeconds(3.0);
    
    
    
    
    stopLoadingScreen();
  }
  
  
  private void startLoadingScreen() {
    loadingAnimationThread.start();
  }
  
  private void stopLoadingScreen() throws LoadingException {
    try {
      loadingScreenAnimation.stop();
      loadingAnimationThread.join();
      
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      
      throw new LoadingException(e.getMessage());
    }
  }
  
}
