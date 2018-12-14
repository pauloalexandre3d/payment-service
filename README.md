PaymentServiceApplicationTests# Microservice to process payments

## Overview

The application here presented is a simple REST microservice that performs operations on top of a Mongo database.
The REST service (create and get under /payment) provides an interface for the user create and find a payment with attributes of Client, Buyer, Payment and Credit Card.

## Technologies

For this I built a Java project using Maven and Spring Boot framework. The application is deployed in an embedded Tomcat and access a Mongo database embeded through Spring Data.
The application was designed using Clean Architecture (https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html).
List of the technologies, frameworks and tools used:
- Java 8
- Mongo Embedded Database
- Spring Boot
- Spring Data
- Spring Test (for integration tests)
- Spring Actuator (for checking if the app was up and running during development)
- Mockito (for unit tests)
- Swagger (who provides a nice interface for running manually the posts requests http://localhost:8080/swagger-ui.html)
- Jacoco (for test coverage)
- Lombok

## Tests

The application has unit and integration tests. Both are under the "/src/test" directory.
The class PaymentServiceApplicationTests.java contains the integration tests and runs Spring Tests for that.
All the other classes are unit tests and uses Mockito for mocking objects.
Jacoco is configured for tests coverage (configuration can be found in pom.xml) and checks for 100% coverage. You can find the reports on directory "/target/site".

For running the tests and jacoco:
> mvn clean verify

## Running the application

Make sure you do not have any server running on port 8080, that is where Tomcat will make available our application. To run, go to root folder and type on your terminal:

> mvn spring-boot:run

Please access the http://localhost:8080/swagger-ui.html for running manual tests.

You can use the Postman collection, available in postman folder.

## Running the application with Docker

You can running the application with Docker.
So, you need build the image:

> docker build -t payment-service .

And you can run the container like this:

>docker run -p 8080:8080 payment-service:latest

### API

Make a POST request to http://localhost:8080/payment with the following input Json example:

```sh
{
	"client": {
		"id": "123456789"
	},
	"buyer": {
		"name": "Paulo Machado",
		"email": "paulomachado@project.com",
		"cpf": "46434925034"
	},
	"amount": 100,
	"type": "CREDIT_CARD",
	"creditCard": {
		"holderName": "Paulo Machado",
		"number": "5131778903563981",
		"expirationDate": "2022-09",
		"cvv": "123"
	}
}
```

Make a GET operation to http://localhost:8080/payment/{paymentId}/status to get a response like this:
*This paymentId is returned in the before operation.

```sh
{
    "id": {
        "timestamp": 1544552205,
        "machineIdentifier": 7191117,
        "processIdentifier": -22833,
        "counter": 13395261,
        "time": 1544552205000,
        "date": "2018-12-11T18:16:45.000+0000",
        "timeSecond": 1544552205
    },
    "amount": 100,
    "type": "CREDIT_CARD",
    "client": {
        "id": "123456789"
    },
    "buyer": {
        "name": "Paulo Machado",
        "email": "paulomachado@project.com",
        "cpf": "46434925034"
    },
    "creditCard": {
        "holderName": "Paulo Machado",
        "number": "5131778903563981",
        "expirationDate": "2022-09",
        "cvv": "123"
    },
    "status": "APPROVED"
}
```

## Considerations

- Some requirements validations are applied using javax.validation.ConstraintValidator. 
- There is a special validator to CreditCard, it is called CreditCardNumberValidator. It is using "br.com.moip.credit-card-validator" artifact. 
- Using MongoDB embeded, the application is going to download while running application.