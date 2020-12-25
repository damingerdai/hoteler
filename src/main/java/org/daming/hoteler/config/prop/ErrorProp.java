package org.daming.hoteler.config.prop;

import org.daming.hoteler.base.support.YamlPropertySourceFactory;
import org.daming.hoteler.pojo.ApiError;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.StringJoiner;

/**
 * error property
 *
 * @author gming001
 * @create 2020-12-13 16:09
 **/
@Configuration
@ConfigurationProperties()
@PropertySource(value = "classpath:errors.yml", factory = YamlPropertySourceFactory.class)
public class ErrorProp {

    private List<ApiError> errors;

    public List<ApiError> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiError> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ErrorProp.class.getSimpleName() + "[", "]")
                .add("errors=" + errors)
                .toString();
    }
}
