package service;

import controller.LoadingScreenAnimationController;
import controller.SystemMessagesController;
import controller.WelcomeController;
import interfaces.GeneralControllerInterface;
import utils.generalUtils.ConsoleInteractorUtil;

public class WelcomeService implements GeneralControllerInterface {
  public WelcomeService() {
    showWelcome();
  }
  
  public void showWelcome() {
    ConsoleInteractorUtil.clear();
    
    WelcomeController.displayWelcomeMessage();
    
    SystemMessagesController.imperativeMessage("\n  This program's text is completely WHITE,\n" + "  " + "so please consider using dark mode.");
    
    LoadingScreenAnimationController.runForNSeconds(2.0);
  }
  
  @Override public void startDialog() {
    // This method is part of the GeneralControllerInterface but not needed for welcome service
  }
}
