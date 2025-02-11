package model.strategy;

import model.CarProfile;

public interface RangeCalculationStrategy {
    /**
     * Calculate the range for a given car profile and efficiency mode
     * @param carProfile The car profile to calculate range for
     * @param efficiencyMode The efficiency mode to use for calculation
     * @return The calculated range in kilometers
     */
    double calculateRange(CarProfile carProfile, CarProfile.EfficiencyMode efficiencyMode);
} 