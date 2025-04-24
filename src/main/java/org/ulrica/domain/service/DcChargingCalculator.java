package org.ulrica.domain.service;

import java.util.Optional;

import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.BatteryProfile;
import org.ulrica.domain.valueobject.ChargingCurve;

public class DcChargingCalculator {

    public static final double MIN_BATTERY_TEMPERATURE = -20.0;
    public static final double MAX_BATTERY_TEMPERATURE = 60.0;
    public static final double OPTIMAL_TEMPERATURE_MIN = 15.0;
    public static final double OPTIMAL_TEMPERATURE_MAX = 35.0;


    public DcChargingResult calculateChargingTime(
            CarProfile carProfile,
            double startingSocPercent,
            double targetSocPercent,
            double maxStationPowerKw,
            double ambientTemperatureCelsius) {
        
        validateInputParameters(startingSocPercent, targetSocPercent, maxStationPowerKw, ambientTemperatureCelsius);
        
        BatteryProfile batteryProfile = carProfile.getBatteryProfile();
        Optional<ChargingCurve> chargingCurveOptional = carProfile.getChargingCurve();
        
        double remainingCapacityKwh = batteryProfile.getRemainingCapacityKwh();
        double energyToAddKwh = remainingCapacityKwh * (targetSocPercent - startingSocPercent) / 100.0;
        
        double batteryTemperature = calculateBatteryTemperature(ambientTemperatureCelsius);
        double temperatureEfficiencyFactor = calculateTemperatureEfficiencyFactor(batteryTemperature);
        
        double powerReductionPercent = calculatePowerReductionPercent(startingSocPercent, targetSocPercent);
        
        double maxPower = Math.min(batteryProfile.getMaxDcPowerKw(), maxStationPowerKw);
        double effectivePower = maxPower * temperatureEfficiencyFactor * (1 - powerReductionPercent / 100.0);
        
        if (chargingCurveOptional != null && chargingCurveOptional.isPresent()) {
            ChargingCurve chargingCurve = chargingCurveOptional.get();
            return calculateWithChargingCurve(
                    chargingCurve, 
                    startingSocPercent, 
                    targetSocPercent, 
                    energyToAddKwh, 
                    temperatureEfficiencyFactor, 
                    powerReductionPercent, 
                    maxStationPowerKw,
                    batteryProfile,
                    batteryTemperature);
        } else {
            double chargingTimeHours = energyToAddKwh / effectivePower;
            
            return new DcChargingResult(
                    chargingTimeHours,
                    batteryProfile.getCapacityKwh(),
                    energyToAddKwh,
                    batteryProfile.getType(),
                    ambientTemperatureCelsius,
                    batteryTemperature,
                    powerReductionPercent,
                    effectivePower
            );
        }
    }
    
    private DcChargingResult calculateWithChargingCurve(
            ChargingCurve chargingCurve,
            double startingSocPercent,
            double targetSocPercent,
            double energyToAddKwh,
            double temperatureEfficiencyFactor,
            double powerReductionPercent,
            double maxStationPowerKw,
            BatteryProfile batteryProfile,
            double batteryTemperature) {
        
        double totalTime = 0;
        double stepSize = 1.0; // 1% SoC steps
        
        for (double soc = startingSocPercent; soc < targetSocPercent; soc += stepSize) {
            double powerAtSoc = chargingCurve.getChargingPowerAt(soc);
            double limitedPower = Math.min(powerAtSoc, maxStationPowerKw);
            double effectivePower = limitedPower * temperatureEfficiencyFactor * (1 - powerReductionPercent / 100.0);
            
            double energyForStep = batteryProfile.getRemainingCapacityKwh() * stepSize / 100.0;
            
            double timeForStep = energyForStep / effectivePower;
            totalTime += timeForStep;
        }
        
        double avgPower = energyToAddKwh / totalTime;
        
        return new DcChargingResult(
                totalTime,
                batteryProfile.getCapacityKwh(),
                energyToAddKwh,
                batteryProfile.getType(),
                batteryTemperature - 5.0, 
                batteryTemperature,
                powerReductionPercent,
                avgPower
        );
    }
    
    private double calculateBatteryTemperature(double ambientTemperatureCelsius) {
        return Math.min(MAX_BATTERY_TEMPERATURE, ambientTemperatureCelsius + 5.9);
    }
    
