package model.route.charging;

import model.route.ChargingStation;

public class MinimalChargingStrategy implements ChargingStrategy {
    private static final double SAFETY_BUFFER_PERCENTAGE = 10.0;

    @Override
    public double calculateTargetSoC(double currentSoC, double remainingDistance, double totalDistance, ChargingStation station) {
        // Calculate required SoC based on remaining distance
        double requiredSoC = (remainingDistance / totalDistance) * 100.0;
        
        // Add safety buffer but don't exceed 100%
        return Math.min(100.0, requiredSoC + SAFETY_BUFFER_PERCENTAGE);
    }

    @Override
    public String getStrategyName() {
        return "Minimal Charging";
    }

    @Override
    public String getStrategyDescription() {
        return "Charges only enough to reach the next charging station with a small safety buffer";
    }
} 