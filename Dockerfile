FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/*.jar /app/application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "application.jar" ]