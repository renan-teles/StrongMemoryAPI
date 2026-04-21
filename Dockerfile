FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/StrongMemoryAPI-2.0.2.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
