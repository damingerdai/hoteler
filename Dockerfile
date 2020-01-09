FROM maven:3.6.3-jdk-11-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

WORKDIR usr/src/app
RUN mvn clean install package -Dmaven.test.skip=true

FROM openjdk:11.0.5-slim
WORKDIR /app
COPY --from=build /usr/src/app/target/*.jar /app/app.jar
ENV TZ=Aisa/Shanghai
RUN ln -snf /usr/shar/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 8763
CMD ["sh", "-c", "exec java -jar app.jar --spring.profiles.active=${profile}"]