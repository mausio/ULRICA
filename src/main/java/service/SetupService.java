package service;

import utils.AnsiColorsUtil;
import utils.ConsoleInteractorUtil;
import utils.JsonDataLoaderUtil;
import utils.LoadingScreenAnimation;

public class SetupService {
  private static final String carProfilePath = "./src/main/java/resources/carProfiles.json";
  static LoadingScreenAnimation loadingScreenAnimation = new LoadingScreenAnimation();
  static JsonDataLoaderUtil carProfileJsonLoader = new JsonDataLoaderUtil(SetupService.getCarProfilePath());
  
  public SetupService() {
    ConsoleInteractorUtil.clear();
    
    System.out.print(AnsiColorsUtil.WHITE.getCode());
  }
  
  public static String getCarProfilePath() {
    return carProfilePath;
  }
  
  public static LoadingScreenAnimation getLoadingScreenAnimation() {
    return loadingScreenAnimation;
  }
  
  public static JsonDataLoaderUtil getCarProfileJsonLoader() {
    return carProfileJsonLoader;
  }
}
