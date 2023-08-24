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
        "https://ghproxy.com/https://github.com/bazelbuild/rules_jvm_external/releases/download/%s/rules_jvm_external-%s.tar.gz" % (RULES_JVM_EXTERNAL_TAG, RULES_JVM_EXTERNAL_TAG),
    ]
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "org.springframework.boot:spring-boot-starter-web:3.1.3",
        "org.springframework.boot:spring-boot-starter-jdbc:3.1.3",
        "org.springframework.boot:spring-boot-starter-aop:3.1.3",
        "org.springframework.boot:spring-boot-starter-security:3.1.3",
        "org.springframework.boot:spring-boot-starter-cache:3.1.3",
        "org.springframework.boot:spring-boot-starter-actuator:3.1.3",
        "org.springframework.boot:spring-boot-starter-data-redis:3.1.3",
        "org.springframework.boot:spring-boot-starter-graphql:3.1.3",
        "org.springframework.boot:spring-boot-starter-quartz:3.1.3",
        "org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.2",

        "org.postgresql:postgresql:42.6.0",
        "org.springdoc:springdoc-openapi-ui:1.7.0",
        "io.jsonwebtoken:jjwt-api:0.11.5",
        "io.jsonwebtoken:jjwt-impl:0.11.5",
        "io.jsonwebtoken:jjwt-jackson:0.11.5",
        "commons-codec:commons-codec:1.16.0",
        "org.apache.commons:commons-lang3:3.13.0",
        "jakarta.xml.bind:jakarta.xml.bind-api:4.0.0",

        "org.glassfish.jaxb:jaxb-runtime:4.0.3",
        "javax.xml.bind:jaxb-api:2.4.0-b180830.0359",
        "com.github.ben-manes.caffeine:caffeine:3.1.8",
        "cn.hutool:hutool-crypto:5.8.21",
        "org.yaml:snakeyaml:2.1",
        "org.flywaydb:flyway-core:9.21.1",
        "com.cronutils:cron-utils:9.2.1",

        "org.springframework.boot:spring-boot-devtools:3.1.3",
        "org.springframework.boot:spring-boot-starter-test:3.1.3",
        "org.springframework.security:spring-security-test:6.1.2",

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
)