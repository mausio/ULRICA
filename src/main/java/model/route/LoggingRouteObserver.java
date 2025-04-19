package model.route;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoggingRouteObserver implements RouteObserver {
    private final List<String> eventLog;
    private final DateTimeFormatter formatter;

    public LoggingRouteObserver() {
        this.eventLog = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public void onRouteUpdate(RouteStatus status) {
        String event = String.format("[%s] Position: %.1f km, Battery: %.1f%%, Remaining: %.1f km",
            getCurrentTimestamp(),
            status.currentPosition(),
            status.currentBatteryPercentage(),
            status.remainingDistance()
        );
        logEvent(event);

        if (status.state() == RouteStatus.RouteState.FAILED) {
            logEvent(String.format("[%s] Route failed: %s",
                getCurrentTimestamp(),
                status.errorMessage()
            ));
        }
    }

    @Override
    public void onChargingStarted(ChargingSession session) {
        String event = String.format("[%s] Started charging at %.1f km - From %.1f%% to %.1f%%, Power: %.1f kW",
            getCurrentTimestamp(),
            session.position(),
            session.startSoC(),
            session.targetSoC(),
            session.chargingPower()
        );
        logEvent(event);
    }

    @Override
    public void onChargingCompleted(ChargingSession session) {
        String event = String.format("[%s] Completed charging at %.1f km - Duration: %.1f minutes",
            getCurrentTimestamp(),
            session.position(),
            session.estimatedDuration()
        );
        logEvent(event);
    }

    @Override
    public void onRouteCompleted(RouteStatus finalStatus) {
        String event = String.format("[%s] Route completed - Final position: %.1f km, Battery: %.1f%%",
            getCurrentTimestamp(),
            finalStatus.currentPosition(),
            finalStatus.currentBatteryPercentage()
        );
        logEvent(event);
    }

    private void logEvent(String event) {
        eventLog.add(event);
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(formatter);
    }

    public List<String> getEventLog() {
        return new ArrayList<>(eventLog);
    }

    public void printEventLog() {
        eventLog.forEach(System.out::println);
    }

    public String getFormattedEventLog() {
        return String.join("\n", eventLog);
    }
} 