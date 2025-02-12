package model.charging.strategy;

import model.charging.ChargingProfile;

public interface ChargingStrategy {
    double calculateChargingTime(ChargingProfile profile, double startSoc, double targetSoc, double maxStationPower, double temperature);
    String getName();
    String getDescription();
} 