workspace(name = "hoteler")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "5.2"
RULES_JVM_EXTERNAL_SHA ="f86fd42a809e1871ca0aabe89db0d440451219c3ce46c58da240c7dcdc00125f"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    urls = [
        "https://github.com/bazelbuild/rules_jvm_external/releases/download/%s/rules_jvm_external-%s.tar.gz" % (RULES_JVM_EXTERNAL_TAG, RULES_JVM_EXTERNAL_TAG),
        "https://mirror.ghproxy.com/https://github.com/bazelbuild/rules_jvm_external/releases/download/%s/rules_jvm_external-%s.tar.gz" % (RULES_JVM_EXTERNAL_TAG, RULES_JVM_EXTERNAL_TAG),
    ]
)

http_archive(
    name = "rules_spring",
    sha256 = "c99a54db3e86ee3a5ff2cdd545824ec1d3d41f72644ca48283218ce56190b3b1",
    urls = [
        "https://github.com/salesforce/rules_spring/releases/download/2.4.0/rules-spring-2.4.0.zip",
        "https://mirror.ghproxy.com/https://github.com/salesforce/rules_spring/releases/download/2.4.0/rules-spring-2.4.0.zip",
    ],
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("@rules_jvm_external//:defs.bzl", "maven_install")

Jsonwebtoken_Version = "0.12.5"

maven_install(
    artifacts = [
        "org.springframework.boot:spring-boot-starter-web:3.4.0",
        "org.springframework.boot:spring-boot-starter-jdbc:3.4.0",
        "org.springframework.boot:spring-boot-starter-aop:3.4.0",
        "org.springframework.boot:spring-boot-starter-security:3.4.0",
        "org.springframework.boot:spring-boot-starter-cache:3.4.0",
        "org.springframework.boot:spring-boot-starter-actuator:3.4.0",
        "org.springframework.boot:spring-boot-starter-data-redis:3.4.0",
        "org.springframework.boot:spring-boot-starter-graphql:3.4.0",
        "org.springframework.boot:spring-boot-starter-quartz:3.4.0",
        "org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3",
        "com.graphql-java:graphql-java-extended-scalars:2023-01-24T02-11-56-babda5f",
        #"org.springframework.boot:spring-boot:3.2.2",
        #"org.springframework.boot:spring-boot-starter:3.2.2",
        "org.springframework.boot:spring-boot-loader:3.4.0",
        "org.springframework.boot:spring-boot-loader-tools:3.4.0",

        "org.postgresql:postgresql:42.7.4",

        "org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0",
        "io.jsonwebtoken:jjwt-api:%s" % (Jsonwebtoken_Version),
        "io.jsonwebtoken:jjwt-impl:%s" % (Jsonwebtoken_Version),
        "io.jsonwebtoken:jjwt-jackson:%s" % (Jsonwebtoken_Version),
        "commons-codec:commons-codec:1.17.1",
        "org.apache.commons:commons-lang3:3.17.0",
        "jakarta.xml.bind:jakarta.xml.bind-api:4.0.2",

        "org.glassfish.jaxb:jaxb-runtime:4.0.5",
        "com.github.ben-manes.caffeine:caffeine:3.1.8",
        "cn.hutool:hutool-crypto:5.8.33",
        "org.yaml:snakeyaml:2.3",
        "org.flywaydb:flyway-core:10.21.0",
        "com.cronutils:cron-utils:9.2.1",
        "org.bouncycastle:bcpkix-jdk18on:1.79",
        "com.lmax:disruptor:4.0.0",

        "org.springframework.boot:spring-boot-devtools:3.4.0",
        "org.springframework.boot:spring-boot-starter-test:3.4.0",
        "org.springframework.security:spring-security-test:6.3.4",

    ],
    fetch_sources = True,
    repositories = [
        "https://maven.aliyun.com/repository/central",
        "https://maven.aliyun.com/repository/public",
        "https://repo1.maven.org/maven2",
        "http://uk.maven.org/maven2",
        "https://jcenter.bintray.com/",
        "https://maven.google.com",
    ],
    maven_install_json = "@//:maven_install.json",
)

load("@maven//:defs.bzl", "pinned_maven_install")
pinned_maven_install()