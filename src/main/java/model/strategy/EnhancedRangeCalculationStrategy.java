package model.strategy;

import java.util.ArrayList;
import java.util.List;

import model.CarProfile;
import model.strategy.efficiency.EfficiencyFactor;

public class EnhancedRangeCalculationStrategy implements RangeCalculationStrategy {
    private final List<EfficiencyFactor> efficiencyFactors;

    public EnhancedRangeCalculationStrategy() {
        this.efficiencyFactors = new ArrayList<>();
    }

    public void addEfficiencyFactor(EfficiencyFactor factor) {
        efficiencyFactors.add(factor);
    }

    public void clearEfficiencyFactors() {
        efficiencyFactors.clear();
    }

    public List<EfficiencyFactor> getEfficiencyFactors() {
        return new ArrayList<>(efficiencyFactors);
    }

    @Override
    public double calculateRange(CarProfile carProfile, CarProfile.EfficiencyMode efficiencyMode) {
        double baseRange = carProfile.getWltpRange();
        double range = baseRange / efficiencyMode.getConsumptionFactor();
        
        double combinedFactor = efficiencyFactors.stream()
                .mapToDouble(EfficiencyFactor::calculateFactor)
                .reduce(1.0, (a, b) -> a * b);
        
        range /= combinedFactor;
        
        return range;
    }
} 