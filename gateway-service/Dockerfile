FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# This grabs the JAR built by Maven
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]