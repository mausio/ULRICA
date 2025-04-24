package org.ulrica.presentation.view;

import org.ulrica.application.port.out.DcChargingOutputPortInterface;
import org.ulrica.application.port.out.UserOutputPortInterface;
import org.ulrica.domain.service.DcChargingCalculator.DcChargingResult;


public class DcChargingView implements DcChargingOutputPortInterface {
    
    private final UserOutputPortInterface userOutputPort;
    

    public DcChargingView(UserOutputPortInterface userOutputPort) {
        this.userOutputPort = userOutputPort;
    }
    
    @Override
    public void showDcChargingCalculatorForm() {
        userOutputPort.displayLine("\n=== DC (Fast) Charging Calculator ===");
    }
    
    @Override
    public void showDcChargingResults(DcChargingResult result) {
        userOutputPort.displayLine("\n=== Charging Time Estimate ===");
        
        long hours = (long) result.getChargingTimeHours();
        long minutes = Math.round((result.getChargingTimeHours() - hours) * 60);
        
        if (hours > 0) {
            userOutputPort.displayLine(String.format("Charging Time: %dh %dm", hours, minutes));
        } else {
            userOutputPort.displayLine(String.format("Charging Time: %dm", minutes));
        }
        
        userOutputPort.displayLine(String.format("Battery Capacity: %.1f kWh", result.getBatteryCapacityKwh()));
        userOutputPort.displayLine(String.format("Energy to be added: %.1f kWh", result.getEnergyToAddKwh()));
        userOutputPort.displayLine(String.format("Battery Chemistry: %s", result.getBatteryType()));
        userOutputPort.displayLine(String.format("Temperature: %.1f°C → %.1f°C", 
                result.getStartTemperatureCelsius(), result.getEndTemperatureCelsius()));
        userOutputPort.displayLine(String.format("Power Reduction: %.0f%% (for battery longevity)", 
                result.getPowerReductionPercent()));
        
        userOutputPort.displayLine("");
        if (result.getEnergyToAddKwh() < 5.0) {
            userOutputPort.displayLine("Note: Adding small amount of energy. Consider charging more.");
        } else if (result.getPowerReductionPercent() > 20.0) {
            userOutputPort.displayLine("Note: Battery level is high. Charging power is reduced to protect the battery.");
        } else if (result.getStartTemperatureCelsius() < 0) {
            userOutputPort.displayLine("Note: Battery is cold. Charging will be slower until the battery warms up.");
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