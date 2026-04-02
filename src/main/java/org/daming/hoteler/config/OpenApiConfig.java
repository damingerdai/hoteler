package org.daming.hoteler.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
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
                // 让所有接口默认都带上“小锁”图标
                .addSecurityItem(new SecurityRequirement().addList("OAuth2Password").addList("bearer-key"))
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
                .addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                .addSecuritySchemes("OAuth2Password", new SecurityScheme()
                        .type(SecurityScheme.Type.OAUTH2)
                        .description("输入用户名和密码登录以获取令牌")
                        .flows(new OAuthFlows()
                                .password(new OAuthFlow()
                                        // 这里的 URL 必须对应你 Controller 里的 @PostMapping("token")
                                        .tokenUrl("/api/v1/token")
                                        .scopes(new Scopes().addString("read", "读权限"))
                                )
                        ));
    }
}
