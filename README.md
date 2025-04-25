# ULRICA

→ ***U***niversa***L*** ***R***ange and dest***I***nation ***CA***lculator

## Project Overview
ULRICA is a range estimation and route planning tool for electric vehicles. It calculates remaining range, charging times, and optimizes route planning based on various conditions including weather, terrain, driving environment, and battery profile. The application implements Clean Architecture principles, providing a modular and maintainable design.

## Technical Requirements
- Java 17 or higher
- Maven
- Git

## Getting Started
1. Clone the repository
```bash
git clone https://github.com/mausio/ULRICA
```
2. Navigate to the project directory
```bash
cd ULRICA
```
3. Compile the project
```bash
mvn clean compile
```
4. Run ULRICA
```bash
mvn exec:java -Dexec.mainClass="org.ulrica.App"
```
5. Create an executable JAR
```bash
mvn clean package
java -jar target/ULRICA-1.0-SNAPSHOT.jar
```

## Architecture Overview
ULRICA follows **Clean Architecture principles** with a clear separation of concerns across multiple layers.

### Current Architecture Implementation

#### Domain Layer (Core)
Contains business entities, value objects, and domain services.
```
src/main/java/org/ulrica/domain/
├── ApplicationState.java             # Application state management
├── entity/                           # Domain entities
│   └── CarProfile.java               # Core entity representing vehicle data
├── repository/                       # Repository interfaces
│   └── CarProfileRepository.java     # Interface for car profile persistence
├── service/                          # Domain services
│   ├── AcChargingCalculator.java     # AC charging calculations
│   ├── ActionAvailabilityService.java # Available actions management
│   ├── ActionAvailabilityServiceImpl.java
│   ├── ConsumptionBasedRangeCalculationStrategy.java
│   ├── DcChargingCalculator.java     # DC charging calculations
│   ├── ProfileSelectionService.java  # Profile selection management
│   ├── ProfileSelectionServiceImpl.java
│   ├── RangeCalculationStrategyInterface.java
│   ├── RangeCalculatorService.java   # Range calculation service
│   └── WltpBasedRangeCalculationStrategy.java
└── valueobject/                      # Value objects
    ├── BatteryProfile.java           # Battery specifications
    ├── BatteryType.java              # Battery chemistry types
    ├── ChargingCurve.java            # Charging curve specifications
    ├── ConsumptionProfile.java       # Vehicle consumption data
    ├── DrivingEnvironment.java       # Driving environment types
    ├── EfficiencyMode.java           # Efficiency modes (ECO, NORMAL, SPORT)
    ├── RangeParameters.java          # Parameters for range calculation
    ├── RangeResult.java              # Range calculation results
    ├── TerrainType.java              # Terrain types
    └── WeatherType.java              # Weather conditions
```

#### Application Layer
Implements use cases through interactors and defines ports for communication.
```
src/main/java/org/ulrica/application/
├── port/                             # Input/Output ports
│   ├── in/                           # Input ports (use case interfaces)
│   │   ├── CalculateAcChargingUseCaseInterface.java
│   │   ├── CalculateDcChargingUseCaseInterface.java
│   │   ├── CalculateRangeUseCaseInterface.java
│   │   ├── CreateCarProfileUseCaseInterface.java
│   │   ├── ExecuteActionUseCaseInterface.java
│   │   ├── InputValidationServiceInterface.java
│   │   ├── NavigationUseCaseInterface.java
│   │   ├── ProfileSelectionListener.java
│   │   ├── ShowActionMenuUseCaseInterface.java
│   │   ├── ShowCarProfileMenuUseCaseInterface.java
│   │   ├── ShowMainMenuUseCaseInterface.java
│   │   ├── ShowWelcomeUseCaseInterface.java
│   │   └── ValidationInterface.java
│   └── out/                          # Output ports
│       ├── AcChargingOutputPortInterface.java
│       ├── ActionMenuOutputPortInterface.java
│       ├── ActionResultOutputPortInterface.java
│       ├── CarProfileMenuOutputPort.java
│       ├── CarProfilePersistencePortInterface.java
│       ├── DcChargingOutputPortInterface.java
│       ├── MainMenuOutputPortInterface.java
│       ├── RangeCalculationOutputPortInterface.java
│       ├── UserInputPortInterface.java
│       ├── UserOutputPortInterface.java
│       └── WelcomeOutputPortInterface.java
├── service/                          # Application services
│   ├── InputValidationService.java   # Input validation
│   └── UserInputService.java         # User input handling
└── usecase/                          # Use case implementations
    ├── CalculateAcChargingInteractor.java
    ├── CalculateDcChargingInteractor.java
    ├── CalculateRangeInteractor.java
    ├── CreateCarProfileInteractor.java
    ├── ExecuteActionInteractor.java
    ├── NavigationUseCase.java
    ├── SelectProfileUseCase.java
    ├── ShowActionMenuInteractor.java
    ├── ShowCarProfileMenuInteractor.java
    ├── ShowMainMenuInteractor.java
    └── ShowWelcomeInteractor.java
```

