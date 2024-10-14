package models;

import controller.carProfile.ConsumptionProfileController;
import utils.generalUtils.AnsiColorsUtil;

import java.util.*;

public class ConsumptionProfileModel {
  private static final int MAX_PARAMETERS = 3;
  
  private static List<Map<Double, Double>> parametersList = null;
  private double a = 0.0;
  private double b = 0.0;
  
  public ConsumptionProfileModel() {
    parametersList = new ArrayList<>();
  }
  
  public static List<Map<Double, Double>> getParametersList() {
    return parametersList;
  }
  
  public static void setParametersList(
      List<Map<Double, Double>> parametersList) {
    ConsumptionProfileModel.parametersList = parametersList;
  }
  
  //TODO: Maybe offset/refactor this into some calculationUtils (file is already very long)
  public void performRegression() {
    Double testValue = 200.0;
    int n = parametersList.size();
    
    if (n == 0) {
      System.out.println("No data points available for regression.");
      //TODO: Throw/Handle error here
      return;
    }
    
    double sumX = 0.0, sumY = 0.0, sumXY = 0.0, sumX2 = 0.0;
    
    for (Map<Double, Double> tuple : parametersList) {
      for (Map.Entry<Double, Double> entry : tuple.entrySet()) {
        double speed = entry.getKey();
        double consumption = entry.getValue();
        
        sumX += speed;
        sumY += Math.log(consumption);
        sumXY += speed * Math.log(consumption);
        sumX2 += speed * speed;
      }
    }
    
    double a = Math.exp((sumY * sumX2 - sumX * sumXY) / (n * sumX2 - sumX * sumX));
    double b = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
    
    System.out.printf("Model: y = %.4f * e^(%.4f * x)\n", a, b);
    //TODO: Check if b is null or negative; Then throw an error.
    this.a = a;
    this.b = b;
    
    double estimatedConsumption = a * Math.exp(b * testValue);
    if (Double.isNaN(estimatedConsumption) || Double.isInfinite(
        estimatedConsumption) || estimatedConsumption == 0) {
      System.out.println(AnsiColorsUtil.RED.getCode() + "\n Something went wrong;" + " Please start again with adding data points." + AnsiColorsUtil.WHITE.getCode());
//      createConsumptionProfile(scanner);
      //TODO: Do sth here; Handle/Throw error.
    }
  }
  
  public void createConsumptionProfile(Scanner scanner) {
    Integer i = 0;
    Boolean done = false;
    Double consumption = 0.0;
    Double speed = 0.0;
    
    while (!done) {
      ConsumptionProfileController.printNrOfParamter(i);
      
      consumption = ConsumptionProfileController.getConsumptionDialog(
          scanner);
      speed = ConsumptionProfileController.getSpeedDialog(scanner);
      
      
      if (speed != 0.0 && consumption != 0.0) {
        addParameterTuple(consumption, speed);
        ConsumptionProfileController.printParameters(consumption,
                                                     speed);
        
        if (i >= MAX_PARAMETERS) {
          done = true;
        } else {
          if (i > 0) {
            done = ConsumptionProfileController.additionalParamterDialog(
                scanner);
          } else {
            i++;
          }
        }
      } else {
        System.out.println("Something went wrong; Please try again.");
        //TODO: Handle/Throw error here
      }
      
    }
  }
  
  public void addParameterTuple(Double consumption, Double speed) {
    if (parametersList.size() < MAX_PARAMETERS) {
      Map<Double, Double> parameterTuple = new HashMap<>();
      parameterTuple.put(speed, consumption);
      parametersList.add(parameterTuple);
    } else {
      System.out.println(
          "Maximum number of parameters reached. Cannot add more.");
      //TODO: Throw/Handle error here
    }
  }
  
  //TODO: Maybe offset/refactor this into some calculationUtils (file is already very long)
  public void estimateConsumption(double speed) {
    double estimatedConsumption = a * Math.exp(b * speed);
    System.out.println("\nEstimated consumption of " + estimatedConsumption + "kWh @ " + speed + "km/h");
  }
  
  public void clearParameterList() {
    if (!parametersList.isEmpty()) {
      parametersList.clear();
    }
  }
}
