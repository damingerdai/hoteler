FROM node:21.1.0 AS front-build

ENV SELF_SIGNED_CERT_IN_CHAIN=true
ENV NODE_TLS_REJECT_UNAUTHORIZED=0
RUN npm config set strict-ssl false
RUN yarn config set strict-ssl false

COPY src/main/angular/package.json src/main/angular/yarn.lock /tmp/
RUN cd /tmp && yarn --pure-lockfile
RUN mkdir -p /app && cp -a /tmp/node_modules /app/

WORKDIR /app
COPY src/main/angular /app
RUN yarn build

FROM maven:3.9.8-amazoncorretto-21-debian AS back-build

WORKDIR app
COPY pom.xml /app
RUN mvn clean install -Dmaven.test.skip=true -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true
COPY src /app/src
COPY --from=front-build /app/dist/hoteler /app/src/main/resources/static
COPY --from=front-build /app/dist/hoteler/index.html /app/src/main/resources/static/error/404.html
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-slim
WORKDIR /app
COPY --from=back-build /app/target/*.jar /app/app.jar
ENV TZ=Aisa/Shanghai
RUN ln -snf /usr/shar/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 8080
CMD ["sh", "-c", "exec java -jar app.jar --spring.profiles.active=docker"]
