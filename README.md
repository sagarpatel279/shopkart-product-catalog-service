# Shopkart Product Catalog Service

A Spring Boot microservice for managing product catalogs in the Shopkart e-commerce platform.

## Features

- Product management (CRUD operations)
- Category management
- Integration with external product APIs (FakeStore API)
- MySQL database for persistent storage
- OAuth2 security

## Prerequisites

- Java 21
- Maven
- Docker and Docker Compose (for containerized deployment)

## Docker Setup

This project includes Docker configuration for easy deployment.

### Building and Running with Docker Compose

1. Clone the repository:
   ```
   git clone <repository-url>
   cd shopkart-product-catalog-service
   ```

2. Build and start the containers:
   ```
   docker-compose up -d
   ```

   This will:
   - Build the application using the Dockerfile
   - Start a MySQL database container
   - Start the application container
   - Set up networking between containers

3. Access the application:
   ```
   http://localhost:8080/resources
   ```

### Environment Variables

The following environment variables can be configured in the docker-compose.yml file:

| Variable | Description | Default Value |
|----------|-------------|---------------|
| SPRING_DATASOURCE_URL | JDBC URL for database connection | jdbc:mysql://mysql:3306/product_catalogue_service |
| SPRING_DATASOURCE_USERNAME | Database username | product_root_user |
| SPRING_DATASOURCE_PASSWORD | Database password | product@#123 |
| SPRING_PROFILES_ACTIVE | Active Spring profile | database |
| SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI | OAuth2 JWT issuer URI | http://auth-server:9090 |

## Running Locally (Without Docker)

1. Ensure MySQL is running and accessible with the credentials in application.properties
2. Build the application:
   ```
   mvn clean package
   ```
3. Run the application:
   ```
   java -jar target/*.jar
   ```

## API Endpoints

- Products: `/resources/products`
- Categories: `/resources/categories`
