# Author: Oleksii Hnidan | Project: web-security26 | File: Dockerfile | Lab: 1
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests package

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /workspace/target/web-security26-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080 5005
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
