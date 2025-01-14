FROM maven:3-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine
COPY --from=build /target/SpringApp3_MyPlant-0.0.1-SNAPSHOT.jar SpringApp3_MyPlant.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "SpringApp3_MyPlant.jar" ]
