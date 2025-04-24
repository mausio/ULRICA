package org.ulrica.application.port.in;

public interface ValidationInterface {
    boolean isValidName(String name);
    boolean isValidYear(int year);
    boolean isValidBatteryCapacity(double capacity);
    boolean isValidBatteryDegradation(double degradation);
    boolean isValidDcChargingPower(double power);
    boolean isValidAcChargingPower(double power);
    boolean isValidConsumption(double consumption);
    boolean isValidConsumptionProgression(double at50, double at100, double at130);
    boolean isValidBatteryTypeChoice(int choice);
    String getValidationMessage(String fieldName, String value);
} 