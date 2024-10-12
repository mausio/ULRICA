package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.CarProfile;
import models.ConsumptionProfile;
import utils.SetUp;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    CarProfile carProfile = new CarProfile();
    ConsumptionProfile consumptionProfile = new ConsumptionProfile();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    SetUp.initial();
    
    carProfile.setConsumptionProfile(consumptionProfile);
    
    consumptionProfile.createConsumptionProfile(scanner);
    
    consumptionProfile.estimateConsumption(80.0);
//    String jsonCarProfile = gson.toJson(carProfile);
//    System.out.println("\n" + jsonCarProfile);
  }
}
