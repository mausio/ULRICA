package service;

import utils.generalUtils.AnsiColorsUtil;
import utils.generalUtils.ConsoleInteractorUtil;

public class SetupService {
  //TODO: Create Singleton for CarProfileModel to access it anywhere
  public SetupService() {
    initialize();
  }
  
  public void initialize() {
    ConsoleInteractorUtil.clear();
    System.out.print(AnsiColorsUtil.WHITE.getCode());
  }
}
