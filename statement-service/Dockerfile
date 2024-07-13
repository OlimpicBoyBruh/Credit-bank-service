FROM maven:3.8.4-openjdk-17 AS build

COPY . /app

WORKDIR /app

RUN mvn clean package

FROM openjdk:17

COPY --from=build /app/target/statement-service-1.0-SNAPSHOT.jar /usr/local/statement-service-1.0-SNAPSHOT.jar

WORKDIR /usr/local

EXPOSE 8205

CMD ["java", "-jar", "statement-service-1.0-SNAPSHOT.jar"]


