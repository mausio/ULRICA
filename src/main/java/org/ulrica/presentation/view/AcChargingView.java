package org.ulrica.presentation.view;

import org.ulrica.application.port.out.AcChargingOutputPortInterface;
import org.ulrica.application.port.out.UserOutputPortInterface;
import org.ulrica.domain.service.AcChargingCalculator;
import org.ulrica.domain.service.AcChargingCalculator.AcChargingResult;


public class AcChargingView implements AcChargingOutputPortInterface {
    
    private final UserOutputPortInterface userOutputPort;
    private final AcChargingCalculator acChargingCalculator;
    

    public AcChargingView(UserOutputPortInterface userOutputPort, AcChargingCalculator acChargingCalculator) {
        this.userOutputPort = userOutputPort;
        this.acChargingCalculator = acChargingCalculator;
    }
    
    @Override
    public void showAcChargingCalculatorForm() {
        userOutputPort.displayLine("\n=== AC (Slow) Charging Calculator ===");
    }
    
    @Override
    public void showAcChargingResults(AcChargingResult result) {
        userOutputPort.displayLine("\n=== Charging Time Estimate ===");
        
        long hours = (long) result.getChargingTimeHours();
        long minutes = Math.round((result.getChargingTimeHours() - hours) * 60);
        
        if (hours > 0) {
            if (minutes > 0) {
                userOutputPort.displayLine(String.format("Charging time: %d hours %d minutes", hours, minutes));
            } else {
                userOutputPort.displayLine(String.format("Charging time: %d hours", hours));
            }
        } else {
            userOutputPort.displayLine(String.format("Charging time: %d minutes", minutes));
        }
        
        userOutputPort.displayLine(String.format("Energy required: %.1f kWh", result.getEnergyRequiredKwh()));
        userOutputPort.displayLine(String.format("Effective charging power: %.1f kW", result.getEffectiveChargingPowerKw()));
        userOutputPort.displayLine(String.format("Efficiency loss: %.1f%%", result.getEfficiencyLossPercent()));
        userOutputPort.displayLine(String.format("Temperature efficiency: %.1f%%", result.getTemperatureEfficiencyPercent()));
        
        userOutputPort.displayLine("");
        
        if (result.getConnectorType() == AcChargingCalculator.HOUSEHOLD_SOCKET && result.getChargingTimeHours() > 12) {
            userOutputPort.displayLine("Note: Charging time is long. Consider using a more powerful connector.");
        } else if (result.getEfficiencyLossPercent() > 8.0) {
            userOutputPort.displayLine("Note: Efficiency loss is high. Using a wallbox would be more efficient.");
        } else if (result.getTemperatureEfficiencyPercent() < 95.0) {
            userOutputPort.displayLine("Note: Temperature conditions are not optimal for charging.");
        }
        
        userOutputPort.displayLine("");
        userOutputPort.displayLine("Press Enter to continue...");
    }
    
    @Override
    public void showError(String message) {
        userOutputPort.displayLine("\nError: " + message);
        userOutputPort.displayLine("");
        userOutputPort.displayLine("Press Enter to continue...");
    }
} 