package org.daming.hoteler.config.processor;

import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author gming001
 * @version 2024-01-25 10:14
 */
// @Component
public class ConfigPropertyEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        var ps = environment.getPropertySources();
        ps.forEach(System.out::println);
    }
}
