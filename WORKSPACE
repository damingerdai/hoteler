workspace(name = "hoteler")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "4.1"
RULES_JVM_EXTERNAL_SHA = "f36441aa876c4f6427bfb2d1f2d723b48e9d930b62662bf723ddfb8fc80f0140"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    urls = [
        "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
        "https://ghproxy.com/https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
    ]
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "org.springframework.boot:spring-boot-starter-web:2.7.6",
        "org.springframework.boot:spring-boot-starter-jdbc:2.7.6",
        "org.springframework.boot:spring-boot-starter-aop:2.7.6",
        "org.springframework.boot:spring-boot-starter-security:2.7.6",
        "org.springframework.boot:spring-boot-starter-cache:2.7.6",
        "org.springframework.boot:spring-boot-starter-actuator:2.7.6",
        "org.springframework.boot:spring-boot-starter-data-redis:2.7.6",
        "org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2",

        "org.postgresql:postgresql:42.5.0",
        "org.springdoc:springdoc-openapi-ui:1.6.11",
        "io.jsonwebtoken:jjwt-api:0.11.5",
        "io.jsonwebtoken:jjwt-impl:0.11.5",
        "io.jsonwebtoken:jjwt-jackson:0.11.5",
        "commons-codec:commons-codec:1.15",
        "org.apache.commons:commons-lang3:3.12.0",
        "jakarta.xml.bind:jakarta.xml.bind-api:4.0.0",

        "org.glassfish.jaxb:jaxb-runtime:4.0.1",
        "javax.xml.bind:jaxb-api:2.4.0-b180830.0359",
        "com.github.ben-manes.caffeine:caffeine:2.9.3",
        "org.yaml:snakeyaml:1.31",
        "org.flywaydb:flyway-core:9.2.3",

        "org.springframework.boot:spring-boot-devtools:2.7.6",
        "org.springframework.boot:spring-boot-starter-test:2.7.6",
        "org.springframework.security:spring-security-test:5.7.3",

    ],
    fetch_sources = True,
    repositories = [
        "https://repo1.maven.org/maven2",
        "https://maven.aliyun.com/repository/public",
        "http://uk.maven.org/maven2",
        "https://jcenter.bintray.com/",
    ],
)