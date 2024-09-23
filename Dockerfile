FROM openjdk:17-jdk-alpine
COPY target/app.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
LABEL maintainer="author@javatodev.com"
VOLUME /main-app
EXPOSE 8080
