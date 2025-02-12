package service;

import java.util.Scanner;

import model.CarProfile;
import model.strategy.EnhancedRangeCalculationStrategy;
import model.strategy.efficiency.DrivingEnvironmentFactor.DrivingEnvironment;
import model.strategy.efficiency.EfficiencyFactor;
import model.strategy.efficiency.TerrainEfficiencyFactor.TerrainType;
import model.strategy.efficiency.WeatherEfficiencyFactor.WeatherCondition;
import model.strategy.factory.RangeCalculationStrategyFactory;

public class RangeCalculationService {
    private final Scanner scanner;
    private final CarProfile carProfile;

    public RangeCalculationService(CarProfile carProfile) {
        this.scanner = new Scanner(System.in);
        this.carProfile = carProfile;
    }

    public void showMenu() {
        calculateRangeWithConditions();
    }

    private void calculateRangeWithConditions() {
        System.out.println("\n=== Terrain Conditions ===");
        System.out.println("1. Flat");
        System.out.println("2. Hilly");
        System.out.println("3. Mountainous");
        TerrainType terrain = getTerrainType();

        System.out.println("\n=== Weather Conditions ===");
        System.out.println("1. Sunny");
        System.out.println("2. Cloudy");
        System.out.println("3. Rain");
        System.out.println("4. Snow");
        System.out.println("5. Strong Wind");
        WeatherCondition weather = getWeatherCondition();

        double temperature;
        while (true) {
            System.out.print("\nEnter temperature in Celsius: ");
            try {
                temperature = Double.parseDouble(scanner.nextLine());
                if (weather == WeatherCondition.SNOW && temperature > 5) {
                    System.out.println("Error: Snow condition is only valid for temperatures below 5°C");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        System.out.println("\n=== Driving Environment ===");
        System.out.println("1. City");
        System.out.println("2. Rural");
        System.out.println("3. Highway");
        DrivingEnvironment environment = getDrivingEnvironment();

        double averageSpeed;
        switch (environment) {
            case CITY:
                averageSpeed = 40.0;
                break;
            case RURAL:
                averageSpeed = 70.0;
                break;
            case HIGHWAY:
                averageSpeed = 110.0;
                break;
            default:
                averageSpeed = 70.0;
        }

        double stateOfCharge;
        while (true) {
            System.out.print("\nEnter current state of charge (0-100%): ");
            try {
                stateOfCharge = Double.parseDouble(scanner.nextLine());
                if (stateOfCharge < 0 || stateOfCharge > 100) {
                    System.out.println("Error: State of charge must be between 0 and 100%");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        double batteryTemperature = temperature;

        System.out.println("\n=== Efficiency Mode ===");
        System.out.println("1. ECO");
        System.out.println("2. NORMAL");
        System.out.println("3. SPORT");
        CarProfile.EfficiencyMode efficiencyMode = getEfficiencyMode();

        try {
            EnhancedRangeCalculationStrategy strategy = RangeCalculationStrategyFactory.createEnhancedStrategy(
                terrain, weather, temperature, environment, averageSpeed, stateOfCharge, batteryTemperature);

            double range = strategy.calculateRange(carProfile, efficiencyMode);
            
            double baseConsumption = carProfile.getConsumptionProfile().getAverageConsumption();
            double consumption = baseConsumption * efficiencyMode.getConsumptionFactor();
            double combinedFactor = strategy.getEfficiencyFactors().stream()
                .mapToDouble(EfficiencyFactor::calculateFactor)
                .reduce(1.0, (a, b) -> a * b);
            consumption *= combinedFactor;

            System.out.println("\n=== Range Calculation Results ===");
            System.out.println("Estimated range: " + String.format("%.1f", range) + " km");
            System.out.println("Average consumption: " + String.format("%.1f", consumption) + " kWh/100km");
            
            System.out.println("\nInput:");
            System.out.println("- Terrain: " + terrain);
            System.out.println("- Weather: " + weather);
            System.out.println("- Temperature: " + temperature + "°C");
            System.out.println("- Environment: " + environment);
            System.out.println("- Average Speed: " + averageSpeed + " km/h");
            System.out.println("- Battery SoC: " + stateOfCharge + "%");
            System.out.println("- Battery Temperature: " + batteryTemperature + "°C");
            System.out.println("- Efficiency Mode: " + efficiencyMode);

            System.out.println("\nImpact due to Conditions:");
            System.out.println("- Weather impact: " + getWeatherImpactDescription(weather, temperature));
            System.out.println("- Terrain impact: " + getTerrainImpactDescription(terrain));
            System.out.println("- Environment impact: " + getEnvironmentImpactDescription(environment, averageSpeed));
            System.out.println("- Battery condition: " + getBatteryConditionDescription(stateOfCharge, batteryTemperature));
            
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();

        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private String getWeatherImpactDescription(WeatherCondition weather, double temperature) {
        StringBuilder impact = new StringBuilder();
        switch (weather) {
            case SNOW:
                impact.append("Severe impact - Snow conditions significantly reduce range");
                break;
            case RAIN:
                impact.append("Moderate impact - Rain increases rolling resistance");
                break;
            case STRONG_WIND:
                impact.append("Significant impact - Strong wind increases air resistance");
                break;
            case CLOUDY:
                impact.append("Minor impact - Normal driving conditions");
                break;
            case SUNNY:
                impact.append("Optimal conditions");
                break;
        }
        
        if (temperature < 10) {
            impact.append(", Cold temperature reduces battery efficiency");
        } else if (temperature > 30) {
            impact.append(", High temperature affects battery cooling");
        }
        return impact.toString();
    }

    private String getTerrainImpactDescription(TerrainType terrain) {
        switch (terrain) {
            case MOUNTAINOUS:
                return "High impact - Mountainous terrain significantly increases energy consumption";
            case HILLY:
                return "Moderate impact - Hills affect energy consumption";
            case FLAT:
                return "Optimal conditions - Flat terrain provides best efficiency";
            default:
                return "Unknown terrain impact";
        }
    }

    private String getEnvironmentImpactDescription(DrivingEnvironment environment, double speed) {
        switch (environment) {
            case CITY:
                return "Stop-and-go traffic at " + speed + " km/h - Frequent regenerative braking";
            case RURAL:
                return "Mixed driving at " + speed + " km/h - Balanced energy consumption";
            case HIGHWAY:
                return "Constant high speed at " + speed + " km/h - Increased air resistance";
            default:
                return "Unknown environment impact";
        }
    }

    private String getBatteryConditionDescription(double soc, double temp) {
        StringBuilder condition = new StringBuilder();
        
        if (soc > 80) {
            condition.append("High SoC (").append(soc).append("%) - Optimal for long trips");
        } else if (soc > 20) {
            condition.append("Medium SoC (").append(soc).append("%) - Good operating range");
        } else {
            condition.append("Low SoC (").append(soc).append("%) - Consider charging soon");
        }
        
        condition.append(", Battery temperature ");
        if (temp < 10) {
            condition.append("cold (").append(temp).append("°C) - Reduced efficiency");
        } else if (temp < 30) {
            condition.append("optimal (").append(temp).append("°C)");
        } else {
            condition.append("high (").append(temp).append("°C) - Cooling needed");
        }
        
        return condition.toString();
    }

    private TerrainType getTerrainType() {
        while (true) {
            System.out.print("\nSelect terrain type (1-3): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: return TerrainType.FLAT;
                    case 2: return TerrainType.HILLY;
                    case 3: return TerrainType.MOUNTAINOUS;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private WeatherCondition getWeatherCondition() {
        while (true) {
            System.out.print("\nSelect weather condition (1-5): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: return WeatherCondition.SUNNY;
                    case 2: return WeatherCondition.CLOUDY;
                    case 3: return WeatherCondition.RAIN;
                    case 4: return WeatherCondition.SNOW;
                    case 5: return WeatherCondition.STRONG_WIND;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private DrivingEnvironment getDrivingEnvironment() {
        while (true) {
            System.out.print("\nSelect driving environment (1-3): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: return DrivingEnvironment.CITY;
                    case 2: return DrivingEnvironment.RURAL;
                    case 3: return DrivingEnvironment.HIGHWAY;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private CarProfile.EfficiencyMode getEfficiencyMode() {
        while (true) {
            System.out.print("\nSelect efficiency mode (1-3): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: return CarProfile.EfficiencyMode.ECO;
                    case 2: return CarProfile.EfficiencyMode.NORMAL;
                    case 3: return CarProfile.EfficiencyMode.SPORT;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
} 