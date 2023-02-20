workspace(name = "hoteler")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "4.5"
RULES_JVM_EXTERNAL_SHA = "b17d7388feb9bfa7f2fa09031b32707df529f26c91ab9e5d909eb1676badd9a6"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    urls = [
        "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
        "https://ghproxy.com/https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
    ]
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "org.springframework.boot:spring-boot-starter-web:3.0.2",
        "org.springframework.boot:spring-boot-starter-jdbc:3.0.2",
        "org.springframework.boot:spring-boot-starter-aop:3.0.2",
        "org.springframework.boot:spring-boot-starter-security:3.0.2",
        "org.springframework.boot:spring-boot-starter-cache:3.0.2",
        "org.springframework.boot:spring-boot-starter-actuator:3.0.2",
        "org.springframework.boot:spring-boot-starter-data-redis:3.0.2",
        "org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1",

        "org.postgresql:postgresql:42.5.3",
        "org.springdoc:springdoc-openapi-ui:1.6.14",
        "io.jsonwebtoken:jjwt-api:0.11.5",
        "io.jsonwebtoken:jjwt-impl:0.11.5",
        "io.jsonwebtoken:jjwt-jackson:0.11.5",
        "commons-codec:commons-codec:1.15",
        "org.apache.commons:commons-lang3:3.12.0",
        "jakarta.xml.bind:jakarta.xml.bind-api:4.0.0",

        "org.glassfish.jaxb:jaxb-runtime:4.0.2",
        "javax.xml.bind:jaxb-api:2.4.0-b180830.0359",
        "com.github.ben-manes.caffeine:caffeine:3.1.2",
        "org.yaml:snakeyaml:1.33",
        "org.flywaydb:flyway-core:9.14.1",

        "org.springframework.boot:spring-boot-devtools:3.0.2",
        "org.springframework.boot:spring-boot-starter-test:3.0.2",
        "org.springframework.security:spring-security-test:6.0.0",

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