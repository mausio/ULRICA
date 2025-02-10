package org.ulrica;

import exception.LoadingException;
import service.CarProfileService;
import service.SetupService;
import service.WelcomeService;
import utils.generalUtils.ConsoleInteractorUtil;

// TODO: DO MISTAKES THAT YOU CAN LATER IMPROVE!!!


// FEATURES:
// TODO:  CarProfileRepository for replacing JSON files -> Repository Pattern
// TODO: Alternative range calculation for efficiency modes -> Strategy Pattern
// TODO: Battery types (LFP, NMC, etc.) -> Factory Pattern
// TODO: Create fake repository for testing -> Mocking
// TODO: Create generic charging calculator -> DIP analysis
// TODO: Improve data validation -> SRP analysis


// CODING PRINCIPLES AND PATTERNS and EXAMPLES:
// TODO: Domain Code
// TODO: Dependency Rule
// TODO: Single Responsibility Principle
// TODO: Open/Closed Principle
// TODO: Liskov Substitution Principle
// TODO: Interface Segregation Principle
// TODO: Dependency Inversion Principle
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
// TODO: Repository Pattern
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
    
    ConsoleInteractorUtil.stepper(
        "Step 1: Choose or create a car profile");
    new CarProfileService();

//    ConsoleInteractorUtil.stepper("Step 2: Stuff");
  }
}
