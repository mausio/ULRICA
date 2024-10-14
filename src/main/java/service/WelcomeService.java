package service;

import controller.WelcomeController;
import utils.LoadingScreenAnimation;
import utils.SleepUtil;

public class WelcomeService {
  public WelcomeService() {
    WelcomeController.displayWelcomeMessage();
    WelcomeController.displayAttentionMessage();
    
    LoadingScreenAnimation.runForNSeconds(5.0);
  }
}
