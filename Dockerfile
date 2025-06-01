FROM openjdk:24-slim AS builder
WORKDIR /app

COPY gradle ./gradle
COPY settings.gradle build.gradle gradlew ./
RUN ./gradlew dependencies --no-daemon --parallel --configure-on-demand
COPY src ./src
RUN ./gradlew build \
    -x test \
    -x checkstyleMain \
    -x checkstyleTest \
    --no-daemon \
    --parallel \
    --configure-on-demand

FROM openjdk:24-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

HEALTHCHECK CMD curl --fail http://localhost:8443/ping || exit 1

EXPOSE 8443
CMD ["sh", "-c", "exec java -jar app.jar"]