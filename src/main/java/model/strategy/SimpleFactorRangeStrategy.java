package model.strategy;

import model.CarProfile;

public class SimpleFactorRangeStrategy implements RangeCalculationStrategy {
    @Override
    public double calculateRange(CarProfile carProfile, CarProfile.EfficiencyMode efficiencyMode) {
        return carProfile.getWltpRange() / efficiencyMode.getConsumptionFactor();
    }
} 