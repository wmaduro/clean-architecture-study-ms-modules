[//]: # 
[eps-I]: <https://github.com/wmaduro/clean-architecture-study/tree/master-sync>
[eps-II]: <https://github.com/wmaduro/clean-architecture-study/tree/master-eventbus>
[eps-IV]: <https://github.com/wmaduro/clean-architecture-study-ms-modules/tree/master-episodeIV>

# Clean Architecture Study Series

## Episodes
- Episode I: [Decoupling the Problem Aiming Testability][eps-I]
- Episode II: [Increasing Decoupling (Event Queue)][eps-II]
- **Episode III: Increasing Decoupling, Testability and Scalability (SpringBoot and Docker) - (WE ARE HERE...)** 
- Episode IV: [High Scalability (Spring Cloud MicroServices)][eps-IV]


# Episode III: Increasing Decoupling, Testability and Scalability (SpringBoot and Docker)

If you are not familiar with the problem's scope, please, get back to the [Episode I][eps-I].

In this episode, we have implemented each service in separated SpringBoot applications.

The "cas-ms-orchestration" is the application responsible for orchestrating the right services calls.

All services are containerized using Docker-Compose to simplify the deployment process.

### Project Overview

![alt text](https://raw.githubusercontent.com/wmaduro/clean-architecture-study-ms-modules/master/md-files/overview.svg)

### SERVICES 
- ### cas-ms-storage
    - StorageService: Provide services to store and retrieve file contents.
      
- ### cas-ms-orchestration
    - OrchestrationService: Orchestrate the statistic calculation from file data. 
    - ResultService: Retrive the evaluation outcome.

- ### cas-ms-file-parser
    -  FileParserService: Process the file content and parse the lines to a list of hand's objects.

- ### cas-ms-hand-mapper
    -  HandMapperService: Organize the hand's objects in blocks per hand code.

- ### cas-ms-hand-evaluator
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
- Clone the repository: 
    ```sh
    git clone -b master https://github.com/wmaduro/clean-architecture-study-ms-modules.git
    ```
- Jump into the project folder: 
    ```sh
    cd clean-architecture-study-ms-modules/cas-compile-all/
    ```
- Change permission: 
    ```sh
    chmod +x mvnw
    ``` 
- RUN THE INTEGRATED TESTS: 
    ```sh
    ./mvnw verify
    ``` 
- Compile using built-in maven  (notice that all unit tests will be triggered):
    ```sh
    ./mvnw clean package
    ``` 
- Run: 
    ```sh
    sudo docker-compose up -d --build
    ``` 

- Call the endpoint to process the file:
    ```sh
    curl --location --request POST 'http://<SERVER_IP>:20003/orchestration' --form 'file=@<PATH_OF_THE_FILE_all-in.csv>'
    ``` 
    You must replace:
    * **<SERVER_IP>** - If your deploy was done in a different machine, please use its IP. Othewise, use localhost.
    * **<PATH_OF_THE_FILE_all-in.csv>** - The "all-in.csv" file will be located in the "sample-files" folder that is inside the project.
    
        Ex: curl --location --request POST 'http://**localhost**:20003/orchestration' --form 'file=@**/home/maduro/clean-architecture-study-ms-modules/sample-files/all-in.csv**'

- Call the endpoint to show the evaluation result:
    ```sh
    curl --location --request GET 'http://<SERVER_IP>:20003/result/<PROCESS_ID>'
    ``` 
    You must replace:
    * **<SERVER_IP>** - If your deploy was done in a different machine, please use its IP. Othewise, use localhost.
    * **<PROCESS_ID>** - Replace the "PROCESS_ID" by the "id" returned in the step 7 response.

        Ex: curl --location --request GET 'http://**localhost**:20003/result/**2**'

- ### If you wish stop amd remove] all installed containers:
    ```sh
    sudo docker-compose -f docker-compose-services.yml down
    sudo docker-compose -f docker-compose-infra.yml down
    ```

 - ### If you also wish remove the images:
    ```sh
   sudo docker images | grep -E "wmaduro/cas" | awk '{print $3}' | xargs -I {} sudo docker rmi {}
    ```


Optionally, you can import the project in Eclipse 4+ with maven plugins installed. 

