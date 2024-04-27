# Hi there!!! ZOO_API is welcoming you!

# ZOO API README

## Introduction

Welcome to ZOO API made using Spring Boot.
[See the example of requests to some endpoints here](https://www.loom.com/share/47391f9860d34ad3ad6b00537024395b?sid=db339fa9-0205-4bdb-a782-0bd864e4efbc)

## Technologies used

- **Spring Boot (v3.2.2):** A super-powerful framework for creating Java-based applications (just like this one).
- **Spring Security:** Ensures application security with features such as authentication and authorization.
- **JWT (JSON Web Token):** Ensures secure user authentication.
- **Spring Data JPA:** Simplifies the data access layer and interactions with the database.
- **Swagger (springdoc-openapi):** Eases understanding and interaction with endpoints for other developers.
- **MapStruct (v1.5.5.Final):** Simplifies the implementation of mappings between Java bean types.
- **Liquibase:** A powerful way to ensure database-independence for project and database schema changes and control.
- **Docker:** A powerful tool for letting other developers use this application.

## Project structure

This Spring Boot application follows the most common structure with such **main layers** as:
- repository (for working with database).
- service (for business logic implementation).
- controller (for accepting clients' requests and getting responses to them).

Also it has other **important layers** such as:
- mapper (for converting models for different purposes).
- security (for letting user authorize and be secured while interacting with application).
- exception (CustomGlobalExceptionHandler for getting proper messages about errors).
- dto (for managing sensitive info about models and better representation of it).
- config (config for mappers and OpenApi config).

## Key features

- **User authentication:** Secure user authentication using JWT for enhanced security.
- **User authorization:** Limited access to some endpoints of the application.
- **User login:** Login by email and password for generating JWT token.
- **API Documentation:** Using Swagger to generate clear and interactive API documentation.

## Setup Instructions

To set up and run the project locally, follow these steps:

1. Clone the repository.
2. Ensure you have Java 21 installed.
3. Ensure you have Maven installed.
4. Ensure you have Docker installed.
5. Create the database configuration in the `.env` file. (put your cliend.id and client-secret for Oauth2)
6. Build the project using Maven: `mvn clean package` (it will create required jar-archive).
7. Build the image using Docker: `docker-compose build`.
8. Run the application using Docker: `docker-compose up` (to test, send requests to port pointed in your .env file as SPRING_LOCAL_PORT).

## Roles explanation

- There are only 2 roles of users available: **USER and ADMIN roles**.
- User has access to such endpoints as searching zoo, getting an animal by id etc.
- But user doesn't get to upload a file for inserting zoo to a database. **(Remember it)**

## Users managing

- There is 1 user added to a database with help of liquibase.
- This user is already an admin.
- Credentials: email: admin@example.com, password: 1234567890.
- Using this admin credentials you can update roles of other users.
