package org.ulrica.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.RangeParameters;
import org.ulrica.domain.valueobject.RangeResult;

public class RangeCalculatorService {
    private final List<RangeCalculationStrategyInterface> strategies;
    private RangeCalculationStrategyInterface defaultStrategy;

    public RangeCalculatorService() {
        this.strategies = new ArrayList<>();
        
        // Initialize with standard strategies
        WltpBasedRangeCalculationStrategy wltpStrategy = new WltpBasedRangeCalculationStrategy();
        ConsumptionBasedRangeCalculationStrategy consumptionStrategy = new ConsumptionBasedRangeCalculationStrategy();
        
        this.strategies.add(wltpStrategy);
        this.strategies.add(consumptionStrategy);
        
        // Set default strategy
        this.defaultStrategy = consumptionStrategy;
    }
    
    public void addStrategy(RangeCalculationStrategyInterface strategy) {
        Objects.requireNonNull(strategy, "Strategy cannot be null");
        strategies.add(strategy);
    }
    
    public void setDefaultStrategy(RangeCalculationStrategyInterface strategy) {
        Objects.requireNonNull(strategy, "Strategy cannot be null");
        if (!strategies.contains(strategy)) {
            strategies.add(strategy);
        }
        this.defaultStrategy = strategy;
    }
    
    public List<RangeCalculationStrategyInterface> getAvailableStrategies() {
        return new ArrayList<>(strategies);
    }
    
    public RangeCalculationStrategyInterface getDefaultStrategy() {
        return defaultStrategy;
    }
    
    public RangeResult calculateRange(CarProfile carProfile, RangeParameters parameters) {
        Objects.requireNonNull(carProfile, "Car profile cannot be null");
        Objects.requireNonNull(parameters, "Range parameters cannot be null");
        
        return defaultStrategy.calculateRange(carProfile, parameters);
    }
    
    public RangeResult calculateRangeWithStrategy(CarProfile carProfile, RangeParameters parameters, RangeCalculationStrategyInterface strategy) {
        Objects.requireNonNull(carProfile, "Car profile cannot be null");
        Objects.requireNonNull(parameters, "Range parameters cannot be null");
        Objects.requireNonNull(strategy, "Strategy cannot be null");
        
        return strategy.calculateRange(carProfile, parameters);
    }
} 