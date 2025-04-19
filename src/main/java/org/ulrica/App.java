package org.ulrica;

import controller.ApplicationController;
import exception.LoadingException;
import repository.CarProfileRepository;
import repository.JsonCarProfileRepository;
import service.SetupService;
import service.WelcomeService;

// // Convention of todo comments:
// TODO: DO MISTAKES THAT YOU CAN LATER IMPROVE, Robin!!!!
// TODO: EVERY PATTERN SHOULD BE APPLIED WITHIN THE APPLICATION MULTIPLE TIMES! (DON'T JUST DO IT ONCE AND THEN STOP; DO IT AGAIN AND AGAIN AND AGAIN)
// TODO: When ticking off coding pricniple and pattern, add an "==>" arrow and name where the principle and pattern is applied (e.g. class, interface, method, etc.)



// // FEATURES:
// ✅ CarProfileRepository for replacing JSON files -> Repository Pattern ==> CarProfileRepository interface, JsonCarProfileRepository and InMemoryCarProfileRepository implementations
// ✅ Alternative range calculation for efficiency modes -> Strategy Pattern ==> RangeCalculationStrategy interface with SimpleFactorRangeStrategy and ConsumptionBasedRangeStrategy implementations
// ✅ Battery types (LFP, NMC, etc.) -> Factory Method Pattern ==> BatteryProfile creation in CarProfileService.createBatteryProfile() method
// ✅ Create fake repository for testing -> Mocking ==> MockCarProfileRepository class with in-memory storage and operation logging for testing
// ✅ Create a generic range calculator at different charging states with different efficiency modes ==> RangeCalculationService with EnhancedRangeCalculationStrategy
// ✅ Create generic dc charging calculator for fast charging ==> ChargingCalculatorService with FastChargingStrategy and BatteryPreservingStrategy implementations
// ✅ Create generic ac charging calculator for slow charging ==> ACChargingCalculator with ACChargingCalculatorService and ACConnectorType implementations
// ❌ Create a route planner for a route on a ficticious map with ficticious charging stations and let it "charge and drive" between two points on the map (map should be one-dimensional for simplicity)
// TODO (OPTIONAL): CarRace: Let two profiles race&charge against each other in a theoretical race; Use the route planner to create a route for the race between two points on the map (map should be one-dimensional for simplicity)


// CODING PRINCIPLES AND PATTERNS and EXAMPLES:
// ✅ Domain Code ==> Clear separation of model (CarProfile, BatteryProfile, etc.), service, and repository layers
// ✅ Dependency Rule ==> High-level modules (services) don't depend on low-level modules (repositories), both depend on abstractions
// ✅ Single Responsibility Principle ==> Each repository implementation (JsonCarProfileRepository, InMemoryCarProfileRepository) has single responsibility of data access
// ✅ Open/Closed Principle ==> RangeCalculationStrategy allows extending range calculation without modifying CarProfile class
// ✅ Liskov Substitution Principle ==> All repository implementations can be used interchangeably through CarProfileRepository interface
// ✅ Interface Segregation Principle ==> CarProfileRepository interface has cohesive methods for profile management
// ✅ Dependency Inversion Principle ==> CarProfileService depends on CarProfileRepository interface, not concrete implementations
// ✅ GRASP:
//     ✅ Information Expert ==> RangeCalculationService knows how to calculate range with all efficiency factors
//     ✅ Creator ==> RangeCalculationStrategyFactory creates appropriate range calculation strategies
//     ✅ Controller ==> ApplicationController manages the flow of the application
//     ✅ Polymorphism ==> Different implementations of RangeCalculationStrategy, ChargingStrategy, and EfficiencyFactor
//     ✅ Pure Fabrication ==> RangeCalculationStrategyFactory as a pure fabrication class
//     ✅ Indirection ==> Repository pattern provides indirection for data access
//     ✅ High Cohesion ==> Each service and strategy class has a single, well-defined responsibility
//     ✅ Low Coupling ==> Use of interfaces and dependency injection reduces coupling
// TODO: Don't Repeat Yours
//  elf
// TODO: Ubiquitous Language
// ✅ Repository Pattern ==> CarProfileRepository provides data access abstraction with JSON and InMemory implementations
// ✅ Strategy Pattern ==> RangeCalculationStrategy interface with different calculation implementations, ChargingStrategy interface with FastChargingStrategy and BatteryPreservingStrategy implementations
// ✅ Factory Method Pattern ==> Battery type creation in CarProfileService.createBatteryProfile()
// ✅ Template Method Pattern ==> Abstract charging calculation flow in ChargingCalculatorService with customizable steps in strategies
// ✅ Enum Strategy Pattern ==> ACConnectorType enum with different charging power and efficiency configurations
// TODO: Aggregates
// TODO: Entities
// TODO: Value Objects
// ✅ Zwei unterschiedliche Entwurfsmuster ==> Repository Pattern and Strategy Pattern implemented



// TODO: FOUND A BUG FOR REAL RANGE
//Creating consumption profile:
//Enter consumption values for Normal mode (baseline):
//Enter consumption at 50 km/h (kWh/100km): 15
//Enter consumption at 100 km/h (kWh/100km): 19
//Enter consumption at 130 km/h (kWh/100km): 22
//
//Calculated ranges and consumptions for mixed driving style (city/rural/highway):
//
//WLTP-based range calculation:
//ECO Mode: 318,6 km (17,8 kWh/100km)
//Normal Mode: 303,3 km (18,7 kWh/100km)
//Sport Mode: 252,8 km (22,4 kWh/100km)
//
//Consumption-based range calculation:
//ECO Mode: 0,0 km (17,8 kWh/100km)
//Normal Mode: 0,0 km (18,7 kWh/100km)
//Sport Mode: 0,0 km (22,4 kWh/100km)


// TESTING:
// TODO: 10 Unit Tests
// TODO: ATRIP: 
//     Autonomy
//     Thorough 
//     Professional
// TODO: Fakes and Mocks

// IDEAS FOR APPLICATION OF PATTERNS, Principles and Testing:
// TODO: Observer Pattern: CarRace: Let the two cars observe the route and charging stations and react to it; Use the Observer Pattern
// TODO: OCP: Add fourth efficiency mode "track mode" with a very "bad" efficiency factor
// TODO: OCP: Add Validation of input data
// TODO: SRP: Split CarProfileService into multiple services
// TODO: DIP: Move range calculation to a separate service
// TODO: DIP: Move battery profile creation to a separate factory
// TODO: DIP: Move car profile loading to a separate repository
// TODO: DIP: Move car profile saving to a separate repository
// TODO: DIP: Move car profile updating to a separate repository
// TODO: Charging Calculator is perfect for mocking and testing due to physical limitations of the car; E.g. when -10°C the car will charge faster than when 20°C; perfect for testing the charging profile


// CHANGES:
//TODO: introduce interfaces to classes
// TODO: Improve data validation 


// MISCELLANEOUS
// TODO: edit readme as it no longer conforms to the application workflow etc

public class App {
  public static void main(String[] args) throws LoadingException {
    SetupService setupService = new SetupService();
    WelcomeService welcomeService = new WelcomeService();

    setupService.initialize();
    welcomeService.showWelcome();
    
    CarProfileRepository repository = new JsonCarProfileRepository();
    ApplicationController appController = new ApplicationController(repository);
    
    while (appController.shouldContinue()) {
      appController.processCurrentState();
    }
  }
}
