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
    sha256 = "7bb891ccb2f53ca188a769b3a3777be1c38348e18091afea05320f3003b3e886",
    urls = [
        "https://github.com/salesforce/rules_spring/releases/download/2.3.1/rules-spring-2.3.1.zip",
        "https://mirror.ghproxy.com/https://github.com/salesforce/rules_spring/releases/download/2.3.1/rules-spring-2.3.1.zip",
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
        "org.springframework.boot:spring-boot-starter-web:3.2.2",
        "org.springframework.boot:spring-boot-starter-jdbc:3.2.2",
        "org.springframework.boot:spring-boot-starter-aop:3.2.2",
        "org.springframework.boot:spring-boot-starter-security:3.2.2",
        "org.springframework.boot:spring-boot-starter-cache:3.2.2",
        "org.springframework.boot:spring-boot-starter-actuator:3.2.2",
        "org.springframework.boot:spring-boot-starter-data-redis:3.2.2",
        "org.springframework.boot:spring-boot-starter-graphql:3.2.2",
        "org.springframework.boot:spring-boot-starter-quartz:3.2.2",
        "org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3",

        #"org.springframework.boot:spring-boot:3.2.2",
        #"org.springframework.boot:spring-boot-starter:3.2.2",
        "org.springframework.boot:spring-boot-loader:3.2.2",
        "org.springframework.boot:spring-boot-loader-tools:3.2.2",

        "org.postgresql:postgresql:42.7.1",

        "org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0",
        "io.jsonwebtoken:jjwt-api:%s" % (Jsonwebtoken_Version),
        "io.jsonwebtoken:jjwt-impl:%s" % (Jsonwebtoken_Version),
        "io.jsonwebtoken:jjwt-jackson:%s" % (Jsonwebtoken_Version),
        "commons-codec:commons-codec:1.16.1",
        "org.apache.commons:commons-lang3:3.14.0",
        "jakarta.xml.bind:jakarta.xml.bind-api:4.0.1",

        "org.glassfish.jaxb:jaxb-runtime:4.0.4",
        "com.github.ben-manes.caffeine:caffeine:3.1.8",
        "cn.hutool:hutool-crypto:5.8.24",
        "org.yaml:snakeyaml:2.2",
        "org.flywaydb:flyway-core:10.4.1",
        "com.cronutils:cron-utils:9.2.1",
        "org.bouncycastle:bcpkix-jdk18on:1.77",

        "org.springframework.boot:spring-boot-devtools:3.2.2",
        "org.springframework.boot:spring-boot-starter-test:3.2.2",
        "org.springframework.security:spring-security-test:6.2.1",

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
   maven_install_json = "//:maven_install.json",
)

load("@maven//:defs.bzl", "pinned_maven_install")  
pinned_maven_install()