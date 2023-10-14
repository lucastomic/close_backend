# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src


COPY ./entrypoint.sh ./entrypoint.sh 


ENTRYPOINT ["./entrypoint.sh"]


FROM base as build
RUN ./mvnw package

