

# Clean Architecture Study Series

The same content described at https://github.com/wmaduro/clean-architecture-study/tree/master-sync.

## Episodes
- Episode I: Java SE - Service/DTO Model (Synchronicity) - https://github.com/wmaduro/clean-architecture-study/tree/master-sync
- Episode II: Java SE - Service/DTO Model (EventBus) - https://github.com/wmaduro/clean-architecture-study/tree/master-eventbus
- **Episode III: SpringBoot Services (RESTFUL / Blocking) - (WE ARE HERE...)** 
- Episode IV: SpringBoot Cloud MicroServices (RESTFUL / Non-Blocking) - (Coming Soon..)  

## The Problem 

The same content described at https://github.com/wmaduro/clean-architecture-study/tree/master-sync.

# Episode III: SpringBoot Services (RESTFUL - Blocking)

In this episode, we have implemented the services using SpringBoot. All communication among them use REST and blocking approach.
The "cas-ms-orchestration" project is the main user's endpoint provider.  It is responsible to orchestrate the right order for all related services.

### Project Overview

![alt text](https://raw.githubusercontent.com/wmaduro/clean-architecture-study-ms-modules/master/md-files/overview.svg)

### Services (Units)

>### cas-ms-storage
-  StorageService: Provide services to store and retrieve file contents.
      
>### cas-ms-orchestration
- OrchestrationService: Orchestrate the statistic calculation from file data. 
- ResultService: Retrive the evaluation outcome.

>### cas-ms-file-parser
-  FileParserService: Process the file content and parse the lines to a list of hand's objects.

>### cas-ms-hand-mapper
-  HandMapperService: Organize the hand's objects in blocks per hand code.

>### cas-ms-hand-evaluator
-  HandEvaluatorService:
    -   Identify the best cards in the hand.
    -   Evaluate if the best card had won the hand.
    -   Filter the content by player name and/or aggressivity behaviour (optional).


### Tests

...

- **Unit Tests**

...

- **Integrated Tests** (Not implemented yet*)
...

## How to run

### Requirement(s)
- **DOCKER**

### Step by Step 
1. ...


Optionally, you can import the project in Eclipse 4+ with maven plugins installed. 

## Backlog

1. ...



