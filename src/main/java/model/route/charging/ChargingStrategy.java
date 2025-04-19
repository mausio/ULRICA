package model.route.charging;

import model.route.ChargingStation;

public interface ChargingStrategy {
    double calculateTargetSoC(double currentSoC, double remainingDistance, double totalDistance, ChargingStation station);
    String getStrategyName();
    String getStrategyDescription();
} 