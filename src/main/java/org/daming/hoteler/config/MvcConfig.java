package org.daming.hoteler.config;


import org.daming.hoteler.pojo.enums.converter.EnumeratorConverterFactory;
import org.springframework.boot.web.error.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.server.servlet.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * MvcConfig
 *
 * @author gming001
 * @create 2021-01-08 16:32
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    // https://keepgrowing.in/java/springboot/make-spring-boot-surrender-routing-control-to-angular/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException, IOException {
                        Resource requestedResource = location.createRelative(resourcePath);

                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
                                : new ClassPathResource("/static/index.html");
                    }
                });
    }

    /**
     * 枚举类的转换器工厂 addConverterFactory
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new EnumeratorConverterFactory());
    }

    // https://github.com/DavidWhom/DavidWhom.github.io/blob/master/docs/SpringBoot%E9%9B%86%E6%88%90Vue%E6%89%93%E5%8C%85%E5%90%8E%E7%9A%84dist%E5%BA%94%E8%AF%A5%E5%A6%82%E4%BD%95%E9%85%8D%E7%BD%AE.md
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addErrorPages(new ErrorPage(HttpStatus.OK, "/index.html"));
    }

}
