package model;

import java.util.ArrayList;
import java.util.List;

public class ChargingProfile {
    private List<ChargingCurvePoint> chargingCurve;
    private double maxDcPower;
    private double maxAcPower;

    public static class ChargingCurvePoint {
        private double batteryPercentage;
        private double chargingPower;

        public ChargingCurvePoint(double batteryPercentage, double chargingPower) {
            this.batteryPercentage = batteryPercentage;
            this.chargingPower = chargingPower;
        }

        public double getBatteryPercentage() { return batteryPercentage; }
        public void setBatteryPercentage(double batteryPercentage) { this.batteryPercentage = batteryPercentage; }

        public double getChargingPower() { return chargingPower; }
        public void setChargingPower(double chargingPower) { this.chargingPower = chargingPower; }
    }

    public ChargingProfile(double maxDcPower, double maxAcPower) {
        this.chargingCurve = new ArrayList<>();
        this.maxDcPower = maxDcPower;
        this.maxAcPower = maxAcPower;
    }

    public void addChargingPoint(double batteryPercentage, double chargingPower) {
        // Ensure charging power doesn't exceed maximum DC power
        double actualPower = Math.min(chargingPower, maxDcPower);
        chargingCurve.add(new ChargingCurvePoint(batteryPercentage, actualPower));
        // Sort by battery percentage to maintain curve order
        chargingCurve.sort((a, b) -> Double.compare(a.getBatteryPercentage(), b.getBatteryPercentage()));
    }

    public double getChargingPowerAtPercentage(double batteryPercentage) {
        if (chargingCurve.isEmpty()) {
            return 0.0;
        }

        // Find the closest points in the curve
        ChargingCurvePoint lower = null;
        ChargingCurvePoint upper = null;

        for (ChargingCurvePoint point : chargingCurve) {
            if (point.getBatteryPercentage() <= batteryPercentage) {
                lower = point;
            } else {
                upper = point;
                break;
            }
        }

        // Handle edge cases
        if (lower == null) {
            return chargingCurve.get(0).getChargingPower();
        }
        if (upper == null) {
            return lower.getChargingPower();
        }

        // Linear interpolation between points
        double percentage = (batteryPercentage - lower.getBatteryPercentage()) / 
                          (upper.getBatteryPercentage() - lower.getBatteryPercentage());
        return lower.getChargingPower() + percentage * (upper.getChargingPower() - lower.getChargingPower());
    }

    // Getters and setters
    public List<ChargingCurvePoint> getChargingCurve() {
        return new ArrayList<>(chargingCurve);
    }

    public void setChargingCurve(List<ChargingCurvePoint> chargingCurve) {
        this.chargingCurve = new ArrayList<>(chargingCurve);
    }

    public double getMaxDcPower() { return maxDcPower; }
    public void setMaxDcPower(double maxDcPower) { this.maxDcPower = maxDcPower; }

    public double getMaxAcPower() { return maxAcPower; }
    public void setMaxAcPower(double maxAcPower) { this.maxAcPower = maxAcPower; }
} 