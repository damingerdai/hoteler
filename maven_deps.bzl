# 加载 rules_jvm_external 提供的 maven 扩展定义
load("@rules_jvm_external//:extensions.bzl", "maven_install")

# 定义 Maven Artifacts 列表
Jsonwebtoken_Version = "0.12.5"

MAVEN_ARTIFACTS = [
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

# 核心：定义一个模块扩展，用于管理 Maven 依赖
# 这个扩展将会在 MODULE.bazel 中被 'use_extension' 引用
rules_jvm_external_deps = module_extension(
    implementation = lambda ctx: maven_install(
        artifacts = MAVEN_ARTIFACTS,
        fetch_sources = True,
        maven_install_json = "//:maven_install.json",
        repositories = [
            "https://maven.aliyun.com/repository/central",
            "https://maven.aliyun.com/repository/public",
            "https://repo1.maven.org/maven2",
            "http://uk.maven.org/maven2",
            "https://jcenter.bintray.com/",
            "https://maven.google.com",
        ],
    ),
)