package model.strategy.efficiency;

public interface DrivingEnvironmentFactor extends EfficiencyFactor {
    enum DrivingEnvironment {
        CITY(0.9),      
        RURAL(1.0),    
        HIGHWAY(1.1);  

        private final double baseFactor;

        DrivingEnvironment(double baseFactor) {
            this.baseFactor = baseFactor;
        }

        public double getBaseFactor() {
            return baseFactor;
        }
    }

    DrivingEnvironment getDrivingEnvironment();
    double getAverageSpeed(); // in km/h
} 