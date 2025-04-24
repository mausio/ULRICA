# ULRICA

→ ***U***niversa***L*** ***R***ange and dest***I***nation ***CA***lculator

### **Project Overview**
ULRICA is a range estimation and route planning tool designed to calculate the remaining range of electric vehicles, estimate charging times, and optimize route planning based on weather conditions and battery profiles. The application supports multiple car profiles, consumption models, and charging profiles to provide accurate estimations.

### **Technical Requirements**
- Java 17 or higher
- Maven
- Git
- Minimum **2000+ lines** of code
- At least **20+ Java classes**
- Comprehensive test coverage
- SOLID and design pattern implementation

### **Getting Started**
1. Clone the repository
```bash
git clone https://github.com/mausio/ULRICA
```
2. Navigate to the project directory
```bash
cd /path/to/ULRICA
```
3. Compile the project
```bash
mvn clean compile
```
4. Run ULRICA
```bash
mvn exec:java -Dexec.mainClass="org.ulrica.App"
```
5. (Optional) Create an executable JAR
```bash
mvn clean package
java -jar target/ULRICA-1.0-SNAPSHOT.jar
```

---

## **Architecture Overview**
ULRICA follows **Clean Architecture principles** with a clear separation of concerns across multiple layers, ensuring maintainability, testability, and flexibility.

### **Clean Architecture Principles**
1. **Dependency Rule**
   - Source code dependencies must point inward
   - Inner layers must not know about outer layers
   - Outer layers can depend on inner layers
   - Dependencies cross boundaries through interfaces

2. **Layer Independence**
   - Domain layer is completely independent
   - Application layer depends only on domain
   - Infrastructure layer depends on application and domain
   - Presentation layer depends on application

3. **Interface Ownership**
   - Interfaces are owned by the layer that uses them
   - Implementation details are in outer layers
   - Inner layers define the contracts

4. **Use Case Focus**
   - Application layer organized around use cases
   - Each use case is a separate class
   - Use cases orchestrate domain objects
   - Clear input/output boundaries

### **Architectural Layers**
1. **Domain Layer (Core)**
   - Contains business entities, value objects, and aggregates
   - Defines repository interfaces and domain services
   - Implements strategy patterns for core calculations
   - Completely independent of external frameworks and technologies
   - Examples:
     - `CarProfile` entity
     - `BatteryProfile` value object
     - `RangeCalculationStrategy` interface

2. **Application Layer**
   - Implements use cases through interactors
   - Defines input and output ports for communication between layers
   - Orchestrates domain objects to fulfill business requirements
   - Depends only on the domain layer
   - Examples:
     - `LoadCarProfileUseCase`
     - `CalculateRangeUseCase`
     - `ChargingCalculatorUseCase`

3. **Infrastructure Layer**
   - Provides concrete implementations of repositories
   - Contains adapters for external services
   - Implements technical concerns (persistence, logging, etc.)
   - Depends on domain and application layers
   - Examples:
     - `JsonCarProfileRepository`
     - `ConsoleInteractorUtil`
     - `RangeCalculationView`

4. **Presentation Layer**
   - Handles user interaction through controllers and actions
   - Transforms use case outputs into appropriate UI formats
   - Contains no business logic
   - Depends on application layer through input ports
   - Examples:
     - `MainController`
     - `WelcomeController`
     - `RangeCalculationView`

### **Clean Architecture Benefits in ULRICA**
1. **Testability**
   - Domain layer can be tested without infrastructure
   - Use cases can be tested with mock repositories
   - UI can be tested independently

2. **Maintainability**
   - Clear separation of concerns
   - Easy to modify individual layers
   - Business rules are isolated

3. **Flexibility**
   - Easy to change infrastructure (e.g., JSON to database)
   - Easy to add new features
   - Easy to modify UI without touching business logic

4. **Independence**
   - Business rules are framework-independent
   - Database decisions can be deferred
   - UI decisions can be changed without affecting core

### **Architecture Requirements**
- Dependency rule: inner layers don't know about outer layers
- Use of ports and adapters for flexible integration
- Clear boundaries between layers using interfaces
- Domain-driven design principles throughout
- Examples should be from current state of the project
- Use original terminology (no translations)

---

## **Features**
### **Core Functionality**
- **Car Profile Management**
  - CRUD operations for car profiles
  - JSON-based persistence
  - Detailed profile creation with:
    - Basic vehicle information (name, manufacturer, model, year)
    - Heat pump configuration
    - WLTP range and charging capabilities
    - Battery profile (type, capacity, degradation)
    - Consumption profiles for different speeds
    - Custom charging curves
  - Profile selection and switching

