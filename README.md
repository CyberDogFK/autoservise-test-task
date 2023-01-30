## Auto-service API

## Description of project

This api present work with Autoservice, for controling cusotmer orders, cars, and you masters

## Endpoints

- :dart: Work with article

|    Endpoint     | Method |               Description               |
|:---------------:|:------:|:---------------------------------------:|
|   `/article`    |  POST  | Create and return crated article object |
| `/article/{id}` |  PUT   |     Change specific article with id     |

- Work with car

|  Endpoint   | Method |          Description          |
|:-----------:|:------:|:-----------------------------:|
|   `/car`    |  POST  | Create and return created car |
| `/car/{id}` |  PUT   |  Change specific car with id  |

- :dart: Work with master

|       Endpoint        | Method |                                                  Description                                                   |
|:---------------------:|:------:|:--------------------------------------------------------------------------------------------------------------:|
|       `/master`       |  POST  |                                        Create and return crated master                                         |
|    `/master/{id}`     |  PUT   |                                         Change master with specific id                                         |
| `/master/{id}/orders` |  GET   |                                   Return array of orders for specific master                                   |
| `master/{id}/salary`  |  GET   | Calculate payment for all completed, non paid services, return number and change all not paid statuses to paid |

- :dart: Work with order

|       Endpoint        | Method |           Description           |
|:---------------------:|:------:|:-------------------------------:|
|       `/order`        |  POST  | Create and return created order |
| `/order/{id}/article` |  POST  |    Add new articles to order    |
|       `/order`        |  PUT   |  Change specific order with id  |
| `/order/{id}/status`  |  PUT   | Change status of specific order |
|  `/order/{id}/price`  |  GET   |   Return price for all order    |

- :dart: Work with owner

|       Endpoint       | Method |           Description           |
|:--------------------:|:------:|:-------------------------------:|
|       `/owner`       |  POST  | Create and return craeted owner |
|    `/owner/{id}`     |  PUT   |      Change specific owner      |
| `/owner/{id}/orders` |  GET   | Return orders of specific owner |
|  `/owner/{id}/cars`  |  GET   |  Return cars of specific owner  |

- :dart: Work with service

|    Endpoint     | Method |            Description            |
|:---------------:|:------:|:---------------------------------:|
|   `/service`    |  POST  | Create and return craeted service |
| `/service/{id}` |  PUT   |  Change specifc service with id   |
| `/service/{id}` |  PUT   |  Change specific service status   |


## Technologies

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Web
- Spring Boot Dev Tools
- JUnit 4
- Mockito
- SpringDoc
- Swagger
- Lombok
- Postgres
- Docker
- Liquibase

## Project Structure

| Tier       | Description                                                          |
|------------|----------------------------------------------------------------------|
| DAO        | Tier for work only with repositories, for opportunity easy change DB |
| Service    | All logic of computing and work with application data here           |
| Controller | User can use this application throw that tier                        |


## How to start

If you have docker, in terminal:
1. Download repo to you computer
2. Use
````
./mvnw clean package
````
3. Use, and wait booting
````
docker-compose up
````

If not:
1. Download repo to you computer
2. Change
   src/main/resources/application.properties
   , change values to you database, i create this application for work with MySQl, so recommend use the same
3. Use
```
./mvnw spring-boot:run
```` 
in command line, from directory.
4. GoTo localhost:6868 and have fun
