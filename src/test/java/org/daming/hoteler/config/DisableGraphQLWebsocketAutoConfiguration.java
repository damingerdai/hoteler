package org.daming.hoteler.config;

import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@TestPropertySource(properties = { "spring.autoconfigure.exclude=graphql.kickstart.spring.web.boot.GraphQLWebsocketAutoConfiguration" })
public @interface DisableGraphQLWebsocketAutoConfiguration {
}
