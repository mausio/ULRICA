package models;

public class CarProfile {
  private String name;
  private Float batterySize;
  private ConsumptionProfile consumptionProfile;
  private Boolean heatPump;
  
  public CarProfile(String name, ConsumptionProfile consumptionProfile,
                    Float batterySize, Boolean heatPump) {
    this.name = name;
    this.consumptionProfile = consumptionProfile;
    this.batterySize = batterySize;
    this.heatPump = heatPump;
  }
  
  public String getName() {
    return name;
  }
  
  public Float getBatterySize() {
    return batterySize;
  }
  
  public ConsumptionProfile getConsumptionProfile() {
    return consumptionProfile;
  }
  
  public Boolean getHeatPump() {
    return heatPump;
  }
}
