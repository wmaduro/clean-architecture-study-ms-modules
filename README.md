


# Clean Architecture Study Series

## Episodes
- Episode I: Decoupling the Problem Aiming Testability - https://github.com/wmaduro/clean-architecture-study/tree/master-sync
- Episode II: Increasing Decoupling (Event Queue) - https://github.com/wmaduro/clean-architecture-study/tree/master-eventbus
- **Episode III: Increasing Decoupling, Testability and Scalability (SpringBoot and Docker) - (WE ARE HERE...)** 
- Episode IV: High Scalability (SpringBoot Cloud MicroServices) - https://github.com/wmaduro/clean-architecture-study-ms-modules/tree/master-episodeIV  


# Episode III: Increasing Decoupling, Testability and Scalability (SpringBoot and Docker)

Please, get back to the "Episode I" (https://github.com/wmaduro/clean-architecture-study/tree/master-sync) if you are not familiar with the problem's scope.

In this episode, we have implemented each service in separated SpringBoot applications.

The "cas-ms-orchestration" is the application responsible for orchestrating the right services calls.

All services are containerized using Docker-Compose to simplify the deployment process.

### Project Overview

![alt text](https://raw.githubusercontent.com/wmaduro/clean-architecture-study-ms-modules/master/md-files/overview.svg)

### Services 
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

### Scalability

If you would be interested in increase the scalability of the application's components by using DOCKER-COMPOSE, please consider a deep-dive study at articles like: 
[https://pspdfkit.com/blog/2018/how-to-use-docker-compose-to-run-multiple-instances-of-a-service-in-development/](https://pspdfkit.com/blog/2018/how-to-use-docker-compose-to-run-multiple-instances-of-a-service-in-development/).

I haven't tried this approach yet! But It seems to be promising.

### Tests
In this episode, it was also implemented **Integrated Tests** for all services.

Test libraries:
* Rest Assured
* Mock Server
* Apache FileSafe (Run integrated tests)


## How to run

### Requirement(s)
- **GIT**
- **JDK 14**
- **DOCKER-COMPOSE**


### Step by Step (LINUX)
1. Clone the repository: **git clone https://github.com/wmaduro/clean-architecture-study-ms-modules.git**
2. Jump into the project folder: **cd clean-architecture-study-ms-modules/cas-compile-all/**
3. Change permission: **chmod +x mvnw**
4. RUN THE INTEGRATED TESTS: **./mvnw verify**
5. Compile using built-in maven  (notice that all unit tests will be triggered): **./mvnw clean package**
6. Run: **sudo docker-compose up -d --build**
7. Call the endpoint to process the file:

        curl --location --request POST 'http://<SERVER_IP>:20003/orchestration' --form 'file=@<PATH_OF_THE_FILE_all-in.csv>'

    You must replace:
    * **<SERVER_IP>** - If your deploy was done in a different machine, please use its IP. Othewise, use localhost.
    * **<PATH_OF_THE_FILE_all-in.csv>** - The "all-in.csv" file will be located in the "sample-files" folder that is inside the project.
    
        Ex: curl --location --request POST 'http://**localhost**:20003/orchestration' --form 'file=@**/home/maduro/clean-architecture-study-ms-modules/sample-files/all-in.csv**'

8. Call the endpoint to show the evaluation result:

        curl --location --request GET 'http://<SERVER_IP>:20003/result/<PROCESS_ID>'

    You must replace:
    * **<SERVER_IP>** - If your deploy was done in a different machine, please use its IP. Othewise, use localhost.
    * **<PROCESS_ID>** - Replace the "PROCESS_ID" by the "id" returned in the step 7 response.

        Ex: curl --location --request GET 'http://**localhost**:20003/result/**2**'

Optionally, you can import the project in Eclipse 4+ with maven plugins installed. 

