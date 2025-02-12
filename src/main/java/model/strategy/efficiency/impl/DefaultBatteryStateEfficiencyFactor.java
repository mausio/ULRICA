package model.strategy.efficiency.impl;

import model.strategy.efficiency.BatteryStateEfficiencyFactor;

public class DefaultBatteryStateEfficiencyFactor implements BatteryStateEfficiencyFactor {
    private final double stateOfCharge;
    private final double batteryTemperature;

    public DefaultBatteryStateEfficiencyFactor(double stateOfCharge, double batteryTemperature) {
        if (stateOfCharge < 0 || stateOfCharge > 100) {
            throw new IllegalArgumentException("State of charge must be between 0 and 100");
        }
        this.stateOfCharge = stateOfCharge;
        this.batteryTemperature = batteryTemperature;
    }

    @Override
    public double getStateOfCharge() {
        return stateOfCharge;
    }

    @Override
    public double getBatteryTemperature() {
        return batteryTemperature;
    }

    @Override
    public double calculateFactor() {
        double factor = 1.0;
        
        if (stateOfCharge < 20) {
            factor *= 1 + (20 - stateOfCharge) * 0.01;
        } else if (stateOfCharge > 80) {
            factor *= 1 + (stateOfCharge - 80) * 0.005;
        }
        
        if (batteryTemperature < 15) {
            factor *= 1 + (15 - batteryTemperature) * 0.02; 
        } else if (batteryTemperature > 35) {
            factor *= 1 + (batteryTemperature - 35) * 0.01;
        }
        
        return factor;
    }
} 