package org.daming.hoteler.config.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author gming001
 * @version 2024-01-25 10:24
 */
// @Component
public class ConfigPropertyProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        ConfigurationProperties annotation = bean.getClass().getAnnotation(ConfigurationProperties.class);
        if (Objects.isNull(annotation)) {
            return bean;
        }
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            ReflectionUtils.makeAccessible(field);
            Object value = ReflectionUtils.getField(field, bean);
            if (value == null) {
                String propertyValue = resolvePropertyValue(annotation.prefix(), field.getName());
                ReflectionUtils.setField(field, bean, propertyValue);
                System.out.println(field);
                System.out.println(value);
                System.out.println(propertyValue);
                System.out.println("-------");
            }

        }
        return bean;
    }

    private String resolvePropertyValue(String prefix, String fieldName) {
        String propertyKey = prefix + "." + fieldName;
        String propertyValue = System.getenv(propertyKey);

        return propertyValue;
    }
}
