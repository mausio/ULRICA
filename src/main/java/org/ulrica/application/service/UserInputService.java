package org.ulrica.application.service;

import org.ulrica.application.port.in.InputValidationServiceInterface;
import org.ulrica.application.port.out.UserInputPortInterface;
import org.ulrica.application.port.out.UserOutputPortInterface;
import org.ulrica.domain.valueobject.BatteryType;

public class UserInputService {
    private final UserInputPortInterface userInputPort;
    private final UserOutputPortInterface userOutputPort;
    private final InputValidationServiceInterface validationService;
    
    public UserInputService(
            UserInputPortInterface userInputPort,
            UserOutputPortInterface userOutputPort,
            InputValidationServiceInterface validationService) {
        this.userInputPort = userInputPort;
        this.userOutputPort = userOutputPort;
        this.validationService = validationService;
    }
    
    public String getValidatedTextInput(String prompt, String fieldName) {
        while (true) {
            userOutputPort.displayPrompt(prompt);
            String input = userInputPort.readLine();
            if (validationService.isValidName(input)) {
                return input;
            }
            userOutputPort.displayLine(validationService.getValidationMessage(fieldName, input));
        }
    }
    
    public int getValidatedIntInput(String prompt, String fieldName) {
        while (true) {
            userOutputPort.displayPrompt(prompt);
            try {
                String input = userInputPort.readLine();
                int value = Integer.parseInt(input);
                
                switch (fieldName) {
                    case "year":
                        if (validationService.isValidYear(value)) return value;
                        break;
                    case "battery type":
                        if (validationService.isValidBatteryTypeChoice(value)) return value;
                        break;
                    default:
                        return value;
                }
                userOutputPort.displayLine(validationService.getValidationMessage(fieldName, String.valueOf(value)));
            } catch (NumberFormatException e) {
                userOutputPort.displayLine("Please enter a valid number.");
            }
        }
    }
    
    public double getValidatedDoubleInput(String prompt, String fieldName) {
        while (true) {
            userOutputPort.displayPrompt(prompt);
            try {
                String input = userInputPort.readLine();
                if (input.isEmpty() && fieldName.equals("battery degradation")) {
                    return 0.0;
                }
                double value = Double.parseDouble(input);
                
                switch (fieldName) {
                    case "battery capacity":
                        if (validationService.isValidBatteryCapacity(value)) return value;
                        break;
                    case "battery degradation":
                        if (validationService.isValidBatteryDegradation(value)) return value;
                        break;
                    case "dc charging power":
                        if (validationService.isValidDcChargingPower(value)) return value;
                        break;
                    case "ac charging power":
                        if (validationService.isValidAcChargingPower(value)) return value;
                        break;
                    case "consumption":
                        if (validationService.isValidConsumption(value)) return value;
                        break;
                    default:
                        return value;
                }
                userOutputPort.displayLine(validationService.getValidationMessage(fieldName, String.valueOf(value)));
            } catch (NumberFormatException e) {
                userOutputPort.displayLine("Please enter a valid number.");
            }
        }
    }
    
    public boolean getBooleanInput(String prompt) {
        while (true) {
            userOutputPort.displayPrompt(prompt);
            String input = userInputPort.readLine().trim().toLowerCase();
            if (input.isEmpty()) {
                return false;
            }
            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            }
            userOutputPort.displayLine("Invalid input. Please enter 'yes' or 'no'.");
        }
    }
    
    public BatteryType selectBatteryType() {
        userOutputPort.displayLine("\nAvailable battery types:");
        BatteryType[] types = BatteryType.values();
        for (int i = 0; i < types.length; i++) {
            userOutputPort.displayLine((i + 1) + ". " + types[i].name() + " (" + types[i].getDescription() + ")");
        }
        
        int choice = getValidatedIntInput("Select battery type (1-" + types.length + "): ", "battery type");
        return types[choice - 1];
    }
} 