- **Range Calculation**
  - Multiple calculation strategies:
    - WLTP-based calculation
    - Consumption-based calculation
  - Environmental factors:
    - Terrain conditions (flat, hilly, mountainous)
    - Weather conditions (sunny, cloudy, rain, snow, strong wind)
    - Temperature impact
  - Driving conditions:
    - City, rural, and highway environments
    - Different efficiency modes (ECO, NORMAL, SPORT)
  - Detailed impact analysis:
    - Weather impact on range
    - Terrain impact on consumption
    - Environment impact on efficiency
    - Battery condition effects

- **Charging Calculation**
  - DC (Fast) Charging:
    - State of Charge (SoC) based calculations
    - Temperature-dependent charging
    - Power reduction for battery longevity
    - Detailed charging time estimates
    - Battery chemistry considerations
  - AC (Slow) Charging:
    - Multiple connector types:
      - Household Socket (2.3 kW)
      - Camping Socket (3.7 kW)
      - Wallbox (11 kW)
    - Efficiency loss calculations
    - Temperature efficiency factors
    - Detailed charging time estimates

- **Battery Management**
  - Support for different battery types:
    - LFP (Lithium Iron Phosphate)
    - NMC (Nickel Manganese Cobalt)
    - NCA (Nickel Cobalt Aluminum)
  - Degradation tracking
  - Temperature monitoring
  - Capacity calculations

### **Technical Features**
- Thread-safe operations
- Extensible plugin architecture
- Custom JSON serialization
- Robust error handling
- Modular and maintainable design
- Interactive CLI interface
- Detailed logging and status reporting

### **Implementation Requirements**
- All tasks must be completed according to description
  - No statements like "not needed" or "not implemented"
  - Examples:
    - Fake/Mock objects: "not needed" → 0P
    - Repository: "not implemented" → 0P
    - Design patterns: "not needed" → 0P
- Missing negative examples require more positive examples
- For "Refactoring" chapter: any state can be used

---

## **Application Flow**
### **1. Application Initialization**
- Welcome screen display
- Dark mode recommendation
- Main menu initialization

### **2. Car Profile Management**
- Profile loading from JSON
- Profile creation workflow:
  1. Basic information input
  2. Battery profile setup
  3. Consumption profile creation
  4. Charging curve configuration
- Profile selection and switching
- Profile editing and deletion

### **3. Calculation Workflows**
- Range computation:
  1. Terrain condition selection
  2. Weather condition input
  3. Temperature input
  4. Driving environment selection
  5. Efficiency mode selection
  6. Detailed results display
- Charging calculations:
  1. DC charging:
     - SoC input
     - Temperature input
     - Power station selection
     - Time estimation
  2. AC charging:
     - Connector type selection
     - SoC input
     - Temperature input
     - Time estimation

### **4. Results and Analysis**
- Detailed range estimates
- Consumption calculations
- Impact analysis:
  - Weather effects
  - Terrain effects
  - Environment effects
  - Battery condition
- Charging time estimates
- Efficiency calculations

---

## **Design Patterns & Principles**
### **Implemented Patterns**
1. **Strategy Pattern**
   - Range calculation strategies
   - Charging calculation strategies
   - Efficiency mode strategies
2. **Repository Pattern**
   - Car profile persistence
   - JSON-based storage
3. **Factory Method Pattern**
   - Battery type creation
   - Profile creation
4. **Command Pattern**
   - User action handling
   - Menu navigation
5. **Observer Pattern**
   - Status updates
   - Progress monitoring
6. **Template Method Pattern**
   - Calculation workflows
   - Profile creation process

### **Clean Architecture Structure**

