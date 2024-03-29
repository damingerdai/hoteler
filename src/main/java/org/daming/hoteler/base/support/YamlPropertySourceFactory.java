package org.daming.hoteler.base.support;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * YamlPropertySourceFactory
 *
 * @author gming001
 * @create 2020-12-13 15:54
 **/
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        var factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());
        factory.afterPropertiesSet();
        var ymlProperties = factory.getObject();
        // String propertyName = name != null ? name : resource.getResource().getFilename();
        var propertyName = Objects.requireNonNullElse(name, resource.getResource().getFilename());
        return new PropertiesPropertySource(propertyName, ymlProperties);
    }
}
