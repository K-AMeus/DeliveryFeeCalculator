# Food Delivery Fee Calculator API

## Overview
This project is a Java-based application designed for the Fujitsu Java Programming Trial Task 2024. It implements a service to calculate delivery fees for a food delivery application. The calculation considers various factors like the base fee depending on the city and vehicle type, and extra fees based on current weather conditions.

## Features
- **Database Integration**: Utilizes H2 database to store weather and delivery fee calculation data.
- **Scheduled Weather Data Import**: Implements a CronJob to fetch weather data from the Estonian Environment Agency, ensuring up-to-date information for fee calculation.
- **Dynamic Fee Calculation**: Calculates delivery fees based on city, vehicle type, and current weather conditions, adhering to predefined business rules.
- **RESTful API**: Provides a REST interface for requesting delivery fees, with support for CRUD operations on base and extra fee rules.

## Technologies Used
- Java 21
- Spring Boot
- H2 Database

## Getting Started

### Prerequisites
- JDK 21 or above
- Gradle

### Running the Application
1. **Clone the Project Repository**: git clone YOUR_REPOSITORY_URL
   
   Replace `YOUR_REPOSITORY_URL` with the actual URL of the project's Git repository.

3. **Navigate to the Project Directory**:
   cd delivery-fee-calculator

4. **Build the Project**:
   ./gradlew build

5. **Run the Application**:
   ./gradlew bootRun

## REST API Documentation
Explore the RESTful API endpoints and their functionalities in detail by visiting the Swagger UI documentation available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) when the application is running.

## Accessing the Database Console
To view and manage the application's database via the H2 Console:
- Navigate to [http://localhost:8080/h2-console](http://localhost:8080/h2-console) in your web browser while the application is running.
- Use the JDBC URL, username, and password as configured in your `application.properties` file to log in.





   
