package model.strategy;

import model.CarProfile;
import model.ConsumptionProfile;

public class ConsumptionBasedRangeStrategy implements RangeCalculationStrategy {
    @Override
    public double calculateRange(CarProfile carProfile, CarProfile.EfficiencyMode efficiencyMode) {
        ConsumptionProfile consumptionProfile = carProfile.getConsumptionProfile();
        if (consumptionProfile == null) {
            throw new IllegalStateException("Consumption profile not set");
        }

        // Get average consumption across all speeds
        double averageConsumption = consumptionProfile.getAverageConsumption();
        
        // Apply efficiency mode factor
        double adjustedConsumption = averageConsumption * efficiencyMode.getConsumptionFactor();
        
        // Calculate range based on battery capacity and consumption
        double batteryCapacity = carProfile.getBatteryCapacity();
        
        // Range = (battery capacity in kWh * 100) / consumption per 100km
        return (batteryCapacity * 100) / adjustedConsumption;
    }
} 