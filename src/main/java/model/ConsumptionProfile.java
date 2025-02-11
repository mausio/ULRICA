package model;

import java.util.HashMap;
import java.util.Map;

public class ConsumptionProfile {
    private Map<Integer, Double> speedConsumption; // Speed in km/h, consumption in kWh/100km

    public ConsumptionProfile() {
        this.speedConsumption = new HashMap<>();
    }

    public void addConsumptionValue(int speed, double consumption) {
        speedConsumption.put(speed, consumption);
    }

    public Double getConsumptionAtSpeed(int speed) {
        return speedConsumption.get(speed);
    }

    public Map<Integer, Double> getAllConsumptionValues() {
        return new HashMap<>(speedConsumption);
    }

    public void setSpeedConsumption(Map<Integer, Double> speedConsumption) {
        this.speedConsumption = new HashMap<>(speedConsumption);
    }
} 