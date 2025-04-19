package model.route.charging;

import model.route.ChargingStation;

public class BatteryOptimizingStrategy implements ChargingStrategy {
    private static final double OPTIMAL_MAX_SOC = 80.0;
    private static final double OPTIMAL_MIN_SOC = 20.0;
    private static final double POWER_REDUCTION_THRESHOLD = 150.0;

    @Override
    public double calculateTargetSoC(double currentSoC, double remainingDistance, double totalDistance, ChargingStation station) {
        // Calculate required SoC based on remaining distance
        double requiredSoC = (remainingDistance / totalDistance) * 100.0;
        
        // For high-power chargers, limit charging to 80% to protect battery
        if (station.getMaxPower() >= POWER_REDUCTION_THRESHOLD) {
            return Math.min(OPTIMAL_MAX_SOC, Math.max(requiredSoC, OPTIMAL_MIN_SOC));
        }
        
        // For slower chargers, we can charge up to 90% if needed
        return Math.min(90.0, Math.max(requiredSoC, OPTIMAL_MIN_SOC));
    }

    @Override
    public String getStrategyName() {
        return "Battery Optimizing";
    }

    @Override
    public String getStrategyDescription() {
        return "Prioritizes battery health by maintaining optimal charge levels and reducing high-power charging stress";
    }
} 