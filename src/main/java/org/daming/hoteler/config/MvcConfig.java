package org.daming.hoteler.config;


import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    // https://github.com/DavidWhom/DavidWhom.github.io/blob/master/docs/SpringBoot%E9%9B%86%E6%88%90Vue%E6%89%93%E5%8C%85%E5%90%8E%E7%9A%84dist%E5%BA%94%E8%AF%A5%E5%A6%82%E4%BD%95%E9%85%8D%E7%BD%AE.md
    @Bean
    public WebServerFactoryCustomizer webServerFactoryCustomizer() {

        return (WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>) factory -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
            factory.addErrorPages(error404Page);
        };
    }

}
