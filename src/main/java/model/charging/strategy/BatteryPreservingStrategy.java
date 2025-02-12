package model.charging.strategy;

import model.charging.ChargingProfile;

public class BatteryPreservingStrategy implements ChargingStrategy {
    private static final int CALCULATION_STEPS = 100;
    private static final double MIN_POWER_THRESHOLD = 0.1;
    private static final double POWER_REDUCTION_FACTOR = 0.7;
    private static final double MAX_SOC = 80.0;

    @Override
    public double calculateChargingTime(ChargingProfile profile, double startSoc, double targetSoc, double maxStationPower, double temperature) {
        if (startSoc >= targetSoc) {
            return 0.0;
        }

        double actualTargetSoc = Math.min(targetSoc, MAX_SOC);
        double totalTime = 0.0;
        double currentSoc = startSoc;
        double socStep = (actualTargetSoc - startSoc) / CALCULATION_STEPS;
        double batteryCapacity = profile.getBatteryCapacity();

        for (int i = 0; i < CALCULATION_STEPS; i++) {
            double basePower = Math.min(profile.getPowerAtSoc((int) currentSoc, temperature), maxStationPower);
            double reducedPower = basePower * POWER_REDUCTION_FACTOR;

            if (reducedPower < MIN_POWER_THRESHOLD) {
                return Double.POSITIVE_INFINITY;
            }

            double energyStep = batteryCapacity * socStep / 100.0;
            totalTime += (energyStep / reducedPower) * 60.0;
            currentSoc += socStep;
        }

        return totalTime;
    }

    @Override
    public String getName() {
        return "Battery Preserving";
    }

    @Override
    public String getDescription() {
        return "Optimizes charging for battery longevity by limiting maximum power and SoC to 80%";
    }
} 