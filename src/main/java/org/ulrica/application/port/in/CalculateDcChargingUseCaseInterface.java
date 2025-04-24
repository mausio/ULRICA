package org.ulrica.application.port.in;

public interface CalculateDcChargingUseCaseInterface {
    

    boolean calculateDcChargingTime(
            double startingSocPercent,
            double targetSocPercent,
            double maxStationPowerKw,
            double ambientTemperatureCelsius);
} 