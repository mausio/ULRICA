package models;

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
  
  public CarProfileModel(String name, String manufacturer,
                         String model, Integer buildYear,
                         Double batterySize, Boolean hasHeatPump) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.model = model;
    this.buildYear = buildYear;
    this.batterySize = batterySize;
    this.hasHeatPump = hasHeatPump;
  }
  
  public void printOutCarProfile(CarProfileModel carProfile) {
  
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public Double getBatterySize() {
    return batterySize;
  }
  
  public void setBatterySize(Double batterySize) {
    this.batterySize = batterySize;
  }
  
  public ConsumptionProfileModel getConsumptionProfile() {
    return consumptionProfile;
  }
  
  public void setConsumptionProfile(
      ConsumptionProfileModel consumptionProfile) {
    this.consumptionProfile = consumptionProfile;
  }
  
  public Boolean getHasHeatPump() {
    return hasHeatPump;
  }
  
  public void setHasHeatPump(Boolean hasHeatPump) {
    this.hasHeatPump = hasHeatPump;
  }
  
  public String getManufacturer() {
    return manufacturer;
  }
  
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }
  
  public String getModel() {
    return model;
  }
  
  public void setModel(String model) {
    this.model = model;
  }
  
  public Integer getBuildYear() {
    return buildYear;
  }
  
  public void setBuildYear(Integer buildYear) {
    this.buildYear = buildYear;
  }
}
