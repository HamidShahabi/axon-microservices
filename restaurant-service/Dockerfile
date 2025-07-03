FROM openjdk:17-jdk-slim
COPY target/restaurant-service-1.0-SNAPSHOT.jar restaurant-service.jar
ENTRYPOINT ["java", "-jar", "/restaurant-service.jar", "--spring.profiles.active=docker"]