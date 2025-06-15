FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

ADD target/*.jar app.jar

# Environment variables
ENV SERVER_PORT=8080
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql-container:3306/product_catalogue_service
ENV SPRING_DATASOURCE_USERNAME=product_root_user
ENV SPRING_DATASOURCE_PASSWORD=product@#123
ENV SPRING_PROFILES_ACTIVE=database
ENV SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI=http://localhost:9090


# Expose the application port
EXPOSE ${SERVER_PORT}

# Start the application
ENTRYPOINT ["java","-jar","app.jar"]
