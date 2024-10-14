package service;

import controller.WelcomeController;
import interfaces.GeneralControllerInterface;
import utils.LoadingScreenAnimation;

public class WelcomeService implements GeneralControllerInterface {
  public WelcomeService() {
    WelcomeController.displayWelcomeMessage();
    WelcomeController.displayAttentionMessage();
    
    LoadingScreenAnimation.runForNSeconds(5.0);
  }
  
  @Override public void startDialog() {
  
  }
}
