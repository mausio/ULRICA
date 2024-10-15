package service;

import controller.ConsumptionProfileController;
import utils.carProfileUtils.ConsumptionProfileUtil;

public class RangeService {
  
  public void estimateAndPrintConsumption(double speed, double a,
                                          double b) {
    double estimatedConsumption = ConsumptionProfileUtil.estimateConsumptionAtSpeed(
        speed,
        a,
        b);
    ConsumptionProfileController.printEstimatedConsumptionAtSpeed(
        estimatedConsumption,
        speed);
  }
}
