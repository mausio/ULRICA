package utils.carProfileUtils;

import models.CarProfileModel;

public class CarProfileJsonSaverUtil implements Runnable {
  private final String filePath;
  private CarProfileModel[] carProfiles;
  private CarProfileModel carProfile;
  
  public CarProfileJsonSaverUtil(String filePath) {
    this.filePath = filePath;
  }
  
  public void setCarProfiles(CarProfileModel[] carProfiles) {
    this.carProfiles = carProfiles;
  }
  
  public void setCarProfile(CarProfileModel carProfile) {
    this.carProfile = carProfile;
  }
  
  @Override public void run() {
    //TODO: Make a util out of this
    //TODO: Add carProfile to CarProfileModel[] if it is not in the array
    
  }
}