### **1. Directory Structure**
```
src/main/java/org/ulrica/
├── domain/                           # Domain Layer (Core Business Logic)
│   ├── entity/                       # Business Entities
│   │   ├── CarProfile.java
│   │   ├── BatteryProfile.java
│   │   └── ChargingProfile.java
│   ├── valueobject/                  # Value Objects
│   │   ├── Range.java
│   │   ├── Consumption.java
│   │   └── ChargingCurve.java
│   ├── repository/                   # Repository Interfaces
│   │   ├── CarProfileRepository.java
│   │   └── BatteryProfileRepository.java
│   ├── service/                      # Domain Services
│   │   ├── RangeCalculationService.java
│   │   └── ChargingCalculationService.java
│   └── exception/                    # Domain Exceptions
│       ├── InvalidProfileException.java
│       └── CalculationException.java
│
├── application/                      # Application Layer (Use Cases)
│   ├── port/                         # Ports (Interfaces)
│   │   ├── in/                       # Input Ports
│   │   │   ├── LoadCarProfileUseCase.java
│   │   │   ├── CalculateRangeUseCase.java
│   │   │   └── ChargingCalculatorUseCase.java
│   │   └── out/                      # Output Ports
│   │       ├── CarProfileOutputPort.java
│   │       └── CalculationOutputPort.java
│   └── usecase/                      # Use Case Implementations
│       ├── LoadCarProfileInteractor.java
│       ├── CalculateRangeInteractor.java
│       └── ChargingCalculatorInteractor.java
│
├── infrastructure/                    # Infrastructure Layer (Technical Details)
│   ├── persistence/                  # Repository Implementations
│   │   ├── JsonCarProfileRepository.java
│   │   └── JsonBatteryProfileRepository.java
│   ├── adapter/                      # Adapters
│   │   ├── in/                       # Input Adapters
│   │   │   └── console/              # Console Input Adapters
│   │   └── out/                      # Output Adapters
│   │       └── console/              # Console Output Adapters
│   └── util/                         # Utilities
│       ├── JsonUtil.java
│       └── ConsoleUtil.java
│
└── presentation/                      # Presentation Layer (UI)
    ├── controller/                    # Controllers
    │   ├── MainController.java
    │   ├── CarProfileController.java
    │   └── CalculationController.java
    └── view/                          # Views
        ├── MainView.java
        ├── CarProfileView.java
        └── CalculationView.java
```

### **Core Features by Layer**

#### **Domain Layer**
- **Car Profile Management**
  - Profile Creation/Validation
  - Battery Profile Management
  - Charging Profile Management
- **Range Calculation**
  - Basic Range Calculation
  - Environmental Impact Calculation
  - Battery Impact Calculation
- **Charging Calculation**
  - DC Charging Calculation
  - AC Charging Calculation
  - Temperature Impact Calculation

#### **Application Layer**
- **Use Cases**
  - Load Car Profile
  - Calculate Range
  - Calculate Charging Time
  - Manage Profiles
- **Ports**
  - Profile Management Ports
  - Calculation Ports
  - Data Persistence Ports

#### **Infrastructure Layer**
- **Persistence**
  - JSON File Storage
  - Profile Serialization/Deserialization
- **Adapters**
  - Console Input/Output
  - File System Operations
- **Utilities**
  - JSON Processing
  - Console Formatting

#### **Presentation Layer**
- **User Interface**
  - Main Menu
  - Profile Management Interface
  - Calculation Interface
- **User Input Handling**
  - Command Processing
  - Data Validation
- **Result Presentation**
  - Formatted Output
  - Error Display

### **Design Patterns & Principles**

#### **Domain Layer**
- `CarProfile`: Core entity representing a vehicle's profile
- `BatteryProfile`: Value object for battery specifications
- `ChargingProfile`: Value object for charging characteristics
- `Range`: Value object for range calculations
- `Consumption`: Value object for consumption metrics
- `ChargingCurve`: Value object for charging curves
- `CarProfileRepository`: Interface for profile persistence
- `RangeCalculationService`: Domain service for range calculations
- `ChargingCalculationService`: Domain service for charging calculations

#### **Application Layer**
- `LoadCarProfileUseCase`: Interface for loading profiles
- `CalculateRangeUseCase`: Interface for range calculations
- `ChargingCalculatorUseCase`: Interface for charging calculations
- `LoadCarProfileInteractor`: Implementation of profile loading
- `CalculateRangeInteractor`: Implementation of range calculation
- `ChargingCalculatorInteractor`: Implementation of charging calculation

#### **Infrastructure Layer**
- `JsonCarProfileRepository`: JSON-based profile persistence
- `JsonBatteryProfileRepository`: JSON-based battery profile persistence
- `ConsoleInputAdapter`: Console input handling
- `ConsoleOutputAdapter`: Console output formatting
- `JsonUtil`: JSON processing utilities
- `ConsoleUtil`: Console formatting utilities

#### **Presentation Layer**
- `MainController`: Main application flow control
- `CarProfileController`: Profile management control
- `CalculationController`: Calculation flow control
- `MainView`: Main menu display
- `CarProfileView`: Profile management interface
- `CalculationView`: Calculation results display

### **4. Ubiquitous Language**

#### **Core Domain Terms**
- **CarProfile**: Complete vehicle specification including battery, consumption, and charging characteristics
- **BatteryProfile**: Battery specifications including type, capacity, and degradation
- **ChargingProfile**: Charging characteristics including curves and power limits
- **Range**: Estimated travel distance under specific conditions
- **SoC (State of Charge)**: Current battery charge level (0-100%)
- **ChargingCurve**: Power delivery profile based on battery level
- **Consumption**: Energy usage per distance unit (kWh/100km)
- **WLTP**: Worldwide Harmonized Light Vehicles Test Procedure
- **DC Charging**: Direct Current fast charging
- **AC Charging**: Alternating Current slow charging

