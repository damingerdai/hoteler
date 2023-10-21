FROM maven:3.9.5-amazoncorretto-21-debian AS back-build

WORKDIR app
COPY pom.xml /app
RUN mvn clean install -Dmaven.test.skip=true -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true
COPY src /app/src
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-slim
WORKDIR /app
COPY --from=back-build /app/target/*.jar /app/app.jar
ENV TZ=Aisa/Shanghai
RUN ln -snf /usr/shar/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
HEALTHCHECK CMD curl --fail http://localhost:8443/ping || exit 1
EXPOSE 8443
CMD ["sh", "-c", "exec java -jar app.jar --spring.profiles.active=docker"]