# Stage 1: Build the application
FROM maven:3.9.8-eclipse-temurin-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files into the container
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Package the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the .jar file from the build stage
COPY --from=build /app/target/Take-Note-0.0.1-SNAPSHOT.jar /app/Take-Note-0.0.1-SNAPSHOT.jar

# Expose the port your application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/Take-Note-0.0.1-SNAPSHOT.jar"]

