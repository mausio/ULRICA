#ULRICA 
-> UniversaL Range and destInation CAlculator

## Start-Up: How to run the application


## Architektur 

### Erste Idee
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
                         # Hauptklasse zum Ausführen des Programms
```