### **5. Feature Implementation Mapping**

#### **Domain Layer Implementation**
- Core business rules
- Entity validation
- Value object calculations
- Domain service logic

#### **Application Layer Implementation**
- Use case orchestration
- Input validation
- Output formatting
- Business rule enforcement

#### **Infrastructure Layer Implementation**
- Data persistence
- External service integration
- Technical utilities
- Adapter implementations

#### **Presentation Layer Implementation**
- User interface
- Input processing
- Output formatting
- Error handling

### **6. Repository Pattern Implementation**

#### **Domain Layer**
```java
public interface CarProfileRepository {
    CarProfile findById(String id);
    List<CarProfile> findAll();
    void save(CarProfile profile);
    void delete(String id);
}
```

2. **Strategy Pattern**
   - Range calculation strategies
   - Charging calculation strategies
   - Efficiency mode strategies

3. **Factory Method Pattern**
   - Battery type creation
   - Profile creation

#### **SOLID Principles**
- **Single Responsibility**: Each class has one reason to change
- **Open/Closed**: Extensible through interfaces and implementations
- **Liskov Substitution**: Proper inheritance and interface implementation
- **Interface Segregation**: Focused interfaces for specific needs
- **Dependency Inversion**: High-level modules depend on abstractions

#### **GRASP Principles**
- **Low Coupling**: Minimal dependencies between components
- **High Cohesion**: Related functionality grouped together
- **Polymorphism**: Interface-based implementations
- **Pure Fabrication**: Technical classes for specific purposes

### **Data Flow**
```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  Presentation   │     │  Application    │     │    Domain       │
│     Layer       │     │     Layer       │     │     Layer       │
├─────────────────┤     ├─────────────────┤     ├─────────────────┤
│  Controller     │────▶│   Use Case      │────▶│   Entity        │
│  View          │     │   Interactor    │     │   Value Object  │
└─────────────────┘     └─────────────────┘     └─────────────────┘
        │                       │                       │
        ▼                       ▼                       ▼
┌─────────────────┐     ┌─────────────────┐
│ Infrastructure  │     │  External       │
│     Layer       │     │  Services       │
├─────────────────┤     ├─────────────────┤
│  Repository     │     │  Weather API    │
│  Adapter        │     │  Maps API       │
└─────────────────┘     └─────────────────┘
```

### **Ubiquitous Language**
- **CarProfile**: Complete vehicle specification
- **BatteryProfile**: Battery specifications
- **ChargingProfile**: Charging characteristics
- **Range**: Estimated travel distance
- **SoC**: State of Charge (0-100%)
- **ChargingCurve**: Power delivery profile
- **Consumption**: Energy usage (kWh/100km)
- **WLTP**: Worldwide Harmonized Light Vehicles Test Procedure
- **DC Charging**: Direct Current fast charging
- **AC Charging**: Alternating Current slow charging

---

## **Development Guidelines**

