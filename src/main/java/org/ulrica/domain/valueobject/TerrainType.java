package org.ulrica.domain.valueobject;

public enum TerrainType {
    FLAT("Flat terrain with minimal elevation changes"),
    HILLY("Hilly terrain with moderate elevation changes"),
    MOUNTAINOUS("Mountainous terrain with significant elevation changes");

    private final String description;

    TerrainType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 