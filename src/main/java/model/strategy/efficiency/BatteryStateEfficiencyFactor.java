package model.strategy.efficiency;

public interface BatteryStateEfficiencyFactor extends EfficiencyFactor {

    double getStateOfCharge();

    double getBatteryTemperature();
} 