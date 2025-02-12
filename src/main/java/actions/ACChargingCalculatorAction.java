package actions;

import interfaces.ProfileAction;
import model.CarProfile;
import model.charging.ACConnectorType;
import service.ACChargingCalculatorService;

public class ACChargingCalculatorAction implements ProfileAction {
    private final ACChargingCalculatorService calculatorService;

    public ACChargingCalculatorAction() {
        this.calculatorService = new ACChargingCalculatorService();
    }

    @Override
    public void execute(CarProfile profile) {
        System.out.println("\n=== AC (Slow) Charging Calculator ===");
        
        System.out.println("Available AC connectors:");
        System.out.println("1. Household Socket (2.3 kW)");
        System.out.println("2. Camping Socket (3.7 kW)");
        System.out.println("3. Wallbox (11 kW)");
        
        ACConnectorType connectorType = getConnectorType();
        if (connectorType == null) {
            return;
        }

        double currentSoC = getValidPercentage("Enter current State of Charge (%) [0-100]: ");
        double targetSoC = getValidPercentage("Enter target State of Charge (%) [0-100]: ");
        double temperature = getValidTemperature();

        String result = calculatorService.calculateChargingTime(
            profile.getBatteryProfile().getCurrentCapacity(),
            currentSoC,
            targetSoC,
            temperature,
            connectorType,
            profile.getMaxAcChargingPower()
        );

        System.out.println("\n=== Charging Time Estimate ===");
        System.out.println(result);
        waitForEnter();
    }

    private ACConnectorType getConnectorType() {
        while (true) {
            System.out.print("\nSelect connector type (1-3): ");
            try {
                int choice = Integer.parseInt(System.console().readLine().trim());
                switch (choice) {
                    case 1: return ACConnectorType.HOUSEHOLD_SOCKET;
                    case 2: return ACConnectorType.CAMPING_SOCKET;
                    case 3: return ACConnectorType.WALLBOX_11KW;
                    default:
                        System.out.println("Invalid choice. Please select 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private double getValidPercentage(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(System.console().readLine().trim());
                if (value >= 0 && value <= 100) {
                    return value;
                }
                System.out.println("Please enter a value between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private double getValidTemperature() {
        while (true) {
            System.out.print("Enter ambient temperature (°C): ");
            try {
                return Double.parseDouble(System.console().readLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a temperature in Celsius.");
            }
        }
    }

    private void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
        System.console().readLine();
    }

    @Override
    public String getDisplayName() {
        return "AC (Slow) Charging Calculator";
    }
} 