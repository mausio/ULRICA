package org.ulrica.domain.service;

import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.BatteryProfile;

public class AcChargingCalculator {

    public static final double MIN_BATTERY_TEMPERATURE = -20.0;
    public static final double MAX_BATTERY_TEMPERATURE = 60.0;
    public static final double OPTIMAL_TEMPERATURE_MIN = 15.0;
    public static final double OPTIMAL_TEMPERATURE_MAX = 35.0;
    
    public static final int HOUSEHOLD_SOCKET = 1;
    public static final int CAMPING_SOCKET = 2;
    public static final int WALLBOX = 3;
    
    public static final double HOUSEHOLD_SOCKET_POWER_KW = 2.3;
    public static final double CAMPING_SOCKET_POWER_KW = 3.7;
    public static final double WALLBOX_POWER_KW = 11.0;
    

    public double getConnectorPower(int connectorType) {
        switch (connectorType) {
            case HOUSEHOLD_SOCKET:
                return HOUSEHOLD_SOCKET_POWER_KW;
            case CAMPING_SOCKET:
                return CAMPING_SOCKET_POWER_KW;
            case WALLBOX:
                return WALLBOX_POWER_KW;
            default:
                throw new IllegalArgumentException("Invalid connector type: " + connectorType);
        }
    }
    

    public String getConnectorName(int connectorType) {
        switch (connectorType) {
            case HOUSEHOLD_SOCKET:
                return "Household Socket";
            case CAMPING_SOCKET:
                return "Camping Socket";
            case WALLBOX:
                return "Wallbox";
            default:
                throw new IllegalArgumentException("Invalid connector type: " + connectorType);
        }
    }
    

    public AcChargingResult calculateChargingTime(
            CarProfile carProfile,
            int connectorType,
            double startingSocPercent,
            double targetSocPercent,
            double ambientTemperatureCelsius) {
        
        validateInputParameters(connectorType, startingSocPercent, targetSocPercent, ambientTemperatureCelsius);
        
        BatteryProfile batteryProfile = carProfile.getBatteryProfile();
        
        double remainingCapacityKwh = batteryProfile.getRemainingCapacityKwh();
        double energyToAddKwh = remainingCapacityKwh * (targetSocPercent - startingSocPercent) / 100.0;
        
        double temperatureEfficiency = calculateTemperatureEfficiency(ambientTemperatureCelsius);
        
        double efficiencyLoss = calculateEfficiencyLoss(connectorType);
        
        double connectorPowerKw = getConnectorPower(connectorType);
        double maxAcPowerKw = Math.min(batteryProfile.getMaxAcPowerKw(), carProfile.getMaxAcPowerKw());
        double effectivePowerKw = Math.min(connectorPowerKw, maxAcPowerKw);
        
        effectivePowerKw = effectivePowerKw * (1 - efficiencyLoss) * temperatureEfficiency;
        
        double chargingTimeHours = energyToAddKwh / effectivePowerKw;
        
        return new AcChargingResult(
                chargingTimeHours,
                energyToAddKwh,
                effectivePowerKw,
                efficiencyLoss * 100,
                temperatureEfficiency * 100,
                connectorType
        );
    }
    
    private double calculateTemperatureEfficiency(double ambientTemperatureCelsius) {
        if (ambientTemperatureCelsius < MIN_BATTERY_TEMPERATURE || ambientTemperatureCelsius > MAX_BATTERY_TEMPERATURE) {
            return 0.0;
        }
        
        if (ambientTemperatureCelsius >= OPTIMAL_TEMPERATURE_MIN && ambientTemperatureCelsius <= OPTIMAL_TEMPERATURE_MAX) {
            return 1.0; 
        }
        
        if (ambientTemperatureCelsius < OPTIMAL_TEMPERATURE_MIN) {
            return 0.7 + 0.3 * (ambientTemperatureCelsius - MIN_BATTERY_TEMPERATURE) / (OPTIMAL_TEMPERATURE_MIN - MIN_BATTERY_TEMPERATURE);
        } else {
            return 0.7 + 0.3 * (MAX_BATTERY_TEMPERATURE - ambientTemperatureCelsius) / (MAX_BATTERY_TEMPERATURE - OPTIMAL_TEMPERATURE_MAX);
        }
    }
    
    private double calculateEfficiencyLoss(int connectorType) {
        switch (connectorType) {
            case HOUSEHOLD_SOCKET:
                return 0.1; 
            case CAMPING_SOCKET:
                return 0.07; 
            case WALLBOX:
                return 0.05; 
            default:
                throw new IllegalArgumentException("Invalid connector type: " + connectorType);
        }
    }
    
    private void validateInputParameters(
            int connectorType,
            double startingSocPercent,
            double targetSocPercent,
            double ambientTemperatureCelsius) {
        
        if (connectorType < HOUSEHOLD_SOCKET || connectorType > WALLBOX) {
            throw new IllegalArgumentException("Invalid connector type: " + connectorType);
        }
        
        if (startingSocPercent < 0 || startingSocPercent > 100) {
            throw new IllegalArgumentException("Starting SoC must be between 0 and 100 percent");
        }
        
        if (targetSocPercent < 0 || targetSocPercent > 100) {
            throw new IllegalArgumentException("Target SoC must be between 0 and 100 percent");
        }
        
        if (targetSocPercent <= startingSocPercent) {
            throw new IllegalArgumentException("Target SoC must be greater than starting SoC");
        }
        
        if (ambientTemperatureCelsius < MIN_BATTERY_TEMPERATURE || ambientTemperatureCelsius > MAX_BATTERY_TEMPERATURE) {
            throw new IllegalArgumentException("Ambient temperature must be between " + 
                    MIN_BATTERY_TEMPERATURE + " and " + MAX_BATTERY_TEMPERATURE + " Celsius");
        }
    }
    
    public static class AcChargingResult {
        private final double chargingTimeHours;
        private final double energyRequiredKwh;
        private final double effectiveChargingPowerKw;
        private final double efficiencyLossPercent;
        private final double temperatureEfficiencyPercent;
        private final int connectorType;
        
        public AcChargingResult(
                double chargingTimeHours,
                double energyRequiredKwh,
                double effectiveChargingPowerKw,
                double efficiencyLossPercent,
                double temperatureEfficiencyPercent,
                int connectorType) {
            this.chargingTimeHours = chargingTimeHours;
            this.energyRequiredKwh = energyRequiredKwh;
            this.effectiveChargingPowerKw = effectiveChargingPowerKw;
            this.efficiencyLossPercent = efficiencyLossPercent;
            this.temperatureEfficiencyPercent = temperatureEfficiencyPercent;
            this.connectorType = connectorType;
        }

        public double getChargingTimeHours() {
            return chargingTimeHours;
        }

        public double getEnergyRequiredKwh() {
            return energyRequiredKwh;
        }

        public double getEffectiveChargingPowerKw() {
            return effectiveChargingPowerKw;
        }

        public double getEfficiencyLossPercent() {
            return efficiencyLossPercent;
        }

        public double getTemperatureEfficiencyPercent() {
            return temperatureEfficiencyPercent;
        }

        public int getConnectorType() {
            return connectorType;
        }
    }
} 