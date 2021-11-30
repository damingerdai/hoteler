FROM gradle:7.3.0-jdk17-alpine AS builder
COPY src /usr/src/app/src
COPY *.gradle  /usr/src/app
RUN gradle build -x test --project-dir  /usr/src/app

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=builder /usr/src/app/build/libs/*.jar /app/app.jar
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
HEALTHCHECK CMD curl --fail http://localhost:8443/ping || exit 1
EXPOSE 8443
CMD ["sh", "-c", "exec java -jar app.jar --spring.profiles.active=docker"]