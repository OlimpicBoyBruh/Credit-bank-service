FROM maven:3.8.4-openjdk-17 AS build

COPY . /app

WORKDIR /app

RUN mvn clean package



FROM openjdk:17

COPY --from=build /app/target/gateway-service-1.0-SNAPSHOT.jar /usr/local/gateway-service-1.0-SNAPSHOT.jar

WORKDIR /usr/local

EXPOSE 9090

CMD ["java", "-jar", "gateway-service-1.0-SNAPSHOT.jar"]