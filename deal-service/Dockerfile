FROM maven:3.8.4-openjdk-17 AS build

COPY . /app

WORKDIR /app

RUN mvn clean package

FROM openjdk:17

COPY --from=build /app/target/deal-service-1.0-SNAPSHOT.jar /usr/local/deal-service-1.0-SNAPSHOT.jar

WORKDIR /usr/local

EXPOSE 8085

CMD ["java", "-jar", "deal-service-1.0-SNAPSHOT.jar"]