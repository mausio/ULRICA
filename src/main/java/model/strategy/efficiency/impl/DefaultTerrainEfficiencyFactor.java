package model.strategy.efficiency.impl;

import model.strategy.efficiency.TerrainEfficiencyFactor;

public class DefaultTerrainEfficiencyFactor implements TerrainEfficiencyFactor {
    private final TerrainType terrainType;

    public DefaultTerrainEfficiencyFactor(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    @Override
    public TerrainType getTerrainType() {
        return terrainType;
    }

    @Override
    public double calculateFactor() {
        return terrainType.getBaseFactor();
    }
} 