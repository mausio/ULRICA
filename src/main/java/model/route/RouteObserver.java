package model.route;

public interface RouteObserver {
    void onRouteUpdate(RouteStatus status);
    void onChargingStarted(ChargingSession session);
    void onChargingCompleted(ChargingSession session);
    void onRouteCompleted(RouteStatus finalStatus);
} 