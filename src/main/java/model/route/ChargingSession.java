package model.route;

public record ChargingSession(
    long stationId,
    double position,
    double startSoC,
    double targetSoC,
    double chargingPower,
    double estimatedDuration
) {} 