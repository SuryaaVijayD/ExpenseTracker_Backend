# Stage 1: Build
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
