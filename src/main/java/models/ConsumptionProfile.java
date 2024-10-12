package models;

import utils.AnsiColors;
import utils.CleanInput;

import java.util.*;

public class ConsumptionProfile {
  private static final int MAX_PARAMETERS = 3;
  
  private List<Map<Double, Double>> parametersList;
  private double a = 0.0;
  private double b = 0.0;
  
  public ConsumptionProfile() {
    this.parametersList = new ArrayList<>();
  }
  
  public void createConsumptionProfile(Scanner scanner) {
    Double testValue = 200.0;
    Integer i = 0;
    Boolean done = false;
    String input;
    Double consumption = 0.0;
    Double speed = 0.0;
    if (!parametersList.isEmpty()) {
      parametersList.clear();
    }
    
    System.out.println("\nNext, we calculate how much the car will probably " + "consume at which speed.");
    System.out.println("For that, please give at least one data point of the " + "average consumption at a given speed" + " (e.g" + ". " + "18kWh @ 110km/h) ");
    
    while (!done) {
      System.out.println(AnsiColors.CYAN.getCode() + "\nNr. " + (i + 1) + ": " + AnsiColors.WHITE.getCode());
      
      System.out.print("consumption: (in kWh) ");
      input = scanner.nextLine();
      if (!input.isEmpty()) {
        consumption = CleanInput.cleanDoubleFromCharacters(input);
      }
      
      System.out.print("speed: (in km/h) ");
      input = scanner.nextLine();
      if (!input.isEmpty()) {
        speed = CleanInput.cleanDoubleFromCharacters(input);
      }
      
      if (speed != 0.0 && consumption != 0.0) {
        
        addParameterTuple(consumption, speed);
        System.out.println(AnsiColors.YELLOW.getCode() + "added " + consumption + "kWh @ " + speed + "km/h" + AnsiColors.WHITE.getCode());
        
        if (i >= MAX_PARAMETERS) {
          done = true;
        } else {
          if (i > 0) {
            System.out.println("\nDo you want add another data point? (y/n) ");
            input = scanner.nextLine();
            done = !CleanInput.formatYesOrNoToBoolean(input);
          } else {
            i++;
          }
        }
      } else {
        System.out.println("Something went wrong; Please try again.");
      }
      
    }
    
    performRegression();
    
    double estimatedConsumption = a * Math.exp(b * testValue);
    if (Double.isNaN(estimatedConsumption) || Double.isInfinite(estimatedConsumption) || estimatedConsumption == 0) {
      System.out.println(AnsiColors.RED.getCode() + "\n Something went wrong;" + " Please start again with adding data points." + AnsiColors.WHITE.getCode());
      createConsumptionProfile(scanner);
    }
    
  }
  
  public void addParameterTuple(Double consumption, Double speed) {
    if (parametersList.size() < MAX_PARAMETERS) {
      Map<Double, Double> parameterTuple = new HashMap<>();
      parameterTuple.put(speed, consumption);
      parametersList.add(parameterTuple);
    } else {
      System.out.println("Maximum number of parameters reached. Cannot add more.");
    }
  }
  
  
  public void estimateConsumption(double speed) {
    double estimatedConsumption = a * Math.exp(b * speed);
    System.out.println("\nEstimated consumption of " + estimatedConsumption + "kWh @ " + speed + "km/h");
  }
  
  public void performRegression() {
    int n = parametersList.size();
    
    // Check the number of data points
    if (n == 0) {
      System.out.println("No data points available for regression.");
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
    this.a = a;
    this.b = b;
  }
}
