FROM gradle:jdk11-alpine as build

WORKDIR /app

COPY . /app

RUN chmod +x gradlew

RUN ./gradlew build --exclude-task test --no-daemon


FROM openjdk:11-slim

WORKDIR /app

COPY --from=build /app/build/libs/retoCP2024-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]