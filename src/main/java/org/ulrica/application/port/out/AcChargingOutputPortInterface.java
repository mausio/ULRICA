package org.ulrica.application.port.out;

import org.ulrica.domain.service.AcChargingCalculator.AcChargingResult;

public interface AcChargingOutputPortInterface {
    

    void showAcChargingCalculatorForm();
    

    void showAcChargingResults(AcChargingResult result);
    
 
    void showError(String message);
} 