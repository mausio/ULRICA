package org.ulrica.application.service;

import org.ulrica.application.port.in.InputValidationServiceInterface;
import org.ulrica.application.port.in.ValidationInterface;
import org.ulrica.infrastructure.util.ValidationUtils;

public class InputValidationService implements InputValidationServiceInterface {
    private final ValidationInterface validator;
    
    public InputValidationService() {
        this.validator = ValidationUtils.getInstance();
    }

    @Override
    public boolean isValidName(String name) {
        return validator.isValidName(name);
    }

    @Override
    public boolean isValidYear(int year) {
        return validator.isValidYear(year);
    }

    @Override
    public boolean isValidBatteryCapacity(double capacity) {
        return validator.isValidBatteryCapacity(capacity);
    }

    @Override
    public boolean isValidBatteryDegradation(double degradation) {
        return validator.isValidBatteryDegradation(degradation);
    }

    @Override
    public boolean isValidDcChargingPower(double power) {
        return validator.isValidDcChargingPower(power);
    }

    @Override
    public boolean isValidAcChargingPower(double power) {
        return validator.isValidAcChargingPower(power);
    }

    @Override
    public boolean isValidConsumption(double consumption) {
        return validator.isValidConsumption(consumption);
    }

    @Override
    public boolean isValidConsumptionProgression(double at50, double at100, double at130) {
        return validator.isValidConsumptionProgression(at50, at100, at130);
    }

    @Override
    public boolean isValidBatteryTypeChoice(int choice) {
        return validator.isValidBatteryTypeChoice(choice);
    }

    @Override
    public String getValidationMessage(String fieldName, String value) {
        return validator.getValidationMessage(fieldName, value);
    }
} 