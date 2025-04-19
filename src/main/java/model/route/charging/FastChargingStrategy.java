package model.route.charging;

import model.route.ChargingStation;

public class FastChargingStrategy implements ChargingStrategy {
    private static final double MIN_CHARGE_PERCENTAGE = 80.0;
    private static final double FAST_CHARGE_THRESHOLD = 150.0;

    @Override
    public double calculateTargetSoC(double currentSoC, double remainingDistance, double totalDistance, ChargingStation station) {
        // If the station supports fast charging, charge to at least 80%
        if (station.getMaxPower() >= FAST_CHARGE_THRESHOLD) {
            return Math.max(MIN_CHARGE_PERCENTAGE, 
                Math.min(100.0, (remainingDistance / totalDistance) * 100.0 + 20.0));
        }
        
        // For slower chargers, charge fully to minimize stops
        return 100.0;
    }

    @Override
    public String getStrategyName() {
        return "Fast Charging";
    }

    @Override
    public String getStrategyDescription() {
        return "Prioritizes fast charging at high-power stations, charging to 80% for optimal charging speed";
    }
} 