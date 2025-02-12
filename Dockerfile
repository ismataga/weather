FROM openjdk:17-jdk-slim AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN ./mvnw dependency:resolve
COPY src src
RUN ./mvnw package

FROM openjdk:17-jdk-slim
WORKDIR weather
COPY --from=build /target/*.jar weather.jar
ENTRYPOINT ["java", "-jar", "weather.jar"]


#
#FROM openjdk:17-jdk-slim AS build
#WORKDIR /app
#
#COPY pom.xml mvnw ./
#COPY .mvn .mvn
#
#RUN chmod +x ./mvnw
#
#RUN ./mvnw dependency:resolve
#COPY src src
#
#RUN ./mvnw package
#
#FROM openjdk:17-jdk-slim
#
#WORKDIR /weather
#
#COPY --from=build /app/target/*.jar weather.jar
#
#ENTRYPOINT ["java", "-jar", "weather.jar"]
