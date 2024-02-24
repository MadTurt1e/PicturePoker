FROM maven:3.9.6-eclipse-temurin-21 AS build
ADD . /project
WORKDIR /project
RUN mvn -e package

FROM eclipse-temurin:latest
COPY --from=build /project/target/picturepoker-0.0.1-SNAPSHOT.jar /app/picturepoker.jar
ENTRYPOINT java -jar /app/picturepoker.jar