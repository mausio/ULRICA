package model.route;

public record RouteStatus(
    double currentPosition,
    double remainingDistance,
    double currentBatteryPercentage,
    double drivingTime,
    RouteState state,
    String errorMessage
) {
    public enum RouteState {
        ACTIVE,
        CHARGING,
        COMPLETED,
        FAILED
    }

    public RouteStatus {
        if (state == null) {
            state = RouteState.ACTIVE;
        }
        if (errorMessage == null) {
            errorMessage = "";
        }
    }
} 