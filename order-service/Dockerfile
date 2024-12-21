# Stage 1: Build the application
FROM maven:3.8.7-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy the entire project into the container (including parent POM and all submodules)
COPY . .

# Install the parent POM first
WORKDIR /app
RUN mvn clean install -N -DskipTests


WORKDIR /app/shared
RUN mvn clean install -DskipTests

# Build the order-service module
WORKDIR /app/order-service
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim
# Set working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/order-service/target/order-service-1.0-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]