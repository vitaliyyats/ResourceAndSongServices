FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src
RUN ./mvnw install -DskipTests
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/SongService-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "SongService-0.0.1-SNAPSHOT.jar"]