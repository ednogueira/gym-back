# D3X Gym Server - API RESTful Webservices

This project covers the back-end side of the D3X Gym system, an application developed to accomplish with the final paper of PUC Minas Graduate Program in Software Engineering.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Tech stack required for this development (built with):
- Java 11
- Maven
- Spring Boot Framework 2.3.0
- Spring Security – JWT
- MySQL 8 database connection
- Lombok
- Swagger


## Installing

In order to run the project into a development environment, follow the steps below:

### Run following SQL insert statements in order to be able for creating new user register and login into application:
```
INSERT INTO gym_perfil(nome) VALUES('ROLE_USER');
INSERT INTO gym_perfil(nome) VALUES('ROLE_RECEPCIONISTA');
INSERT INTO gym_perfil(nome) VALUES('ROLE_GERENTE');
```

### Run Spring Boot application, inserting on terminal the following command:
```
mvn spring-boot:run
```

### View and access to provided API RESTful services

The server should be running on http://localhost:8080 for development environment.

Find all available API RESTful services documentation on http://localhost:8080/swagger-ui.html#/


## Authors

* **Emerson D Nogueira** - *Initial work* - [ednogueira](https://github.com/ednogueira)