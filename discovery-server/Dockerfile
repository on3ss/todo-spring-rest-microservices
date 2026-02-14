FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Note: Use a wildcard to handle version changes in pom.xml
COPY target/*.jar app.jar 
ENTRYPOINT ["java", "-jar", "app.jar"]