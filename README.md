# ULRICA

→ ***U***niversa***L*** ***R***ange and dest***I***nation ***CA***lculator

### **Project Overview**
ULRICA is a range estimation and route planning tool designed to calculate the remaining range of electric vehicles, estimate charging times, and optimize route planning based on weather conditions and battery profiles. The application supports multiple car profiles, consumption models, and charging profiles to provide accurate estimations.

---

## **Table of Contents**
1. [Setup Instructions](#setup-instructions)
2. [Architecture Overview](#architecture-overview)
3. [Features](#features)
4. [Application Flow](#application-flow)
5. [Design Patterns & Principles](#design-patterns--principles)
6. [Package Structure](#package-structure)
7. [Development Guidelines](#development-guidelines)
8. [Grading & Presentation](#grading--presentation)

---

## **Setup Instructions**

### **Prerequisites**
- Java 11 or higher
- Maven
- Git

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

### **Architectural Layers**
1. **Domain Layer (Core)**
   - Contains business entities, value objects, and aggregates
   - Defines repository interfaces and domain services
   - Implements strategy patterns for core calculations
   - Completely independent of external frameworks and technologies

2. **Application Layer**
   - Implements use cases through interactors
   - Defines input and output ports for communication between layers
   - Orchestrates domain objects to fulfill business requirements
   - Depends only on the domain layer

3. **Infrastructure Layer**
   - Provides concrete implementations of repositories
   - Contains adapters for external services
   - Implements technical concerns (persistence, logging, etc.)
   - Depends on domain and application layers

4. **Presentation Layer**
   - Handles user interaction through controllers and actions
   - Transforms use case outputs into appropriate UI formats
   - Contains no business logic
   - Depends on application layer through input ports

### **Key Design Decisions**
- Dependency rule: inner layers don't know about outer layers
- Use of ports and adapters for flexible integration
- Clear boundaries between layers using interfaces
- Domain-driven design principles throughout

---

## **Features**
### **Core Functionality**
- **Car Profile Management**
  - CRUD operations
  - JSON-based persistence
  - Data validation rules
- **Range Calculation**
  - Weather impact consideration
  - Battery degradation modeling
  - Consumption profiles integration
- **Charging Calculation**
   - CarProfile based
   - Temperature dependend 
   - SoC and Goal-SoC dependend
- --**Route Planning**--
  - --Charging stop computation--
  - --Time estimation--
  - --Weather integration--

### **Technical Features**
- Thread-safe operations
- Extensible plugin architecture
- Custom JSON serialization
- Robust error handling
- Modular and maintainable design

---

## **Application Flow**
### **1. Application Initialization**
- Component initialization
- Configuration loading
- User welcome sequence

### **2. Car Profile Management**
- Profile loading from JSON
- Profile creation workflow
- Validation and persistence

### **3. Calculation Workflows**
- Range computation
- Route planning
- Weather data integration

---

## **Design Patterns & Principles**
### **Implemented Patterns**
1. **Strategy Pattern** (Range calculation, charging behaviors)
2. **Repository Pattern** (Data access abstraction)
3. **Factory Method Pattern** (Object creation with specific types)
4. **Command Pattern** (Encapsulated actions for user operations)
5. **Observer Pattern** (Event notifications for route changes)
6. **Aggregate Pattern** (Entity clustering with clear boundaries)

### **SOLID Principles Application**
1. **Single Responsibility** (Each class has a distinct purpose)
2. **Open/Closed** (System extensibility via interface-based design)
3. **Liskov Substitution** (Consistent interface adherence)
4. **Interface Segregation** (Focused, minimal interfaces)
5. **Dependency Inversion** (Abstractions over concrete implementations)

---

## **Package Structure**
### **Clean Architecture Structure**
```
src/main/java/org/ulrica/
├── App.java
│
├── domain/
│   ├── entity/
│   ├── valueobject/
│   ├── aggregate/
│   ├── repository/
│   ├── strategy/
│   │   └── factory/
│   ├── service/
│   └── exception/
│
├── application/
│   ├── port/
│   │   ├── in/
│   │   └── out/
│   └── usecase/
│
├── infrastructure/
│   ├── persistence/
│   ├── observer/
│   └── util/
│
└── presentation/
    ├── controller/
    └── action/
```

---

## **Development Guidelines**
### **Code Style**
- Follow **Conventional Commits**: [Link](https://www.conventionalcommits.org/en/v1.0.0/)
- Use **Gitmoji** for meaningful commit messages: [Link](https://gitmoji.dev/)
- Maintain consistent formatting
- Write clear and concise documentation

### **Clean Architecture Rules**
1. **Dependency Rule**: Source code dependencies must only point inward
2. **Layer Independence**: Inner layers must not know about outer layers
3. **Interface Ownership**: Interfaces are owned by the layer that uses them, not implements them
4. **Use Case Focus**: Application layer is organized around use cases, not technical concerns

### **Testing Strategy**
- Domain layer should be 100% unit testable without mocks
- Use ports and adapters for easy component substitution in tests
- Create test doubles for infrastructure dependencies
- Test use cases with mock repositories

---

## **Grading & Presentation**
### **Code Requirements**
- Minimum **2000+ lines** of code
- At least **20+ Java classes**
- Comprehensive test coverage
- SOLID and design pattern implementation

### **Presentation Elements**
- Use case demonstrations
- UML diagrams
- Code examples
- Architecture explanation
- Justification of tech stack

### **Testing**
Count lines of code:
```bash
find . -name "*.java" | xargs wc -l
```

---

## **Dependencies**
- **GSON**: JSON serialization/deserialization
- **JUnit**: Unit testing framework