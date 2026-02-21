# Use JDK 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built jar file
COPY target/Weather-Forecast-API-App-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8070

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]