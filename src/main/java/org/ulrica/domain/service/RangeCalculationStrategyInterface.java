package org.ulrica.domain.service;

import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.valueobject.RangeParameters;
import org.ulrica.domain.valueobject.RangeResult;

public interface RangeCalculationStrategyInterface {
    
    RangeResult calculateRange(CarProfile carProfile, RangeParameters parameters);
    
    String getName();
    
    String getDescription();
} 