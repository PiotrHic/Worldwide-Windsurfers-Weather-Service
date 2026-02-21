# Używamy obrazu Maven + JDK 17 do builda
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Kopiujemy pliki Maven
COPY pom.xml .
COPY src ./src

# Budujemy aplikację
RUN mvn clean package -DskipTests

# Tworzymy finalny obraz z JDK
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Kopiujemy wygenerowany .jar z poprzedniego etapu
COPY --from=build /app/target/Weather-Forecast-API-App-0.0.1-SNAPSHOT.jar app.jar

# Uruchamiamy aplikację
ENTRYPOINT ["java", "-jar", "app.jar"]