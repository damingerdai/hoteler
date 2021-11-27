package org.daming.hoteler.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(info())
                .components(components())
                .externalDocs(externalDocumentation());
    }

    private ExternalDocumentation externalDocumentation() {
        return new ExternalDocumentation()
                .description("大明二代的Hoteler")
                .url("https://github.com/damingerdai/hoteler");
    }

    private Info info() {
        return new Info()
                .title("Hoteler Open API")
                .description("大明二代")
                .version("v0.0.1")
                .license(license());
    }

    private License license() {
        return new License()
                .name("MIT")
                .url("https://opensource.org/licenses/MIT");
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"));
    }
}
