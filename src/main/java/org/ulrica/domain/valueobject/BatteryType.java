package org.ulrica.domain.valueobject;

public enum BatteryType {
    LFP("Lithium Iron Phosphate"),
    NMC("Nickel Manganese Cobalt"),
    NCA("Nickel Cobalt Aluminum");

    private final String description;

    BatteryType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 