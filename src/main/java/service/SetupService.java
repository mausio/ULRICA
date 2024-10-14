package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.CarProfileModel;
import models.ConsumptionProfileModel;
import utils.generalUtils.AnsiColorsUtil;
import utils.generalUtils.ConsoleInteractorUtil;
import utils.carProfileUtils.CarProfilesJsonLoaderUtil;
import utils.generalUtils.LoadingScreenAnimationUtil;

import java.util.Scanner;

public class SetupService {
  private static final String carProfilePath = "./src/main/java/resources/carProfiles.json";
  
  private static Scanner scanner = new Scanner(System.in);
  private static CarProfileModel carProfile = new CarProfileModel();
  private static ConsumptionProfileModel consumptionProfile = new ConsumptionProfileModel();
  
  public static void setConsumptionProfile(
      ConsumptionProfileModel consumptionProfile) {
    SetupService.consumptionProfile = consumptionProfile;
  }
  
  public static ConsumptionProfileModel getConsumptionProfile() {
    return consumptionProfile;
  }
  
  private static LoadingScreenAnimationUtil loadingScreenAnimation = new LoadingScreenAnimationUtil();
  private static CarProfilesJsonLoaderUtil carProfileJsonLoader = new CarProfilesJsonLoaderUtil(
      carProfilePath);
  private static Gson gson = new GsonBuilder()
      .setPrettyPrinting()
      .create();
  
  public SetupService() {
    ConsoleInteractorUtil.clear();
    
    System.out.print(AnsiColorsUtil.WHITE.getCode());
  }
  
  public static Scanner getScanner() {
    return scanner;
  }
  
  public static LoadingScreenAnimationUtil getLoadingScreenAnimation() {
    return loadingScreenAnimation;
  }
  
  public static CarProfilesJsonLoaderUtil getCarProfileJsonLoader() {
    return carProfileJsonLoader;
  }
  
  public static Gson getGson() {
    return gson;
  }
  
  public static CarProfileModel getCarProfile() {
    return carProfile;
  }
  
  public static void setCarProfile(CarProfileModel carProfile) {
    SetupService.carProfile = carProfile;
  }
  
  public String getCarProfilePath() {
    return carProfilePath;
  }
}
