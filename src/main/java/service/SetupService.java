package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.AnsiColorsUtil;
import utils.ConsoleInteractorUtil;
import utils.JsonCarProfilesLoader;
import utils.LoadingScreenAnimation;

public class SetupService {
  private static final String carProfilePath = "./src/main/java/resources/carProfiles.json";
  static LoadingScreenAnimation loadingScreenAnimation = new LoadingScreenAnimation();
  static JsonCarProfilesLoader carProfileJsonLoader = new JsonCarProfilesLoader(carProfilePath);
  static Gson gson = new GsonBuilder().setPrettyPrinting().create();
  
  public SetupService() {
    ConsoleInteractorUtil.clear();
    
    System.out.print(AnsiColorsUtil.WHITE.getCode());
  }
  
  public String getCarProfilePath() {
    return carProfilePath;
  }
  
  public static LoadingScreenAnimation getLoadingScreenAnimation() {
    return loadingScreenAnimation;
  }
  
  public static JsonCarProfilesLoader getCarProfileJsonLoader() {
    return carProfileJsonLoader;
  }
  
  public static Gson getGson() {
    return gson;
  }
}
