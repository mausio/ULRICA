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
// TODO: Alternative range calculation for efficiency modes -> Strategy Pattern
// TODO: Battery types (LFP, NMC, etc.) -> Factory Pattern
// TODO: Create fake repository for testing -> Mocking
// TODO: Create generic charging calculator -> DIP analysis
// TODO: Improve data validation -> SRP analysis


// CODING PRINCIPLES AND PATTERNS and EXAMPLES:
// TODO: Domain Code
// TODO: Dependency Rule
// ✅ Single Responsibility Principle ==> Each repository implementation (JsonCarProfileRepository, InMemoryCarProfileRepository) has single responsibility of data access
// TODO: Open/Closed Principle
// TODO: Liskov Substitution Principle
// TODO: Interface Segregation Principle
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
// TODO: Aggregates
// TODO: Entities
// TODO: Value Objects
// TODO: Zwei unterschiedliche Entwurfsmuster



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
    new SetupService();
    
    new WelcomeService();
    
    // Initialize the repository and service
    CarProfileRepository repository = new JsonCarProfileRepository();
    CarProfileService carProfileService = new CarProfileService(repository);
    
    // Start the car profile management menu
    carProfileService.showMenu();
  }
}
