package model.route;

import java.util.ArrayList;
import java.util.List;

public class ConsoleRouteObserver implements RouteObserver {
    private final List<JourneySegment> segments;
    private double totalTime;
    private boolean headerPrinted;
    private double startPoint;
    private double lastPosition;
    private double lastBattery;
    private double drivingTime;

    private static class JourneySegment {
        String type;
        double position;
        double batteryStart;
        double batteryEnd;
        double duration;
        String details;

        JourneySegment(String type, double position, double batteryStart, double batteryEnd, double duration, String details) {
            this.type = type;
            this.position = position;
            this.batteryStart = batteryStart;
            this.batteryEnd = batteryEnd;
            this.duration = duration;
            this.details = details;
        }
    }

    public ConsoleRouteObserver(double startPoint) {
        this.segments = new ArrayList<>();
        this.totalTime = 0;
        this.headerPrinted = false;
        this.startPoint = startPoint;
        this.lastPosition = startPoint;
        this.drivingTime = 0;
    }

    @Override
    public void onRouteUpdate(RouteStatus status) {
        if (!headerPrinted) {
            printHeader();
            // Add start point
            segments.add(new JourneySegment(
                "Start",
                startPoint,
                status.currentBatteryPercentage(),
                status.currentBatteryPercentage(),
                0.0,
                "Journey begins"
            ));
            lastBattery = status.currentBatteryPercentage();
            headerPrinted = true;
        }
        drivingTime += status.drivingTime();
    }

    @Override
    public void onChargingStarted(ChargingSession session) {
        // Nothing to do here
    }

    @Override
    public void onChargingCompleted(ChargingSession session) {
        // Add driving segment before charging
        if (session.position() > lastPosition) {
            segments.add(new JourneySegment(
                "Drive",
                session.position(),
                lastBattery,
                session.startSoC(),
                drivingTime,
                String.format("%.1f km", session.position() - lastPosition)
            ));
        }
        
        // Add charging segment
        segments.add(new JourneySegment(
            "⚡ Charge",
            session.position(),
            session.startSoC(),
            session.targetSoC(),
            session.estimatedDuration(),
            String.format("%.0f kW", session.chargingPower())
        ));
        
        lastPosition = session.position();
        lastBattery = session.targetSoC();
        totalTime += session.estimatedDuration() + drivingTime;
        drivingTime = 0;
    }

    @Override
    public void onRouteCompleted(RouteStatus finalStatus) {
        if (finalStatus.state() == RouteStatus.RouteState.FAILED) {
            printJourneySummary(finalStatus);
            System.out.println("\n=== Route Planning Failed ===");
            System.out.println("Error: " + finalStatus.errorMessage());
            System.out.println("Suggestions:");
            System.out.println("1. Select a vehicle with longer range");
            System.out.println("2. Reduce energy consumption (e.g., lower speed, avoid AC)");
            System.out.println("3. Check for additional charging stations on the route");
        } else {
            // Add final driving segment if needed
            if (finalStatus.currentPosition() > lastPosition) {
                segments.add(new JourneySegment(
                    "Drive",
                    finalStatus.currentPosition(),
                    lastBattery,
                    finalStatus.currentBatteryPercentage(),
                    drivingTime,
                    String.format("%.1f km", finalStatus.currentPosition() - lastPosition)
                ));
                totalTime += drivingTime;
            }
            
            // Add end point
            segments.add(new JourneySegment(
                "End",
                finalStatus.currentPosition(),
                finalStatus.currentBatteryPercentage(),
                finalStatus.currentBatteryPercentage(),
                0.0,
                "Journey completed"
            ));
            printJourneySummary(finalStatus);
        }
    }

    private void printHeader() {
        System.out.println("\n=== Journey Details ===");
        System.out.println("╔════════════╦═══════════╦═══════════╦═══════════╦═══════════╦════════════════════╗");
        System.out.println("║ Segment    ║ Position  ║ Battery   ║ Battery   ║ Duration  ║ Details            ║");
        System.out.println("║ Type       ║ (km)      ║ Start (%) ║ End (%)   ║ (min)     ║                    ║");
        System.out.println("╠════════════╬═══════════╬═══════════╬═══════════╬═══════════╬════════════════════╣");
    }

    private void printJourneySummary(RouteStatus finalStatus) {
        for (JourneySegment segment : segments) {
            System.out.printf("║ %-10s ║ %9.1f ║ %9.1f ║ %9.1f ║ %9.1f ║ %-18s ║\n",
                segment.type,
                segment.position,
                segment.batteryStart,
                segment.batteryEnd,
                segment.duration,
                segment.details
            );
        }
        System.out.println("╚════════════╩═══════════╩═══════════╩═══════════╩═══════════╩════════════════════╝");
        
        System.out.printf("\nJourney Summary:\n");
        System.out.printf("- Total distance: %.1f km\n", finalStatus.currentPosition() - startPoint);
        System.out.printf("- Total time: %.0f hours %.0f minutes\n", 
            Math.floor(totalTime / 60), 
            totalTime % 60);
        System.out.printf("- Final battery: %.1f%%\n", finalStatus.currentBatteryPercentage());
        
        // Calculate statistics
        long chargingStops = segments.stream().filter(s -> s.type.equals("⚡ Charge")).count();
        double chargingTime = segments.stream()
            .filter(s -> s.type.equals("⚡ Charge"))
            .mapToDouble(s -> s.duration)
            .sum();
        double drivingTime = segments.stream()
            .filter(s -> s.type.equals("Drive"))
            .mapToDouble(s -> s.duration)
            .sum();
            
        System.out.printf("- Number of charging stops: %d\n", chargingStops);
        System.out.printf("- Total charging time: %.0f hours %.0f minutes\n",
            Math.floor(chargingTime / 60),
            chargingTime % 60);
        System.out.printf("- Total driving time: %.0f hours %.0f minutes\n",
            Math.floor(drivingTime / 60),
            drivingTime % 60);
    }
} 