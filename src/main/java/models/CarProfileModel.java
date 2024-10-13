package models;

import utils.InputCleanerUtil;

import java.util.Scanner;

public class CarProfileModel {
  
  private String name;
  private String manufacturer;
  private String model;
  private Integer buildYear;
  private Double batterySize;
  private ConsumptionProfileModel consumptionProfile;
  private Boolean hasHeatPump;
  
  public CarProfileModel() {
    this.name = "Sir Charge-A-Lot";
    this.manufacturer = "Opel";
    this.model = "Corsa-e";
    this.buildYear = 2020;
    this.batterySize = 44.0;
    this.hasHeatPump = false;
  }
  
  public CarProfileModel(String name, String manufacturer, String model,
                         Integer buildYear, Double batterySize, Boolean hasHeatPump) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.model = model;
    this.buildYear = buildYear;
    this.batterySize = batterySize;
    this.hasHeatPump = hasHeatPump;
  }
  
  public void createCarProfile(Scanner scanner) {
    System.out.print("How should the car be named? (e.g.\"Sir Charge-A-Lot\")" + ": ");
    String input = scanner.nextLine();
    if (!input.isEmpty()) {
      this.name = InputCleanerUtil.cleanWhitespacesAround(input);
    }
    
    System.out.print("Which manufacturer is it from? (e.g.\"Opel\"): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      this.manufacturer = InputCleanerUtil.cleanWhitespacesAround(input);
    }
    
    System.out.print("Which model is it? (e.g.\"Corsa-e\"): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      this.model = InputCleanerUtil.cleanWhitespacesAround(input);
    }
    
    System.out.print("When was it build? (e.g.\"2020\"): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      this.buildYear = InputCleanerUtil.cleanIntegerFromCharacters(input);
    }
    
    System.out.print("What is the usable size of the battery? (in kWh): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      this.batterySize = InputCleanerUtil.cleanDoubleFromCharacters(input);
    }
    
    System.out.print("Does your car have a heatpump? (type: y/n): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      this.hasHeatPump = InputCleanerUtil.formatYesOrNoToBoolean(input);
    }
  }
  
  public void setConsumptionProfile(ConsumptionProfileModel consumptionProfile) {
    this.consumptionProfile = consumptionProfile;
  }
  
  public void printOutCarProfile(CarProfileModel carProfile) {
  
  }
  
  public String getName() {
    return name;
  }
  
  public Double getBatterySize() {
    return batterySize;
  }
  
  public ConsumptionProfileModel getConsumptionProfile() {
    return consumptionProfile;
  }
  
  public Boolean getHasHeatPump() {
    return hasHeatPump;
  }
  
  public String getManufacturer() {
    return manufacturer;
  }
  
  public String getModel() {
    return model;
  }
  
  public Integer getBuildYear() {
    return buildYear;
  }
}
