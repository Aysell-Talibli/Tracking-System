
FROM gradle:8.4-jdk17 AS builder

WORKDIR /app

COPY . .

RUN gradle clean build -x test


FROM eclipse-temurin:17-jdk-alpine AS runtime

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
