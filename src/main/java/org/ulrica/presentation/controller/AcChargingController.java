package org.ulrica.presentation.controller;

import org.ulrica.application.port.in.CalculateAcChargingUseCaseInterface;
import org.ulrica.application.port.out.AcChargingOutputPortInterface;
import org.ulrica.application.port.out.UserInputPortInterface;
import org.ulrica.application.port.out.UserOutputPortInterface;
import org.ulrica.domain.service.AcChargingCalculator;

public class AcChargingController {
    
    private final UserInputPortInterface userInputPort;
    private final UserOutputPortInterface userOutputPort;
    private final CalculateAcChargingUseCaseInterface calculateAcChargingUseCase;
    private final AcChargingOutputPortInterface acChargingOutputPort;

    public AcChargingController(
            UserInputPortInterface userInputPort,
            UserOutputPortInterface userOutputPort,
            CalculateAcChargingUseCaseInterface calculateAcChargingUseCase,
            AcChargingOutputPortInterface acChargingOutputPort) {
        this.userInputPort = userInputPort;
        this.userOutputPort = userOutputPort;
        this.calculateAcChargingUseCase = calculateAcChargingUseCase;
        this.acChargingOutputPort = acChargingOutputPort;
    }

    public boolean processAcChargingCalculation() {
        try {
            acChargingOutputPort.showAcChargingCalculatorForm();
            
            showConnectorOptions();
            userOutputPort.display("Select connector type (1-3): ");
            int connectorType = userInputPort.readInt();
            
            if (connectorType < AcChargingCalculator.HOUSEHOLD_SOCKET || connectorType > AcChargingCalculator.WALLBOX) {
                acChargingOutputPort.showError("Invalid connector type. Please select a number from 1 to 3.");
                return false;
            }
            
            userOutputPort.display("Enter current State of Charge (%) [0-100]: ");
            double startingSoc = userInputPort.readDouble();
            
            userOutputPort.display("Enter target State of Charge (%) [0-100]: ");
            double targetSoc = userInputPort.readDouble();
            
            userOutputPort.display("Enter ambient temperature (°C): ");
            double ambientTemperature = userInputPort.readDouble();
            
            return calculateAcChargingUseCase.calculateAcChargingTime(
                    connectorType,
                    startingSoc,
                    targetSoc,
                    ambientTemperature);
            
        } catch (Exception e) {
            acChargingOutputPort.showError("An error occurred: " + e.getMessage());
            return false;
        }
    }

    private void showConnectorOptions() {
        userOutputPort.displayLine("Available AC connectors:");
        userOutputPort.displayLine("1. Household Socket (2.3 kW)");
        userOutputPort.displayLine("2. Camping Socket (3.7 kW)");
        userOutputPort.displayLine("3. Wallbox (11 kW)");
        userOutputPort.displayLine("");
    }
} 