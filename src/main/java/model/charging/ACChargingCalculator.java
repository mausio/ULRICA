package model.charging;

public class ACChargingCalculator {
    private static final double MIN_EFFICIENT_TEMP = -10.0;
    private static final double MAX_EFFICIENT_TEMP = 50.0;
    private static final double MIN_TEMP = -25.0;

    public static class ChargingResult {
        private final double chargingTimeHours;
        private final double energyRequired;
        private final double effectivePowerKW;
        private final double efficiencyLoss;

        public ChargingResult(double chargingTimeHours, double energyRequired, double effectivePowerKW, double efficiencyLoss) {
            this.chargingTimeHours = chargingTimeHours;
            this.energyRequired = energyRequired;
            this.effectivePowerKW = effectivePowerKW;
            this.efficiencyLoss = efficiencyLoss;
        }

        public double chargingTimeHours() { return chargingTimeHours; }
        public double energyRequired() { return energyRequired; }
        public double effectivePowerKW() { return effectivePowerKW; }
        public double efficiencyLoss() { return efficiencyLoss; }
    }

    public ChargingResult calculateChargingTime(
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
        double efficiencyLoss = 1 - totalEfficiency;

        return new ChargingResult(
            chargingTimeHours,
            energyRequired,
            effectivePower,
            efficiencyLoss
        );
    }

    private double calculateTemperatureEfficiency(double temperatureCelsius) {
        if (temperatureCelsius >= MIN_EFFICIENT_TEMP && temperatureCelsius <= MAX_EFFICIENT_TEMP) {
            return 1.0;
        }
        
        if (temperatureCelsius < MIN_TEMP) {
            return 0.5;
        }
        
        if (temperatureCelsius < MIN_EFFICIENT_TEMP) {
            double range = MIN_EFFICIENT_TEMP - MIN_TEMP;
            double offset = temperatureCelsius - MIN_TEMP;
            return 0.5 + (0.5 * (offset / range));
        }
        
        return 1.0;
    }
} 