# Start with a Maven base image to include Maven and JDK
FROM maven:3.8.8-openjdk-17-slim AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a slim OpenJDK image for running the application
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/journalApp.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
