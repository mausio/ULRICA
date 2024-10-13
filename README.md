# ULRICA 
→ ***U***niversa***L*** ***R***ange and dest***I***nation ***CA***lculator

## How to start-up ULRICA

1. Clone this repository
- > git clone https://github.com/mausio/ULRICA
2. Open your Terminal of choice (used here: `iterm2`)
3. Navigate to the root directory of the project
- > cd /path/to/ULRICA
4. Compile this project
- > mvn clean compile 
5. Run ULRICA
- > mvn exec:java -Dexec.mainClass="org.ulrica.App"
6. Optional: Create and an executable JAR
- > mvn clean package
- > java -jar target/ULRICA-1.0-SNAPSHOT.jar
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
   - if there are any car profiles saved: Asks if the user would like to 
     use one of those or would like to create a new one
     - if "yes": display the cars available (`CarProfileList`)
     - if "create new car profile": go through `createNewCarProfile()`
   - if there are no car profiles saved: go through `createNewCarProfile()`
- New car profile
  - `NewCarProfileInputController` questions about name, manufacturer, model, 
    buildyear and heatpump 
  - Calls `NewConsumptionProfileInputController` and goes through 
    process of regression and adds consumption profile to car profile 
    afterwards
  - Displays the new profile as Json
- Display car profile list 
  - lists the cars with their given (nick-)name and allows a selection
  - after selecting the car, the car will be fully loaded into the 
    `CarProfileModel`
4. **Range Calculator or Route Planner**
- Ask...
  - if they would like to just calculate the real range of the car with
    the given battery percentage or
  - Ask if they would like to plan a route with charging stops 
- Ask either way for the current (or expected) weather
  - Temperature 
  - ask about the weather (list with suitable order)
    - cloudy
    - clear / sunny
    - cloudy 
    - rainy
    - snowy 
5. **Range Calculator**
-
6. **Route Planner** 
- 


## Architecture 

### First Idea
```
com.example.rangecalculator
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
└── ui
    └── CarProfileSetupDialog.java
└── Main.java                            
```

### Packages

#### controller 
The controller package handles user input/output and acts as an intermediary between the view (UI) and the service layer. It manages the data flow and interaction between the user and the backend logic.

#### model
The model package represents the core data models used across the application. These classes hold the data and are passed between different layers (e.g., service, controller).

#### service
The service package is where the business logic resides. Each service handles a specific domain (range calculation, charging, weather, and route management). Each service is further divided into calculators and validations for better modularity.

#### api
The api package is responsible for managing external communication, likely with third-party services like weather APIs, car databases, or charging infrastructure data.

#### util
The util (utility) package contains helper classes that provide reusable functions across the application.

