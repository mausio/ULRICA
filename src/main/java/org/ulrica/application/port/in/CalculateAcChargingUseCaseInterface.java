package org.ulrica.application.port.in;

public interface CalculateAcChargingUseCaseInterface {
    
    boolean calculateAcChargingTime(
            int connectorType,
            double startingSocPercent,
            double targetSocPercent,
            double ambientTemperatureCelsius);
} 