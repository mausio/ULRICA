package service;

import model.charging.ACConnectorType;

public class ACChargingCalculatorService {
    private static final double MIN_TEMP_FULL_POWER = -10.0;
    private static final double MAX_TEMP_FULL_POWER = 50.0;
    private static final double MIN_TEMP = -25.0;
    private static final double MIN_EFFICIENCY = 0.5;

    public String calculateChargingTime(
            double batteryCapacityKWh,
            double currentSoCPercent,
            double targetSoCPercent,
            double temperatureCelsius,
            ACConnectorType connectorType,
            double maxVehicleACChargingPowerKW
    ) {
        double effectiveMaxPower = Math.min(connectorType.getMaxPowerKW(), maxVehicleACChargingPowerKW);
        double temperatureEfficiency = calculateTemperatureEfficiency(temperatureCelsius);
        
        double baseEfficiency = connectorType.getEfficiency();
        double totalEfficiency = baseEfficiency * temperatureEfficiency;
        
        double soCDifference = (targetSoCPercent - currentSoCPercent) / 100.0;
        double energyRequired = batteryCapacityKWh * soCDifference;
        
        double effectivePower = effectiveMaxPower * totalEfficiency;
        double chargingTimeHours = energyRequired / effectivePower;
        
        int hours = (int) chargingTimeHours;
        int minutes = (int) ((chargingTimeHours - hours) * 60);

        return String.format(
            "Charging time: %d hours %d minutes%n" +
            "Energy required: %.1f kWh%n" +
            "Effective charging power: %.1f kW%n" +
            "Efficiency loss: %.1f%%%n" +
            "Temperature efficiency: %.1f%%",
            hours,
            minutes,
            energyRequired,
            effectivePower,
            (1 - totalEfficiency) * 100,
            temperatureEfficiency * 100
        );
    }

    private double calculateTemperatureEfficiency(double temperatureCelsius) {
        if (temperatureCelsius >= MIN_TEMP_FULL_POWER && temperatureCelsius <= MAX_TEMP_FULL_POWER) {
            return 1.0;
        }
        
        if (temperatureCelsius < MIN_TEMP) {
            return MIN_EFFICIENCY;
        }
        
        if (temperatureCelsius < MIN_TEMP_FULL_POWER) {
            double range = MIN_TEMP_FULL_POWER - MIN_TEMP;
            double offset = temperatureCelsius - MIN_TEMP;
            return MIN_EFFICIENCY + ((1.0 - MIN_EFFICIENCY) * (offset / range));
        }
        
        return 1.0;
    }
} 