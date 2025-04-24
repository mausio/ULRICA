package org.ulrica.application.usecase;

import org.ulrica.application.port.in.CalculateAcChargingUseCaseInterface;
import org.ulrica.application.port.out.AcChargingOutputPortInterface;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.service.AcChargingCalculator;
import org.ulrica.domain.service.AcChargingCalculator.AcChargingResult;
import org.ulrica.domain.service.ProfileSelectionService;


public class CalculateAcChargingInteractor implements CalculateAcChargingUseCaseInterface {
    
    private final ProfileSelectionService profileSelectionService;
    private final AcChargingCalculator acChargingCalculator;
    private final AcChargingOutputPortInterface outputPort;

    public CalculateAcChargingInteractor(
            ProfileSelectionService profileSelectionService,
            AcChargingCalculator acChargingCalculator,
            AcChargingOutputPortInterface outputPort) {
        this.profileSelectionService = profileSelectionService;
        this.acChargingCalculator = acChargingCalculator;
        this.outputPort = outputPort;
    }
    
    @Override
    public boolean calculateAcChargingTime(
            int connectorType,
            double startingSocPercent,
            double targetSocPercent,
            double ambientTemperatureCelsius) {
        
        try {
            CarProfile selectedProfile = profileSelectionService.getSelectedProfile();
            if (selectedProfile == null) {
                outputPort.showError("No car profile selected");
                return false;
            }
            
            AcChargingResult result = acChargingCalculator.calculateChargingTime(
                    selectedProfile,
                    connectorType,
                    startingSocPercent,
                    targetSocPercent,
                    ambientTemperatureCelsius);
            
            outputPort.showAcChargingResults(result);
            return true;
        } catch (IllegalArgumentException e) {
            outputPort.showError(e.getMessage());
            return false;
        } catch (Exception e) {
            outputPort.showError("An error occurred: " + e.getMessage());
            return false;
        }
    }
} 