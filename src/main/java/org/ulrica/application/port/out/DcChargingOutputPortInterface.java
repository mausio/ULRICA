package org.ulrica.application.port.out;

import org.ulrica.domain.service.DcChargingCalculator.DcChargingResult;

public interface DcChargingOutputPortInterface {

    void showDcChargingCalculatorForm();
    

    void showDcChargingResults(DcChargingResult result);
    

    void showError(String message);
} 