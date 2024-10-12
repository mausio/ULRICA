package models;

import utils.CleanInput;

import java.util.Scanner;

public class CarProfile {
  
  private String name;
  private String manufacturer;
  private String model;
  private Integer buildYear;
  private Double batterySize;
  private ConsumptionProfile consumptionProfile;
  private Boolean hasHeatPump;
  
  public CarProfile() {
    this.name = "Sir Charge-A-Lot";
    this.manufacturer = "Opel";
    this.model = "Corsa-e";
    this.buildYear = 2020;
    this.batterySize = 44.0;
    this.hasHeatPump = false;
  }
  
  public CarProfile(String name, String manufacturer, String model,
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
      this.name = CleanInput.cleanWhitespacesAround(input);
    }
    
    System.out.print("Which manufacturer is it from? (e.g.\"Opel\"): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      this.manufacturer = CleanInput.cleanWhitespacesAround(input);
    }
    
    System.out.print("Which model is it? (e.g.\"Corsa-e\"): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      this.model = CleanInput.cleanWhitespacesAround(input);
    }
    
    System.out.print("When was it build? (e.g.\"2020\"): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      this.buildYear = CleanInput.cleanIntegerFromCharacters(input);
    }
    
    System.out.print("What is the usable size of the battery? (in kWh): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      this.batterySize = CleanInput.cleanDoubleFromCharacters(input);
    }
    
    System.out.print("Does your car have a heatpump? (type: y/n): ");
    input = scanner.nextLine();
    if (!input.isEmpty()) {
      this.hasHeatPump = CleanInput.formatYesOrNoToBoolean(input);
    }
  }
  
  public void setConsumptionProfile(ConsumptionProfile consumptionProfile) {
    this.consumptionProfile = consumptionProfile;
  }
  
  public void printOutCarProfile(CarProfile carProfile) {
  
  }
  
  public String getName() {
    return name;
  }
  
  public Double getBatterySize() {
    return batterySize;
  }
  
  public ConsumptionProfile getConsumptionProfile() {
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
