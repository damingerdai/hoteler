FROM openjdk:24-slim AS builder

WORKDIR app
COPY .mvn ./.mvn
COPY pom.xml mvnw .
RUN  ./mvnw clean install -Dmaven.test.skip=true -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true -Dcheckstyle.skip=true
COPY src .
RUN ./mvnw package -Dmaven.test.skip=true -Dcheckstyle.skip=true

FROM openjdk:24-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
ENV TZ=Aisa/Shanghai
RUN ln -snf /usr/shar/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
HEALTHCHECK CMD curl --fail http://localhost:8443/ping || exit 1
EXPOSE 8443
CMD ["sh", "-c", "exec java -jar app.jar"]