### **Code Style**
- Follow **Conventional Commits**: [Link](https://www.conventionalcommits.org/en/v1.0.0/)
- Use **Gitmoji** for meaningful commit messages: [Link](https://gitmoji.dev/)
- Maintain consistent formatting

### **Testing Strategy**
- **Domain Layer Testing**
  - 100% unit test coverage for core business logic
  - Test-driven development (TDD) approach
  - No external dependencies in domain tests
  - Focus on business rules and validation
  - Example: `CarProfileTest`, `BatteryProfileTest`

- **Application Layer Testing**
  - Use case testing with mock repositories
  - Input validation testing
  - Business rule enforcement testing
  - Example: `CalculateRangeUseCaseTest`, `ChargingCalculatorUseCaseTest`

- **Infrastructure Layer Testing**
  - Integration tests for repositories
  - Adapter testing with real implementations
  - Technical concern testing
  - Example: `JsonCarProfileRepositoryTest`, `ConsoleAdapterTest`

- **Test Automation**
  - Continuous Integration with GitHub Actions
  - JaCoCo coverage reporting
  - Minimum 50% line coverage requirement
  - Automated test execution on every commit

### **SOLID Principles Implementation**

#### **Single Responsibility Principle (SRP)**
- **Positive Example**: `CarProfile` class
  - Only responsible for managing car profile data
  - No UI, persistence, or calculation logic
  - Clear, focused responsibility

- **Negative Example**: `CarProfileManager` (refactored)
  - Previously handled UI, persistence, and validation
  - Refactored into separate classes:
    - `CarProfileService` (business logic)
    - `CarProfileRepository` (persistence)
    - `CarProfileController` (UI)

#### **Open/Closed Principle (OCP)**
- **Positive Example**: `RangeCalculationStrategy`
  - Interface for different calculation methods
  - New strategies can be added without modifying existing code
  - Extensible through inheritance

- **Negative Example**: `ChargingCalculator` (refactored)
  - Previously used switch statements for different battery types
  - Refactored to use strategy pattern
  - Each battery type implements its own charging strategy

#### **Liskov Substitution Principle (LSP)**
- **Positive Example**: `BatteryProfile` hierarchy
  - Base class defines common battery behavior
  - Subclasses (LFP, NMC, NCA) can be used interchangeably
  - No violation of base class contracts

#### **Interface Segregation Principle (ISP)**
- **Positive Example**: `CarProfileRepository`
  - Focused interface with specific methods
  - No unused methods in implementations
  - Clear separation of concerns

#### **Dependency Inversion Principle (DIP)**
- **Positive Example**: `CarProfileService`
  - Depends on `CarProfileRepository` interface
  - No direct dependency on concrete implementations
  - Easy to swap implementations

### **GRASP Principles Implementation**

#### **Low Coupling**
- **Example**: `RangeCalculationService`
  - Minimal dependencies on other classes
  - Uses interfaces for communication
  - Easy to modify without affecting other components

#### **High Cohesion**
- **Example**: `BatteryProfile`
  - All methods are related to battery management
  - Clear, focused responsibility
  - No unrelated functionality

#### **Polymorphism**
- **Example**: `ChargingStrategy`
  - Different implementations for various charging scenarios
  - Runtime selection of appropriate strategy
  - Extensible through new implementations

#### **Pure Fabrication**
- **Example**: `JsonUtil`
  - Technical class for JSON operations
  - No direct domain responsibility
  - Reusable across the application

### **Refactoring Opportunities**

#### **Code Smells and Solutions**
1. **Long Method**
   - Problem: Complex calculation methods
   - Solution: Extract methods, use strategy pattern
   - Example: `RangeCalculationService`

2. **Large Class**
   - Problem: Too many responsibilities
   - Solution: Split into smaller, focused classes
   - Example: `CarProfileManager` → Multiple services

3. **Primitive Obsession**
   - Problem: Using primitives for domain concepts
   - Solution: Create value objects
   - Example: `Range`, `Consumption`, `ChargingCurve`

4. **Feature Envy**
   - Problem: Methods accessing other classes' data
   - Solution: Move method to appropriate class
   - Example: `BatteryProfile` calculations

#### **Refactoring Workflow**
1. Identify code smells
2. Write tests for existing behavior
3. Apply refactoring techniques
4. Verify tests still pass
5. Commit changes with clear messages

### **Dependencies**
- **GSON**: JSON serialization/deserialization
- **JUnit**: Unit testing framework

## **Beispiel Abläufe**

### Start und Willkommensbildschirm
```zsh
            ██╗   ██╗██╗     ██████╗ ██╗ ██████╗ █████╗
            ██║   ██║██║     ██╔══██╗██║██╔════╝██╔══██╗
            ██║   ██║██║     ██████╔╝██║██║     ███████║
            ██║   ██║██║     ██╔══██╗██║██║     ██╔══██║
            ╚██████╔╝███████╗██║  ██║██║╚██████╗██║  ██║
             ╚═════╝ ╚══════╝╚═╝  ╚═╝╚═╝ ╚═════╝╚═╝  ╚═╝

┌────────────────────────────────────────────────────────────────────┐
│      Welcome to ULRICA - Your Range & Destination Calculator       │
├────────────────────────────────────────────────────────────────────┤
│  Let's calculate the perfect route or range for your electric car! │
└--------------------------------------------------------------------┘

→ Attention:
  This program's text is completely WHITE,
  so please consider using dark mode.
```

### Hauptmenü Navigation
```zsh
=== ULRICA - Main Menu ===
No car profile selected!
1. Car Profile Management
2. Exit

Enter your choice: 1
```

### CarProfile Management Menü
```zsh
Car Profile Management
1. View all car profiles
2. Create new car profile
3. Delete car profile
4. Edit car profile
5. Back to main menu
```

### CarProfile anzeigen und auswählen
```zsh
Car Profiles:

----------------------------------------
Name: Sir
Manufacturer: Opel
Model: Corsa
Build Year: 2020
Heat Pump: No
----------------------------------------

----------------------------------------
Name: Lami
Manufacturer: VW
Model: e-up
Build Year: 2018
Heat Pump: No
----------------------------------------

Would you like to select a profile? (yes/no): yes

Select a profile:
1. Sir
2. Lami

Enter number (1-2): 1
Selected profile: Sir
```

### CarProfile erstellen - Grundinformationen
```zsh
Create New Car Profile
Enter profile name: Sir Charge-A-Lot
Enter manufacturer: Opel
Enter model: Corsa
Enter build year: 2020
Has heat pump? (yes/no): y
Enter WLTP range (km): 337
Enter maximum DC charging power (kW): 100
Enter maximum AC charging power (kW): 11
```

### CarProfile erstellen - Batterieprofil
```zsh
Battery Profile Setup:
Available battery types:
1. LFP (Lithium Iron Phosphate)
2. NMC (Nickel Manganese Cobalt)
3. NCA (Nickel Cobalt Aluminum)
Select battery type (1-3): 2
Enter battery capacity (kWh): 44
Enter current battery degradation (%, press Enter for 0): 2
Remaining battery capacity: 43,1 kWh (98,0%)
```

### CarProfile erstellen - Verbrauchsprofil
```zsh
Creating consumption profile:
Enter consumption values for Normal mode (baseline):
Enter consumption at 50 km/h (kWh/100km): 14
Enter consumption at 100 km/h (kWh/100km): 16
Enter consumption at 130 km/h (kWh/100km): 19

Calculated ranges and consumptions for mixed driving style (city/rural/highway):

WLTP-based range calculation:
ECO Mode: 318,6 km (15,5 kWh/100km)
Normal Mode: 303,3 km (16,3 kWh/100km)
Sport Mode: 252,8 km (19,6 kWh/100km)
```

### CarProfile erstellen - Ladekurve
```zsh
Would you like to add a charging profile? (yes/no): y

Enter charging curve points (empty line to finish):
Format: <battery_percentage> <charging_power>
Example: 20 150  (means 150kW at 20% battery)

Enter point (or empty line to finish): 10 90
Enter point (or empty line to finish): 50 60
Enter point (or empty line to finish): 80 40
Enter point (or empty line to finish): 90 20
Enter point (or empty line to finish): 95 10
Enter point (or empty line to finish): 99 3
Enter point (or empty line to finish):

Charging Curve Overview:
Battery %  | Charging Power (kW)
----------|-----------------
    10,0% |     90,0 kW
    50,0% |     60,0 kW
    80,0% |     40,0 kW
    90,0% |     20,0 kW
    95,0% |     10,0 kW
    99,0% |      3,0 kW
```

### DC (Schnell-)Ladeberechnung
```zsh
=== DC (Fast) Charging Calculator ===
Enter starting State of Charge (%) [0-100]: 10
Enter target State of Charge (%) [0-100]: 80
Enter maximum charging station power (kW): 100
Enter ambient temperature (°C): 20

=== Charging Time Estimate ===
Charging Time: 35m
Battery Capacity: 44.0 kWh
Energy to be added: 30,8 kWh
Battery Chemistry: NMC
Temperature: 20,0°C → 25,9°C
Power Reduction: 30% (for battery longevity)
```

### AC (Langsam-)Ladeberechnung
```zsh
=== AC (Slow) Charging Calculator ===
Available AC connectors:
1. Household Socket (2.3 kW)
2. Camping Socket (3.7 kW)
3. Wallbox (11 kW)

Select connector type (1-3): 2
Enter current State of Charge (%) [0-100]: 10
Enter target State of Charge (%) [0-100]: 80
Enter ambient temperature (°C): 20

=== Charging Time Estimate ===
Charging time: 8 hours 46 minutes
Energy required: 30,2 kWh
Effective charging power: 3,4 kW
Efficiency loss: 7,0%
Temperature efficiency: 100,0%
```

### Reichweitenberechnung
```zsh
=== Terrain Conditions ===
1. Flat
2. Hilly
3. Mountainous

Select terrain type (1-3): 2

=== Weather Conditions ===
1. Sunny
2. Cloudy
3. Rain
4. Snow
5. Strong Wind

Select weather condition (1-5): 4
Enter temperature in Celsius: -2

=== Driving Environment ===
1. City
2. Rural
3. Highway

Select driving environment (1-3): 3
Enter current state of charge (0-100%): 80

=== Efficiency Mode ===
1. ECO
2. NORMAL
3. SPORT

Select efficiency mode (1-3): 2

=== Range Calculation Results ===
Estimated range: 178,5 km
Average consumption: 27,8 kWh/100km

Input:
- Terrain: HILLY
- Weather: SNOW
- Temperature: -2.0°C
- Environment: HIGHWAY
- Average Speed: 110.0 km/h
- Battery SoC: 80.0%
- Battery Temperature: -2.0°C
- Efficiency Mode: NORMAL

Impact due to Conditions:
- Weather impact: Severe impact - Snow conditions significantly reduce range, Cold temperature reduces battery efficiency
- Terrain impact: Moderate impact - Hills affect energy consumption
- Environment impact: Constant high speed at 110.0 km/h - Increased air resistance
- Battery condition: Medium SoC (80.0%) - Good operating range, Battery temperature cold (-2.0°C) - Reduced efficiency
```

### Gesamtbeispiel (Kompletter Ablauf)
```zsh
            ██╗   ██╗██╗     ██████╗ ██╗ ██████╗ █████╗
            ██║   ██║██║     ██╔══██╗██║██╔════╝██╔══██╗
            ██║   ██║██║     ██████╔╝██║██║     ███████║
            ██║   ██║██║     ██╔══██╗██║██║     ██╔══██║
            ╚██████╔╝███████╗██║  ██║██║╚██████╗██║  ██║
             ╚═════╝ ╚══════╝╚═╝  ╚═╝╚═╝ ╚═════╝╚═╝  ╚═╝

┌────────────────────────────────────────────────────────────────────┐
│      Welcome to ULRICA - Your Range & Destination Calculator       │
├────────────────────────────────────────────────────────────────────┤
│  Let's calculate the perfect route or range for your electric car! │
└--------------------------------------------------------------------┘

→ Attention:
  This program's text is completely WHITE,
  so please consider using dark mode.

=== ULRICA - Main Menu ===
No car profile selected!
1. Car Profile Management
2. Exit

Enter your choice: 1

Car Profile Management
1. View all car profiles
2. Create new car profile
3. Delete car profile
4. Edit car profile
5. Back to main menu

Enter your choice (1-5): 1

Car Profiles:

----------------------------------------
Name: Sir
Manufacturer: Opel
Model: Corsa
Build Year: 2020
Heat Pump: No
----------------------------------------

----------------------------------------
Name: Lami
Manufacturer: VW
Model: e-up
Build Year: 2018
Heat Pump: No
----------------------------------------

Would you like to select a profile? (yes/no): yes

Select a profile:
1. Sir
2. Lami

Enter number (1-2): 1
Selected profile: Sir

Press Enter to continue...

Car Profile Management
Currently selected profile: Sir
1. View all car profiles
2. Create new car profile
3. Delete car profile
4. Edit car profile
5. Back to main menu

Enter your choice (1-5): 2

Create New Car Profile
Enter profile name: Sir Charge-A-Lot
Enter manufacturer: Opel
Enter model: Corsa
Enter build year: 2020
Has heat pump? (yes/no): y
Enter WLTP range (km): 337
Enter maximum DC charging power (kW): 100
Enter maximum AC charging power (kW): 11

Battery Profile Setup:
Available battery types:
1. LFP (Lithium Iron Phosphate)
2. NMC (Nickel Manganese Cobalt)
3. NCA (Nickel Cobalt Aluminum)
Select battery type (1-3): 2
Enter battery capacity (kWh): 44
Enter current battery degradation (%, press Enter for 0): 2
Remaining battery capacity: 43,1 kWh (98,0%)

Creating consumption profile:
Enter consumption values for Normal mode (baseline):
Enter consumption at 50 km/h (kWh/100km): 14
Enter consumption at 100 km/h (kWh/100km): 16
Enter consumption at 130 km/h (kWh/100km): 19

Calculated ranges and consumptions for mixed driving style (city/rural/highway):

WLTP-based range calculation:
ECO Mode: 318,6 km (15,5 kWh/100km)
Normal Mode: 303,3 km (16,3 kWh/100km)
Sport Mode: 252,8 km (19,6 kWh/100km)

Consumption-based range calculation:
ECO Mode: 0,0 km (15,5 kWh/100km)
Normal Mode: 0,0 km (16,3 kWh/100km)
Sport Mode: 0,0 km (19,6 kWh/100km)

Would you like to add a charging profile? (yes/no): y

Enter charging curve points (empty line to finish):
Format: <battery_percentage> <charging_power>
Example: 20 150  (means 150kW at 20% battery)

Enter point (or empty line to finish): 10 90
Enter point (or empty line to finish): 50 60
Enter point (or empty line to finish): 80 40
Enter point (or empty line to finish): 90 20
Enter point (or empty line to finish): 95 10
Enter point (or empty line to finish): 99 3
Enter point (or empty line to finish):

Charging Curve Overview:
Battery %  | Charging Power (kW)
----------|-----------------
    10,0% |     90,0 kW
    50,0% |     60,0 kW
    80,0% |     40,0 kW
    90,0% |     20,0 kW
    95,0% |     10,0 kW
    99,0% |      3,0 kW

Car profile created and selected successfully!

Press Enter to continue...

Car Profile Management
Currently selected profile: Sir Charge-A-Lot
1. View all car profiles
2. Create new car profile
3. Delete car profile
4. Edit car profile
5. Back to main menu

Enter your choice (1-5): 5

=== ULRICA - Main Menu ===
Currently selected profile: Sir Charge-A-Lot
1. Car Profile Management
2. Actions Menu
3. Exit

Enter your choice: 2

=== Action Menu ===
Selected Profile: Sir Charge-A-Lot

Available Actions:
1. DC (Fast) Charging Calculator
2. AC (Slow) Charging Calculator
3. Calculate Range with Current Conditions
4. Plan a route with charging stops
5. Back to Main Menu

Select an action: 1

=== DC (Fast) Charging Calculator ===
Enter starting State of Charge (%) [0-100]: 10
Enter target State of Charge (%) [0-100]: 80
Enter maximum charging station power (kW): 100
Enter ambient temperature (°C): 20

=== Charging Time Estimate ===
Charging Time: 35m
Battery Capacity: 44.0 kWh
Energy to be added: 30,8 kWh
Battery Chemistry: NMC
Temperature: 20,0°C → 25,9°C
Power Reduction: 30% (for battery longevity)

Note: Battery level is low.

Press Enter to continue...

=== Action Menu ===
Selected Profile: Sir Charge-A-Lot

Available Actions:
1. DC (Fast) Charging Calculator
2. AC (Slow) Charging Calculator
3. Calculate Range with Current Conditions
4. Plan a route with charging stops
5. Back to Main Menu

Select an action: 2

=== AC (Slow) Charging Calculator ===
Available AC connectors:
1. Household Socket (2.3 kW)
2. Camping Socket (3.7 kW)
3. Wallbox (11 kW)

Select connector type (1-3): 2
Enter current State of Charge (%) [0-100]: 10
Enter target State of Charge (%) [0-100]: 80
Enter ambient temperature (°C): 20

=== Charging Time Estimate ===
Charging time: 8 hours 46 minutes
Energy required: 30,2 kWh
Effective charging power: 3,4 kW
Efficiency loss: 7,0%
Temperature efficiency: 100,0%

Press Enter to continue...

=== Action Menu ===
Selected Profile: Sir Charge-A-Lot

Available Actions:
1. DC (Fast) Charging Calculator
2. AC (Slow) Charging Calculator
3. Calculate Range with Current Conditions
4. Plan a route with charging stops
5. Back to Main Menu

Select an action: 3

=== Terrain Conditions ===
1. Flat
2. Hilly
3. Mountainous

Select terrain type (1-3): 2

=== Weather Conditions ===
1. Sunny
2. Cloudy
3. Rain
4. Snow
5. Strong Wind

Select weather condition (1-5): 4
Enter temperature in Celsius: -2

=== Driving Environment ===
1. City
2. Rural
3. Highway

Select driving environment (1-3): 3
Enter current state of charge (0-100%): 80

=== Efficiency Mode ===
1. ECO
2. NORMAL
3. SPORT

Select efficiency mode (1-3): 2

=== Range Calculation Results ===
Estimated range: 178,5 km
Average consumption: 27,8 kWh/100km

Input:
- Terrain: HILLY
- Weather: SNOW
- Temperature: -2.0°C
- Environment: HIGHWAY
- Average Speed: 110.0 km/h
- Battery SoC: 80.0%
- Battery Temperature: -2.0°C
- Efficiency Mode: NORMAL

Impact due to Conditions:
- Weather impact: Severe impact - Snow conditions significantly reduce range, Cold temperature reduces battery efficiency
- Terrain impact: Moderate impact - Hills affect energy consumption
- Environment impact: Constant high speed at 110.0 km/h - Increased air resistance
- Battery condition: Medium SoC (80.0%) - Good operating range, Battery temperature cold (-2.0°C) - Reduced efficiency

Press Enter to continue...

=== Action Menu ===
Selected Profile: Sir Charge-A-Lot

Available Actions:
1. DC (Fast) Charging Calculator
2. AC (Slow) Charging Calculator
3. Calculate Range with Current Conditions
4. Plan a route with charging stops
5. Back to Main Menu
