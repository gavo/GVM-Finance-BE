# Stage 1: Runtime
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the jar from the build context
COPY target/*.jar app.jar

# Expose port 8080 for Render
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]