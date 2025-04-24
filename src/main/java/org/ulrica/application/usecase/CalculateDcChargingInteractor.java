package org.ulrica.application.usecase;

import org.ulrica.application.port.in.CalculateDcChargingUseCaseInterface;
import org.ulrica.application.port.out.DcChargingOutputPortInterface;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.service.DcChargingCalculator;
import org.ulrica.domain.service.DcChargingCalculator.DcChargingResult;
import org.ulrica.domain.service.ProfileSelectionService;


public class CalculateDcChargingInteractor implements CalculateDcChargingUseCaseInterface {
    
    private final ProfileSelectionService profileSelectionService;
    private final DcChargingCalculator dcChargingCalculator;
    private final DcChargingOutputPortInterface outputPort;

    public CalculateDcChargingInteractor(
            ProfileSelectionService profileSelectionService,
            DcChargingCalculator dcChargingCalculator,
            DcChargingOutputPortInterface outputPort) {
        this.profileSelectionService = profileSelectionService;
        this.dcChargingCalculator = dcChargingCalculator;
        this.outputPort = outputPort;
    }
    
    @Override
    public boolean calculateDcChargingTime(
            double startingSocPercent,
            double targetSocPercent,
            double maxStationPowerKw,
            double ambientTemperatureCelsius) {
        try {
            CarProfile selectedProfile = profileSelectionService.getSelectedProfile();
            if (selectedProfile == null) {
                outputPort.showError("No car profile selected");
                return false;
            }
            
            DcChargingResult result = dcChargingCalculator.calculateChargingTime(
                    selectedProfile,
                    startingSocPercent,
                    targetSocPercent,
                    maxStationPowerKw,
                    ambientTemperatureCelsius);
            
            outputPort.showDcChargingResults(result);
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