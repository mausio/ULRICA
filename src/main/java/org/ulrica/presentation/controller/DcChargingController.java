package org.ulrica.presentation.controller;

import org.ulrica.application.port.in.CalculateDcChargingUseCaseInterface;
import org.ulrica.application.port.out.DcChargingOutputPortInterface;
import org.ulrica.application.port.out.UserInputPortInterface;
import org.ulrica.application.port.out.UserOutputPortInterface;

public class DcChargingController {
    
    private final UserInputPortInterface userInputPort;
    private final UserOutputPortInterface userOutputPort;
    private final CalculateDcChargingUseCaseInterface calculateDcChargingUseCase;
    private final DcChargingOutputPortInterface dcChargingOutputPort;

    public DcChargingController(
            UserInputPortInterface userInputPort,
            UserOutputPortInterface userOutputPort,
            CalculateDcChargingUseCaseInterface calculateDcChargingUseCase,
            DcChargingOutputPortInterface dcChargingOutputPort) {
        this.userInputPort = userInputPort;
        this.userOutputPort = userOutputPort;
        this.calculateDcChargingUseCase = calculateDcChargingUseCase;
        this.dcChargingOutputPort = dcChargingOutputPort;
    }

    public boolean processDcChargingCalculation() {
        try {
            dcChargingOutputPort.showDcChargingCalculatorForm();
            
            userOutputPort.display("Enter starting State of Charge (%) [0-100]: ");
            double startingSoc = userInputPort.readDouble();
            
            userOutputPort.display("Enter target State of Charge (%) [0-100]: ");
            double targetSoc = userInputPort.readDouble();
            
            userOutputPort.display("Enter maximum charging station power (kW): ");
            double maxStationPower = userInputPort.readDouble();
            
            userOutputPort.display("Enter ambient temperature (°C): ");
            double ambientTemperature = userInputPort.readDouble();
            
            return calculateDcChargingUseCase.calculateDcChargingTime(
                    startingSoc,
                    targetSoc,
                    maxStationPower,
                    ambientTemperature);
            
        } catch (Exception e) {
            dcChargingOutputPort.showError("An error occurred: " + e.getMessage());
            return false;
        }
    }
} 