    private double calculateTemperatureEfficiencyFactor(double batteryTemperatureCelsius) {
        if (batteryTemperatureCelsius < MIN_BATTERY_TEMPERATURE || batteryTemperatureCelsius > MAX_BATTERY_TEMPERATURE) {
            return 0.0;
        }
        
        if (batteryTemperatureCelsius >= OPTIMAL_TEMPERATURE_MIN && batteryTemperatureCelsius <= OPTIMAL_TEMPERATURE_MAX) {
            return 1.0;
        }
        
        if (batteryTemperatureCelsius < OPTIMAL_TEMPERATURE_MIN) {
            return 0.5 + 0.5 * (batteryTemperatureCelsius - MIN_BATTERY_TEMPERATURE) / (OPTIMAL_TEMPERATURE_MIN - MIN_BATTERY_TEMPERATURE);
        } else {
            return 0.5 + 0.5 * (MAX_BATTERY_TEMPERATURE - batteryTemperatureCelsius) / (MAX_BATTERY_TEMPERATURE - OPTIMAL_TEMPERATURE_MAX);
        }
    }
    
    private double calculatePowerReductionPercent(double startingSocPercent, double targetSocPercent) {
        if (targetSocPercent > 80) {
            return 60.0; 
        } else if (targetSocPercent > 60) {
            return 30.0; 
        } else {
            return 5.0; 
        }
    }
    
    private void validateInputParameters(
            double startingSocPercent, 
            double targetSocPercent, 
            double maxStationPowerKw,
            double ambientTemperatureCelsius) {
        if (startingSocPercent < 0 || startingSocPercent > 100) {
            throw new IllegalArgumentException("Starting SoC must be between 0 and 100 percent");
        }
        
        if (targetSocPercent < 0 || targetSocPercent > 100) {
            throw new IllegalArgumentException("Target SoC must be between 0 and 100 percent");
        }
        
        if (targetSocPercent <= startingSocPercent) {
            throw new IllegalArgumentException("Target SoC must be greater than starting SoC");
        }
        
        if (maxStationPowerKw <= 0) {
            throw new IllegalArgumentException("Maximum station power must be positive");
        }
        
        if (ambientTemperatureCelsius < MIN_BATTERY_TEMPERATURE || ambientTemperatureCelsius > MAX_BATTERY_TEMPERATURE) {
            throw new IllegalArgumentException("Ambient temperature must be between " + 
                    MIN_BATTERY_TEMPERATURE + " and " + MAX_BATTERY_TEMPERATURE + " Celsius");
        }
    }
    
    public static class DcChargingResult {
        private final double chargingTimeHours;
        private final double batteryCapacityKwh;
        private final double energyToAddKwh;
        private final org.ulrica.domain.valueobject.BatteryType batteryType;
        private final double startTemperatureCelsius;
        private final double endTemperatureCelsius;
        private final double powerReductionPercent;
        private final double effectivePowerKw;
        
        public DcChargingResult(
                double chargingTimeHours,
                double batteryCapacityKwh,
                double energyToAddKwh,
                org.ulrica.domain.valueobject.BatteryType batteryType,
                double startTemperatureCelsius,
                double endTemperatureCelsius,
                double powerReductionPercent,
                double effectivePowerKw) {
            this.chargingTimeHours = chargingTimeHours;
            this.batteryCapacityKwh = batteryCapacityKwh;
            this.energyToAddKwh = energyToAddKwh;
            this.batteryType = batteryType;
            this.startTemperatureCelsius = startTemperatureCelsius;
            this.endTemperatureCelsius = endTemperatureCelsius;
            this.powerReductionPercent = powerReductionPercent;
            this.effectivePowerKw = effectivePowerKw;
        }

        public double getChargingTimeHours() {
            return chargingTimeHours;
        }

        public double getBatteryCapacityKwh() {
            return batteryCapacityKwh;
        }

        public double getEnergyToAddKwh() {
            return energyToAddKwh;
        }

        public org.ulrica.domain.valueobject.BatteryType getBatteryType() {
            return batteryType;
        }

        public double getStartTemperatureCelsius() {
            return startTemperatureCelsius;
        }

        public double getEndTemperatureCelsius() {
            return endTemperatureCelsius;
        }

        public double getPowerReductionPercent() {
            return powerReductionPercent;
        }

        public double getEffectivePowerKw() {
            return effectivePowerKw;
        }
    }
} 