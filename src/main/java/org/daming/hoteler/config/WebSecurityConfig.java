package org.daming.hoteler.config;

import org.daming.hoteler.api.filter.JWTLoginFilter;
import org.daming.hoteler.api.filter.AuthenticationFilter;
import org.daming.hoteler.config.service.ISecretPropService;
import org.daming.hoteler.security.provider.HotelerAuthenticationProvider;
import org.daming.hoteler.security.service.SecurityUserService;
import org.daming.hoteler.service.ITokenService;
import org.daming.hoteler.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

/**
 * WebSecurityConfig
 *
 * @author gming001
 * @create 2020-12-28 11:18
 **/
@Configuration
@EnableWebSecurity
//开启权限注解,默认是关闭的
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebSecurityConfig {

    private  String[] authWhiteList = {
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/**",
            "/csrf",

            // other
            "**.html",
            "/css/**",
            "/js/**",
            "/html/**",
            "/instances",
            "/favicon.ico"
    };

    @Autowired
    private ISecretPropService secretPropService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http, SecurityUserService securityUserService, PasswordEncoder passwordEncoder,
            ITokenService tokenService,  JWTLoginFilter jwtLoginFilter, IUserService userService) throws Exception {
        var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(securityUserService).passwordEncoder(passwordEncoder);
        var authenticationManager = authenticationManagerBuilder.build();
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> {
                    authorizeHttpRequests
                            .requestMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
                            .requestMatchers("/index/**").anonymous()// 匿名用户权限
                            .requestMatchers("/**.html").anonymous()// 匿名用户权限
                            .requestMatchers("/**.js").anonymous()// 匿名用户权限
                            .requestMatchers("/**.css").anonymous()// 匿名用户权限
                            .requestMatchers("/assets/**").anonymous()// 匿名用户权限
                            .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/icon/**", "/assets/**").anonymous()
                            .requestMatchers("/**.js", "/**.css", "/**.ico", "/**.woff2", "/**.svg").anonymous()
                            .requestMatchers("/api/v1/token").anonymous()//普通用户权限
                            .requestMatchers("/api/v1/job/jobinfos").hasRole("USERS")//普通用户权限
                            .requestMatchers(HttpMethod.POST, "/api/v1/token").anonymous()
                            .requestMatchers("/api/login").permitAll()
                            .requestMatchers("/api/**").hasRole("USERS")//普通用户权限
                            .requestMatchers(authWhiteList).permitAll()
                            //其他的需要授权后访问
                            .anyRequest().anonymous();
                });// 授权
        http.exceptionHandling(exceptionHandlingCustomizer -> {
                //TODO: add exception handing
        });
        http.addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new AuthenticationFilter(authenticationManager, tokenService, userService, this.secretPropService), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement((sessionManagementCustomizer) -> {
            sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        http.authenticationManager(authenticationManager);
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public JWTLoginFilter jwtLoginFilter(HttpSecurity http,  SecurityUserService securityUserService, PasswordEncoder passwordEncoder,  ITokenService tokenService) throws Exception {
        var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(securityUserService).passwordEncoder(passwordEncoder);
        var authenticationManager = authenticationManagerBuilder.build();
        var filter = new JWTLoginFilter("/api/login", authenticationManager, tokenService);
        filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HotelerAuthenticationProvider authenticationProvider) {;
        ProviderManager pm = new ProviderManager(authenticationProvider);

        return pm;
    }

    private AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, ex) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        };
    }
}
