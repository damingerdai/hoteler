package org.daming.hoteler.config.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

/**
 * @author gming001
 * @version 2024-01-25 10:14
 */
// @Component
public class ConfigPropertyEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println(ConfigPropertyEnvironmentPostProcessor.class);
        var ps = environment.getPropertySources();
        ps.forEach(System.out::println);
    }
}
