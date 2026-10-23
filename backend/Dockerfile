# 1. Build stage using Maven
FROM --platform=$BUILDPLATFORM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# Force copy the dataset directly into the root execution space where your Java app searches
COPY data/ ./data/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
