package org.ulrica;

import exception.LoadingException;
import repository.CarProfileRepository;
import repository.JsonCarProfileRepository;
import service.CarProfileService;
import service.SetupService;
import service.WelcomeService;

// TODO: DO MISTAKES THAT YOU CAN LATER IMPROVE!!!
// TODO: EVERY PATTERN SHOULD BE APPLIED WITHIN THE APPLICATION MULTIPLE TIMES! (DON'T JUST DO IT ONCE AND THEN STOP; DO IT AGAIN AND AGAIN AND AGAIN)
// TODO: When ticking off coding pricniple and pattern, add an "==>" arrow and name where the principle and pattern is applied (e.g. class, interface, method, etc.)


// FEATURES:
// ✅ CarProfileRepository for replacing JSON files -> Repository Pattern ==> CarProfileRepository interface, JsonCarProfileRepository and InMemoryCarProfileRepository implementations
// ✅ Alternative range calculation for efficiency modes -> Strategy Pattern ==> RangeCalculationStrategy interface with SimpleFactorRangeStrategy and ConsumptionBasedRangeStrategy implementations
// ✅ Battery types (LFP, NMC, etc.) -> Factory Method Pattern ==> BatteryProfile creation in CarProfileService.createBatteryProfile() method
// ✅ Create fake repository for testing -> Mocking ==> MockCarProfileRepository class with in-memory storage and operation logging for testing
// TODO: Create a generic range calculator at different charging states with different efficiency modes 
// TODO: Create generic charging calculator -> DIP analysis
// TODO: Improve data validation -> SRP analysis


// CODING PRINCIPLES AND PATTERNS and EXAMPLES:
// ✅ Domain Code ==> Clear separation of model (CarProfile, BatteryProfile, etc.), service, and repository layers
// ✅ Dependency Rule ==> High-level modules (services) don't depend on low-level modules (repositories), both depend on abstractions
// ✅ Single Responsibility Principle ==> Each repository implementation (JsonCarProfileRepository, InMemoryCarProfileRepository) has single responsibility of data access
// ✅ Open/Closed Principle ==> RangeCalculationStrategy allows extending range calculation without modifying CarProfile class
// ✅ Liskov Substitution Principle ==> All repository implementations can be used interchangeably through CarProfileRepository interface
// ✅ Interface Segregation Principle ==> CarProfileRepository interface has cohesive methods for profile management
// ✅ Dependency Inversion Principle ==> CarProfileService depends on CarProfileRepository interface, not concrete implementations
// TODO: GRASP: 
//     Information Expert
//     Creator
//     Controller
//     Polymorphism
//     Pure Fabrication
//     Indirection
//     High Cohesion
//     Low Coupling
// TODO: Don't Repeat Yourself
// TODO: Ubiquitous Language
// ✅ Repository Pattern ==> CarProfileRepository provides data access abstraction with JSON and InMemory implementations
// ✅ Strategy Pattern ==> RangeCalculationStrategy interface with different calculation implementations
// ✅ Factory Method Pattern ==> Battery type creation in CarProfileService.createBatteryProfile()
// TODO: Aggregates
// TODO: Entities
// TODO: Value Objects
// ✅ Zwei unterschiedliche Entwurfsmuster ==> Repository Pattern and Strategy Pattern implemented



// TESTING:
// TODO: 10 Unit Tests
// TODO: ATRIP: 
//     Autonomy
//     Thorough 
//     Professional
// TODO: Fakes and Mocks

// CHANGES:
//TODO: introduce interfaces to classes
//TODO: 

public class App {
  public static void main(String[] args) throws LoadingException {
    SetupService setupService = new SetupService();
    WelcomeService welcomeService = new WelcomeService();

    setupService.initialize();
    
    welcomeService.showWelcome();
    
    CarProfileRepository repository = new JsonCarProfileRepository();
    CarProfileService carProfileService = new CarProfileService(repository);
    
    carProfileService.showMenu();
  }
}
