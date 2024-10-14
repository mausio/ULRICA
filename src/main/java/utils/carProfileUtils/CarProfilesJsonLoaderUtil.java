package utils.carProfileUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import exception.LoadingException;
import models.CarProfileModel;
import service.SetupService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CarProfilesJsonLoaderUtil implements Runnable{
  private final String filePath;
  private CarProfileModel[] carProfiles;
  
  public CarProfilesJsonLoaderUtil(String filePath) {
    this.filePath = filePath;
  }
  
  public CarProfileModel[] getCarProfiles() {
    return carProfiles;
  }
  
  private void loadJsonData() throws LoadingException{
    File file = new File(filePath);
    
    if(!file.exists()){
      throw new LoadingException("Error: JSON file not found at " + filePath);
    }
    
    try (FileReader reader = new FileReader(file)) {
      Gson gson = SetupService.getGson();
      
      carProfiles = gson.fromJson(reader, CarProfileModel[].class);
    } catch (IOException e) {
      throw new LoadingException("Error reading the JSON file: " + e.getMessage());
    } catch (JsonSyntaxException | JsonIOException e) {
      throw new LoadingException("Error parsing the JSON data: " + e.getMessage());
    }
  }
  
  @Override public void run() {
    try {
      loadJsonData();
    } catch (LoadingException e) {
      carProfiles = null;
    }
  }
}
