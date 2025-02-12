package service;

import model.charging.ChargingProfile;

public class ChargingCalculatorService {
    private static final int CALCULATION_STEPS = 100;
    private static final double MIN_POWER_THRESHOLD = 0.1;
    private static final double POWER_REDUCTION_FACTOR = 0.7;
    private static final double BATTERY_WARMING_RATE = 10.0;
    private static final double MAX_BATTERY_TEMP = 35.0;
    private static final double OPTIMAL_TEMP = 25.0;

    public double calculateChargingTime(ChargingProfile profile, double startSoc, double targetSoc, double maxStationPower, double startTemperature) {
        if (startSoc >= targetSoc) {
            return 0.0;
        }

        double totalTime = 0.0;
        double currentSoc = startSoc;
        double currentTemp = startTemperature;
        double socStep = (targetSoc - startSoc) / CALCULATION_STEPS;
        double batteryCapacity = profile.getBatteryCapacity();
        double timeInHours = 0.0;

        for (int i = 0; i < CALCULATION_STEPS; i++) {
            // Calculate battery temperature based on charging time
            currentTemp = calculateBatteryTemperature(startTemperature, timeInHours);
            
            // Get base power and apply reduction factor for battery preservation
            double basePower = Math.min(profile.getPowerAtSoc((int) currentSoc, currentTemp), maxStationPower);
            double reducedPower = basePower * POWER_REDUCTION_FACTOR;

            if (reducedPower < MIN_POWER_THRESHOLD) {
                return Double.POSITIVE_INFINITY;
            }

            double energyStep = batteryCapacity * socStep / 100.0;
            double stepTimeMinutes = (energyStep / reducedPower) * 60.0;
            
            totalTime += stepTimeMinutes;
            timeInHours = totalTime / 60.0;
            currentSoc += socStep;
        }

        return totalTime;
    }

    private double calculateBatteryTemperature(double startTemp, double timeInHours) {
        double warming = timeInHours * BATTERY_WARMING_RATE;
        double targetTemp = Math.min(OPTIMAL_TEMP, MAX_BATTERY_TEMP);
        
        if (startTemp < targetTemp) {
            return Math.min(startTemp + warming, targetTemp);
        }
        return startTemp;
    }

    public String formatChargingTime(double minutes) {
        if (minutes == Double.POSITIVE_INFINITY) {
            return "Charging not possible";
        }

        long hours = (long) (minutes / 60);
        long remainingMinutes = (long) (minutes % 60);

        if (hours > 0) {
            return String.format("%dh %dm", hours, remainingMinutes);
        } else {
            return String.format("%dm", remainingMinutes);
        }
    }

    public double calculateEnergyToBeAdded(ChargingProfile profile, double startSoc, double targetSoc) {
        return profile.getBatteryCapacity() * (targetSoc - startSoc) / 100.0;
    }

    public String getChargingRecommendation(double startSoc, double targetSoc, double temperature, String batteryChemistry) {
        StringBuilder recommendation = new StringBuilder();
        
        if (startSoc < 20) {
            recommendation.append("Battery level is low. ");
        }

        if (temperature < 10) {
            recommendation.append("Charging will start slower due to low temperature, but battery will warm up during charging. ");
        } else if (temperature > 35) {
            recommendation.append("High temperature may limit charging power. ");
        }

        if (batteryChemistry.equalsIgnoreCase("LFP")) {
            recommendation.append("LFP battery can be safely charged to 100% if needed. ");
        } else if (batteryChemistry.equalsIgnoreCase("NMC")) {
            if (targetSoc > 90) {
                recommendation.append("Consider limiting charge to 80-90% for optimal battery longevity. ");
            }
        }

        return recommendation.toString().trim();
    }
} 