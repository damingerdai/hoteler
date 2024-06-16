package org.daming.hoteler.config;

import org.daming.hoteler.repository.interceptors.SqlInterceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gming001
 * @version 2024-06-16 10:01
 */
@Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return  configuration -> configuration.addInterceptor(new SqlInterceptor());
    }

}
