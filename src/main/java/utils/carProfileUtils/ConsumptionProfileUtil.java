package utils.carProfileUtils;

public class ConsumptionProfileUtil {
  public static String formatModel(double a, double b) {
    return String.format("Model: f(x) = %.5f * e^(%.5f * x)", a, b);
  }
  
  public static double estimateConsumptionAtSpeed(double speed,
                                                  double a,
                                                  double b) {
    return a * Math.exp(b * speed);
    
  }
}
