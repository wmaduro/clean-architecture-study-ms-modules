
[//]: # 
[eps-I]: <https://github.com/wmaduro/clean-architecture-study/tree/master-sync>
[eps-II]: <https://github.com/wmaduro/clean-architecture-study/tree/master-eventbus>
[eps-III]: <https://github.com/wmaduro/clean-architecture-study-ms-modules/tree/master>

# Clean Architecture Study Series

## Episodes
- Episode I: [Decoupling the Problem Aiming Testability][eps-I]
- Episode II: [Increasing Decoupling (Event Queue)][eps-II]
- Episode III: [Increasing Decoupling, Testability and Scalability (SpringBoot and Docker)][eps-III]
- **Episode IV: High Scalability (Spring Cloud MicroServices) - (WE ARE HERE...)**



# Episode IV: High Scalability (SpringBoot Cloud MicroServices)

In this episode, we will implement the infrastructure to enable our business services to be deployed in an IaaS.
The final project will reuse all core business code available in Episode III.

If you are not familiar with the problem's scope, please, get back to the [Episode I][eps-I] .

**Project Overview**

![alt text](https://raw.githubusercontent.com/wmaduro/clean-architecture-study-ms-modules/master-episodeIV/md-files/overview-iaas.png)

### INFRASTRUCTURE SERVICES
- ### Spring Cloud
    - ### cas-infra-api-gateway
        Implementation of Spring Cloud API gateway.
    - ### cas-infra-config-server
        Implementation of Spring Cloud Configuration Service.
    - ### cas-infra-discovery-server
        Implementation of Spring Cloud Eureka Service.
- ### Logstash        
    - ### Logstatsh / Elasticsearch / Kibana
        Services responsible for the log's management.                      
    
### BUSINESS SERVICES
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

### Tests
In this episode, we keep the same level of tests implemented in the Episode III.


## How to run

### Requirement(s)
- **GIT**
- **JDK 14**
- **DOCKER-COMPOSE**

**IMPORTANT NOTE:** You will need to have at least 5.5Gb of free RAM memory to run all the services of this tutorial. **So, to avoid out of memory crash, do not start the whole infrastructure if you do not fullfill this requirement.** 

### Step by Step (LINUX)
- ### Download from GIT
     ```sh
    git clone -b master-episodeIV https://github.com/wmaduro/clean-architecture-study-ms-modules.git
    ```
- ### Jump into the project folder: 
    ```sh
    cd clean-architecture-study-ms-modules/cas-compile-all/
    ```    

- ### Compile All Services
    - Change permission:
        ```sh
        chmod +x mvnw
        ```    
    - Compile using built-in maven  (notice that all unit tests will be triggered): 
        ```sh
        ./mvnw clean package
        ```
- ### Run Integrated Tests
    ```sh
    ./mvnw verify
    ```
- ### Run Infrastructure Services (Spring Cloud)
    ```sh
    sudo docker-compose -f docker-compose-infra.yml up -d --build
    ``` 
    At this point, you **must wait for all infrastructure services starts.** For that, please, wait all services be available at http://localhost:8761. This url must open the Eureka Client Web Client.
        
    ![alt text](https://raw.githubusercontent.com/wmaduro/clean-architecture-study-ms-modules/master-episodeIV/md-files/eureka-infra-services.png)

- ### Run Business Services
    ```sh
    sudo docker-compose -f docker-compose-services.yml up -d --build
    ``` 
    Again, you must wait for business services fully starts. For that, check if the services are visible at http://localhost:8761.
        
    ![alt text](https://raw.githubusercontent.com/wmaduro/clean-architecture-study-ms-modules/master-episodeIV/md-files/eureka-all-services.png)

- ### Test Endpoints (Api Gateway)
    - ### Post File (Orchestration)
        ```sh
        curl --location --request POST 'http://<SERVER_IP>:8080/orchestration' --form 'file=@<PATH_OF_THE_FILE_all-in.csv>'
        ```  
        You must replace:
       * **<SERVER_IP>** - If your deploy was done in a different machine, please use its IP. Othewise, use localhost.
        * **<PATH_OF_THE_FILE_all-in.csv>** - The "all-in.csv" file will be located in the "sample-files" folder that is inside the project.
    
        Ex: curl --location --request POST 'http://localhost:8080/orchestration' --form 'file=@/home/maduro/clean-architecture-study-ms-modules/sample-files/all-in.csv'

    - ### Get outcome (Result)
        ```sh
         curl --location --request GET 'http://<SERVE                      R_IP>:8080/result/<PROCESS_ID>'
        ```  
        You must replace:
        * **<SERVER_IP>** - If your deploy was done in a different machine, please use its IP. Othewise, use localhost.
        * **<PROCESS_ID>** - Replace the "PROCESS_ID" by the "id" returned in the step 7 response.

        Ex: curl --location --request GET 'http://**localhost**:8080/result/**2**'

- ### Configure Logstash/Elasticsearch/Kibana (Infrastructure Services)
    After the complete startup of all services, we must configure Kibana to access the services's logs. The "index pattern" for all services is **caslog**.

## How to stop and remove images/containers
    - ### If you wish to stop and remove all installed containers:
        ```sh
        sudo docker-compose -f docker-compose-services.yml down
        sudo docker-compose -f docker-compose-infra.yml down
        ```
    
     - ### If you also wish to remove the images:
        ```sh
       sudo docker images | grep -E "wmaduro/cas" | awk '{print $3}' | xargs -I {} sudo docker rmi {}
        ```
        


