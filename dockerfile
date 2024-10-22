# Stage 1: Build the application
FROM maven:3.8.8 AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests
RUN mvn clean install

# Stage 2: Run the application with a JRE
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar immigration-visa-service.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "immigration-visa-service.jar"]

