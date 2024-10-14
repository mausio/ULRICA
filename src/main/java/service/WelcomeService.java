package service;

import controller.SystemMessagesController;
import controller.WelcomeController;
import interfaces.GeneralControllerInterface;
import utils.generalUtils.ConsoleInteractorUtil;
import utils.generalUtils.LoadingScreenAnimationUtil;

public class WelcomeService implements GeneralControllerInterface {
  public WelcomeService() {
    ConsoleInteractorUtil.clear();
    
    WelcomeController.displayWelcomeMessage();
    
    SystemMessagesController.imperativeMessage("\n  This program's text is completely WHITE,\n" + "  " + "so please consider using dark mode.");
    
    LoadingScreenAnimationUtil.runForNSeconds(5.0);
  }
  
  @Override public void startDialog() {
  
  }
}
