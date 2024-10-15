# ULRICA

→ ***U***niversa***L*** ***R***ange and dest***I***nation ***CA***lculator

**ALWAYS** use https://gitmoji.dev/

## How to Start ULRICA

1. Clone this repository

- ```bash
  git clone https://github.com/mausio/ULRICA
     ```

2. Open your terminal of choice (used here: `iterm2`)
3. Navigate to the root directory of the project

- ```bash
  cd /path/to/ULRICA
     ```

4. Compile this project

- ```bash
  mvn clean compile 
     ```

5. Run ULRICA

- ```bash
  mvn exec:java -Dexec.mainClass="org.ulrica.App"
     ```

6. Optional: Create an executable JAR

- ```bash
  mvn clean package
     ```
- ```bash
  java -jar target/ULRICA-1.0-SNAPSHOT.jar
     ```
- Run the JAR

## Application Flow

### Idea

1. **Start the Application**

- Initializes the models, controllers, and services
- Welcomes the User and delays for a second before proceeding
- Directs the user to the first question from the `WelcomeController`

2. **CarProfile Setup**

- Loading of car profiles
- Loads car profiles from `carprofiles.json`
    - If there are any car profiles saved: Asks if the user would like to
      use one of those or create a new one.
        - If "yes": displays the cars available (`CarProfileList`).
        - If "create new car profile": goes through `createNewCarProfile()`.
    - If there are no car profiles saved: goes through `createNewCarProfile()`.
- New car profile
    - `NewCarProfileInputController` questions about name, manufacturer, model,
      build year, and heat pump.
    - Calls `NewConsumptionProfileInputController` and goes through
      the process of regression, adding the consumption profile to the car
      profile afterward.
    - Displays the new profile as JSON.
- Display car profile list
    - Lists the cars with their given (nick-)name and allows selection.
    - After selecting the car, the car will be fully loaded into the
      `CarProfileModel`.

3. **Range Calculator or Route Planner**

- Asks if the user would like to calculate the real range of the car
  with the given battery percentage or plan a route with charging stops.
- In either case, asks for the current (or expected) weather:
    - Temperature
    - Asks about the weather (list with suitable order):
        - Cloudy
        - Clear / Sunny
        - Rainy
        - Snowy

## Architecture

### First Idea

```
org.ulrica
├── api
│   ├── RangeCalculationApi.java        
│   └── ApiClient.java                   
├── model
│   ├── CarProfile.java                  
│   ├── WeatherData.java                 
│   └── Route.java                       
├── service
│   ├── RangeService                     
│   │   ├── RangeCalculator.java         
│   │   └── RangeValidation.java         
│   ├── ChargingService                  
│   │   ├── ChargingTimeCalculator.java  
│   │   └── ChargingValidation.java      
│   ├── WeatherService                   
│   │   ├── WeatherDataFetcher.java      
│   │   └── WeatherImpactCalculator.java  
│   └── RouteService                     
│       ├── RouteCalculator.java         
│       └── RouteValidation.java         
├── util
│   ├── JsonUtil.java                    
│   ├── RegressionUtil.java              
│   └── InputValidator.java              
├── controller
│   ├── InputController.java             
│   ├── OutputController.java            
│   └── UserInterface.java               
├── exceptions
│   └── LoadingException.java             
└── Main.java                            
```

### Packages

#### controller

The controller package handles user input/output and acts as an intermediary
between the view (UI) and the service layer. It manages the data flow and
interaction between the user and the backend logic.

#### model

The model package represents the core data models used across the application.
These classes hold the data and are passed between different layers (e.g.,
service, controller).

#### service

The service package is where the business logic resides. Each service handles a
specific domain (range calculation, charging, weather, and route management).
Each service is further divided into calculators and validations for better
modularity.

#### api

The api package is responsible for managing external communication, likely with
third-party services like weather APIs, car databases, or charging
infrastructure data.

#### util

The util (utility) package contains helper classes that provide reusable
functions across the application.

#### exception

The exception package handles custom exceptions that can be thrown throughout
the application, allowing for better error management and debugging.

## Presentation, Grading and Notes

### Grading

- 2000+ lines of code
    - Test with:
        -  ```bash
            find . -name "*.java" | xargs wc -l

- 20+ Java classes
- Testing
    - JUnit
    - JAssert
- Code principals
    - Single Responsibility
    - KISS
    - ... (add more later)

### Presentation

- 1-2 Use Cases
- 30 to 45 minutes of questions
- UML diagram
- code examples
- code principals demonstration
- questions will be released upfront
- justification for tech-stack