#### Infrastructure Layer
Provides concrete implementations of repositories and adapters.
```
src/main/java/org/ulrica/infrastructure/
├── adapter/                          # External adapters
│   ├── ConsoleUserInputAdapter.java  # Console input
│   └── ConsoleUserOutputAdapter.java # Console output
├── persistence/                      # Repository implementations
│   ├── JsonCarProfileRepository.java # JSON-based storage
│   └── OptionalTypeAdapterFactory.java # JSON serialization support
└── util/                             # Utilities
    └── ValidationUtils.java          # Validation utilities
```

#### Presentation Layer
Handles user interaction through controllers and views.
```
src/main/java/org/ulrica/presentation/
├── controller/                       # Controllers
│   ├── AcChargingController.java     # AC charging controller
│   ├── ActionMenuController.java     # Action menu controller
│   ├── ApplicationControllerWithActionMenu.java # Main controller
│   ├── DcChargingController.java     # DC charging controller
│   └── RangeCalculationController.java # Range calculation controller
├── util/                             # Presentation utilities
│   ├── AnsiColors.java               # Console color formatting
│   └── InputValidator.java           # Input validation
└── view/                             # Views
    ├── AcChargingView.java           # AC charging view
    ├── ActionMenuView.java           # Action menu view
    ├── ActionResultView.java         # Action result view
    ├── CarProfileView.java           # Car profile management view
    ├── DcChargingView.java           # DC charging view
    ├── MainMenuView.java             # Main menu view
    ├── RangeCalculationView.java     # Range calculation view
    └── WelcomeView.java              # Welcome screen view
```

### Dependency Flow
The dependency flow follows the Clean Architecture principle of depending inward:

```
Presentation → Application → Domain ← Infrastructure
    ↓             ↓             ↑          ↑
Controllers    Use Cases      Entities     |
    ↓             ↓             ↑          |
   Views          ↓           Services     |
                  ↓             ↑          |
              Input Ports       ↑          |
                  ↓             ↑          |
              Output Ports -----+----------+
```

### Design Patterns Implemented

1. **Strategy Pattern**
   - `RangeCalculationStrategyInterface` with implementations:
     - `WltpBasedRangeCalculationStrategy`
     - `ConsumptionBasedRangeCalculationStrategy`

2. **Repository Pattern**
   - `CarProfileRepository` interface
   - `JsonCarProfileRepository` implementation

3. **Builder Pattern**
   - `CarProfile.Builder` for creating car profiles

4. **Adapter Pattern**
   - `ConsoleUserInputAdapter` and `ConsoleUserOutputAdapter`

5. **Dependency Injection**
   - Manual dependency injection in `App.java`

## Core Features

### Car Profile Management
- Create, view, edit, and delete car profiles
- Define battery specifications (type, capacity, degradation)
- Configure consumption profiles and charging curves
- JSON-based persistence

### Range Calculation
- Calculate range based on multiple factors:
  - Terrain conditions (flat, hilly, mountainous)
  - Weather conditions (sunny, cloudy, rain, snow, strong wind)
  - Temperature impacts
  - Driving environment (city, rural, highway)
  - Efficiency mode (ECO, NORMAL, SPORT)
- Detailed impact analysis of environmental conditions

### Charging Calculation
- DC (Fast) Charging:
  - SoC-based calculations
  - Temperature effects on charging
  - Power reduction based on battery state
  - Detailed charging time estimates
- AC (Slow) Charging:
  - Multiple connector types (household, camping, wallbox)
  - Efficiency loss calculations
  - Temperature efficiency factors

## Dependencies
- **JUnit**: Testing framework
- **GSON**: JSON serialization/deserialization
- **Jackson**: JSON data binding

## Testing
The project includes JUnit tests with JaCoCo for code coverage analysis. A minimum coverage of 50% is enforced for line coverage.

```bash
# Run tests with coverage
mvn test
```

## Application Flow

### Main Menu Navigation
```
=== ULRICA - Main Menu ===
Currently selected profile: [Profile Name]
1. Car Profile Management
2. Actions Menu
3. Exit
```

### Car Profile Management
```
Car Profile Management
1. View all car profiles
2. Create new car profile
3. Delete car profile
4. Edit car profile
5. Back to main menu
```

### Actions Menu
```
=== Action Menu ===
Selected Profile: [Profile Name]

Available Actions:
1. DC (Fast) Charging Calculator
2. AC (Slow) Charging Calculator
3. Calculate Range with Current Conditions
4. Back to Main Menu
```

### Range Calculation Flow
1. Select terrain conditions
2. Select weather conditions
3. Enter temperature
4. Select driving environment
5. Select efficiency mode
6. View detailed range calculation results and impact analysis

### Charging Calculation Flow
1. Choose DC or AC charging
2. For AC, select connector type
3. Enter state of charge parameters
4. Enter temperature
5. View charging time estimate and efficiency details