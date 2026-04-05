FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/StrongMemoryAPI-1.0.10.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
