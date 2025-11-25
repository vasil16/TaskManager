# --- STAGE 1: Build the JAR file ---
# Use a Maven image that includes the JDK for building the project
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src /app/src
# Run the Maven package command. -DskipTests is crucial for speed.
RUN mvn clean package -DskipTests

# --- STAGE 2: Create the final, smaller runtime image ---
# Use a JRE (Java Runtime Environment) image for the final, lightweight app
FROM eclipse-temurin:17-jre-alpine
# Copy the built JAR from the 'target' folder of the build stage
COPY --from=build /app/target/*.jar app.jar
# The application will run on this port
EXPOSE 8080
# The command to run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
COPY target/task-manager-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
