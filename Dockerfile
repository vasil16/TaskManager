# --- STAGE 1: BUILD STAGE ---
# Use a Maven image that includes the JDK for building the project
FROM maven:3.8.8-eclipse-temurin-17 AS build 
WORKDIR /app
# Copy the pom.xml file first to leverage Docker caching
COPY pom.xml .
# Copy all source code
COPY src /app/src 

# Build the project, generating the JAR in target/
# Use -DskipTests for faster deployment builds
RUN mvn clean package -DskipTests 

# --- STAGE 2: RUNTIME STAGE ---
# Use a JRE (Java Runtime Environment) image for the final, lightweight app
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# **THIS IS THE FIX:** Copy the JAR from the previous 'build' stage
# The name '*.jar' handles slight variations in the artifact name
COPY --from=build /app/target/*.jar app.jar 

# The command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]