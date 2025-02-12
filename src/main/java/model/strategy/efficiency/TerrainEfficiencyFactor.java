package model.strategy.efficiency;

public interface TerrainEfficiencyFactor extends EfficiencyFactor {
    enum TerrainType {
        FLAT(1.0),
        HILLY(1.05),
        MOUNTAINOUS(1.1);

        private final double baseFactor;

        TerrainType(double baseFactor) {
            this.baseFactor = baseFactor;
        }

        public double getBaseFactor() {
            return baseFactor;
        }
    }

    TerrainType getTerrainType();
} 