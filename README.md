# ULRICA

→ ***U***niversa***L*** ***R***ange and dest***I***nation ***CA***lculator

### **Project Overview**
ULRICA is a range estimation and route planning tool designed to calculate the remaining range of electric vehicles, estimate charging times, and optimize route planning based on weather conditions and battery profiles. The application supports multiple car profiles, consumption models, and charging profiles to provide accurate estimations.

---

## **Plugins/Libraries**
- GSON
- JUnit

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
ULRICA follows a **layered architecture pattern** with **clean architecture principles**, emphasizing **separation of concerns** and **maintainability**.

### **Architectural Layers**
1. **Presentation Layer (Actions & Controllers)**
   - Actions: Encapsulated operations using the Command Pattern
   - Controllers: Orchestrate user interactions and flow control
   - Separation between actions and controllers ensures modularity

2. **Service Layer**
   - Contains business logic
   - Implements service interfaces for dependency inversion
   - Manages orchestration between components

3. **Repository Layer**
   - Abstracts data access
   - Supports multiple implementations (In-Memory, JSON storage)
   - Ensures thread-safe operations

4. **Domain Layer**
   - Defines core business entities
   - Implements value objects following **Domain-Driven Design (DDD)** principles

### **Key Design Decisions**
- Thread-safe collections for concurrency
- JSON-based persistence using GSON
- Interface-based programming
- Command Pattern for user actions
- Repository Pattern for data access abstraction

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
- **Route Planning**
  - Charging stop computation
  - Time estimation
  - Weather integration

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
- Charging stop optimization

---

## **Design Patterns & Principles**
### **Implemented Patterns**
1. **Command Pattern** (Encapsulated operations via Action interfaces)
2. **Repository Pattern** (Abstracted data access with multiple implementations)
3. **Strategy Pattern** (Modular range calculations, weather impact modeling)
4. **Factory Pattern** (Encapsulated object creation)

### **SOLID Principles Application**
1. **Single Responsibility** (Each class has a distinct purpose)
2. **Open/Closed** (System extensibility via interface-based design)
3. **Liskov Substitution** (Consistent interface adherence)
4. **Interface Segregation** (Focused, minimal dependencies)
5. **Dependency Inversion** (Abstractions over concrete implementations)

---

## **Package Structure**
### **Core Packages**
```
src/main/java/
├── actions/          # Encapsulated user commands
├── controller/       # Handles user interaction
├── service/         # Business logic orchestration
├── interfaces/      # Core abstractions
├── model/           # Domain entities
├── repository/      # Data persistence and storage
├── utils/           # Helper utilities
└── exception/       # Custom exception handling
```
### **Key Components**
- **Actions**: Individual operation implementations
- **Controllers**: Handles user flow and requests
- **Services**: Implements business logic
- **Repositories**: Manages data persistence
- **Models**: Defines domain structures

---

## **Development Guidelines**
### **Code Style**
- Follow **Conventional Commits**: [Link](https://www.conventionalcommits.org/en/v1.0.0/)
- Use **Gitmoji** for meaningful commit messages: [Link](https://gitmoji.dev/)
- Maintain consistent formatting
- Write clear and concise documentation

### **Testing Strategy**
- Unit tests for core components
- Integration tests for complete workflows
- Mocking of external dependencies

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