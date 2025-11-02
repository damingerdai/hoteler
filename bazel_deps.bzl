## bazel_deps.bzl

Jsonwebtoken_Version = "0.12.5"

def get_maven_artifacts():
    """Returns a list of all required Maven artifacts."""
    return [
        "org.springframework.boot:spring-boot-starter-web:3.5.5",
        "org.springframework.boot:spring-boot-starter-jdbc:3.5.5",
        "org.springframework.boot:spring-boot-starter-aop:3.5.5",
        "org.springframework.boot:spring-boot-starter-security:3.5.5",
        "org.springframework.boot:spring-boot-starter-cache:3.5.5",
        "org.springframework.boot:spring-boot-starter-actuator:3.5.5",
        "org.springframework.boot:spring-boot-starter-data-redis:3.5.5",
        "org.springframework.boot:spring-boot-starter-graphql:3.5.5",
        "org.springframework.boot:spring-boot-starter-quartz:3.5.5",
        "org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.5",
        "com.graphql-java:graphql-java-extended-scalars:2023-01-24T02-11-56-babda5f",
        "org.springframework.boot:spring-boot-loader:3.5.5",
        "org.springframework.boot:spring-boot-loader-tools:3.5.5",
        "io.micrometer:micrometer-registry-prometheus:1.15.4",
        "org.postgresql:postgresql:42.7.8",
        "org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13",
        "io.jsonwebtoken:jjwt-api:%s" % (Jsonwebtoken_Version),
        "io.jsonwebtoken:jjwt-impl:%s" % (Jsonwebtoken_Version),
        "io.jsonwebtoken:jjwt-jackson:%s" % (Jsonwebtoken_Version),
        "commons-codec:commons-codec:1.19.0",
        "org.apache.commons:commons-lang3:3.18.0",
        "jakarta.xml.bind:jakarta.xml.bind-api:4.0.4",
        "org.glassfish.jaxb:jaxb-runtime:4.0.6",
        "com.github.ben-manes.caffeine:caffeine:3.2.2",
        "org.yaml:snakeyaml:2.5",
        "org.flywaydb:flyway-core:11.13.2",
        "com.cronutils:cron-utils:9.2.1",
        "org.bouncycastle:bcpkix-jdk18on:1.81",
        "com.lmax:disruptor:4.0.0",
        "org.springframework.boot:spring-boot-devtools:3.5.5",
        "org.springframework.boot:spring-boot-starter-test:3.5.5",
        "org.springframework.security:spring-security-test:6.5.5",
    ]