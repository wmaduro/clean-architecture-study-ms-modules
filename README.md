
# Clean Architecture Study Series

The same content described at https://github.com/wmaduro/clean-architecture-study/tree/master-sync.

## Episodes
- Episode I: Service/DTO Model (Synchronicity) - https://github.com/wmaduro/clean-architecture-study/tree/master-sync
- Episode II: Service/DTO Model (EventBus) - https://github.com/wmaduro/clean-architecture-study/tree/master-eventbus
- **Episode III: Microservices - (WE ARE HERE...)**  

## The Problem 

The same content described at https://github.com/wmaduro/clean-architecture-study/tree/master-sync.

# Episode II: Service/DTO Model (EventBus) 

The main idea is to breakdown the problem above in small units (Services). Each unit produces its output object (DTO) and post it as an event in the EventBus. 

### Project Overview

![alt text](https://raw.githubusercontent.com/wmaduro/clean-architecture-study-ms-modules/master/md-files/overview.svg)

### Decoupled Units

The main problem was separated into smaller "units" to establish concise and clear boundaries.  

**Folder Unit (FolderMonitorService)**
- Responsibility: Watch the content of a folder and process the csv files when available.
- Produce: List of files to be processed.

**File Unit (FileParserService)**
- Responsibility: Process the file content and parse the lines to a list of hand's objects.
- Consume: Outcome of "Folder Unit".
- Produce: List of hand's objects.

**All the other units are the same already described at https://github.com/wmaduro/clean-architecture-study/tree/master-sync.**

### Tests

The principle here is: "Each test must be atomic". So, It must have run in a completely independent environment (when necessary, creating its resources).

- **Unit Tests**

    The unit tests aimed to cover each "service" scenario and all of its exceptions.

- **Integrated Tests** (Not implemented yet*)

    The integrated test is responsible for:
    
    - Create the simulation's input file.
    - Process all services in the right order.
    - Evaluate the outcome.

## How to run

### Requirement(s)
- **GIT**
- **JDK 8+**

### Step by Step (LINUX)
1. Clone the repository: **git clone -b master-eventbus https://github.com/wmaduro/clean-architecture-study.git**
2. Jump into the project folder: **cd clean-architecture-study**
3. Change permission: **chmod +x mvnw**
4. Compile using built-in maven  (notice that all unit tests will be triggered): **./mvnw clean compile package**
5. Run: **java -cp 'target/lib/*:target/clean-architecture-study-0.0.1-SNAPSHOT-jar-with-dependencies.jar' com.maduro.poker.ClenArchitectureStudyApplication 
   ./samples-files**

If you want, you can change the parameter (line 5) and set your own folder. After that, create the "csv" files in it.


Optionally, you can import the project in Eclipse 4+ with maven plugins installed. 

## Backlog

1. Implement the integrated test.



