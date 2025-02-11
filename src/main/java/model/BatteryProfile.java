package model;

public class BatteryProfile {
    private double initialCapacity; // in kWh
    private double currentCapacity; // in kWh
    private double degradationRate; // percentage per year
    private int cycleCount;
    private String batteryType; // e.g., "LFP", "NMC", etc.

    public BatteryProfile(double initialCapacity, String batteryType) {
        this.initialCapacity = initialCapacity;
        this.currentCapacity = initialCapacity;
        this.degradationRate = 0.0;
        this.cycleCount = 0;
        this.batteryType = batteryType;
    }

    // Getters and setters
    public double getInitialCapacity() { return initialCapacity; }
    public void setInitialCapacity(double initialCapacity) { this.initialCapacity = initialCapacity; }

    public double getCurrentCapacity() { return currentCapacity; }
    public void setCurrentCapacity(double currentCapacity) { this.currentCapacity = currentCapacity; }

    public double getDegradationRate() { return degradationRate; }
    public void setDegradationRate(double degradationRate) { this.degradationRate = degradationRate; }

    public int getCycleCount() { return cycleCount; }
    public void setCycleCount(int cycleCount) { this.cycleCount = cycleCount; }

    public String getBatteryType() { return batteryType; }
    public void setBatteryType(String batteryType) { this.batteryType = batteryType; }

    // Calculate current degradation percentage
    public double getCurrentDegradationPercentage() {
        return ((initialCapacity - currentCapacity) / initialCapacity) * 100.0;
    }

    // Update capacity based on cycles and time
    public void updateCapacity(int additionalCycles, double yearsElapsed) {
        cycleCount += additionalCycles;
        double cycleDegradation = calculateCycleDegradation(additionalCycles);
        double timeDegradation = calculateTimeDegradation(yearsElapsed);
        
        // Apply the larger of the two degradation values
        double totalDegradation = Math.max(cycleDegradation, timeDegradation);
        currentCapacity = initialCapacity * (1 - totalDegradation);
    }

    private double calculateCycleDegradation(int cycles) {
        // Simplified degradation calculation - can be made more sophisticated
        return (cycles * 0.001); // 0.1% per 100 cycles
    }

    private double calculateTimeDegradation(double years) {
        return years * degradationRate;
    }
} 