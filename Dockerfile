# Etapa de construção
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Etapa de execução
FROM openjdk:17-jdk
COPY --from=build /app/target/projeto-web-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]