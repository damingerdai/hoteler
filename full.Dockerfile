FROM node:26.13.1 AS front-build

WORKDIR /app
COPY src/main/angular/package.json src/main/angular/package-lock.json ./
RUN HUSKY=0 CYPRESS_INSTALL_BINARY=0 npm ci --legacy-peer-deps
COPY src/main/angular /app
RUN npm run build

FROM azul/zulu-openjdk:25.0.5.1-jdk AS back-build

WORKDIR /app
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B
COPY src ./src
COPY --from=front-build /app/dist/hoteler/browser /app/src/main/resources/static
COPY --from=front-build /app/dist/hoteler/browser/index.html /app/src/main/resources/static/error/404.html
RUN ./mvnw package -Dmaven.test.skip=true

FROM azul/zulu-openjdk:25.0.5.1-jdk
WORKDIR /app
COPY --from=back-build /app/target/*.jar /app/app.jar
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 8080
CMD ["sh", "-c", "exec java -jar app.jar --spring.profiles.active=docker"]
