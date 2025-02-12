package model.charging.strategy;

import model.charging.ChargingProfile;

public class FastChargingStrategy implements ChargingStrategy {
    private static final int CALCULATION_STEPS = 100;
    private static final double MIN_POWER_THRESHOLD = 0.1;

    @Override
    public double calculateChargingTime(ChargingProfile profile, double startSoc, double targetSoc, double maxStationPower, double temperature) {
        if (startSoc >= targetSoc) {
            return 0.0;
        }

        double totalTime = 0.0;
        double currentSoc = startSoc;
        double socStep = (targetSoc - startSoc) / CALCULATION_STEPS;
        double batteryCapacity = profile.getBatteryCapacity();

        for (int i = 0; i < CALCULATION_STEPS; i++) {
            double power = Math.min(profile.getPowerAtSoc((int) currentSoc, temperature), maxStationPower);

            if (power < MIN_POWER_THRESHOLD) {
                return Double.POSITIVE_INFINITY;
            }

            double energyStep = batteryCapacity * socStep / 100.0;
            totalTime += (energyStep / power) * 60.0;
            currentSoc += socStep;
        }

        return totalTime;
    }

    @Override
    public String getName() {
        return "Fast Charging";
    }

    @Override
    public String getDescription() {
        return "Maximizes charging speed using the highest available power, may impact battery longevity";
    }
} 