package org.daming.hoteler.config;

import org.daming.hoteler.api.filter.JWTLoginFilter;
import org.daming.hoteler.api.filter.SecurityAuthTokenFilter;
import org.daming.hoteler.config.service.ISecretPropService;
import org.daming.hoteler.security.service.SecurityUserService;
import org.daming.hoteler.service.ITokenService;
import org.daming.hoteler.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * WebSecurityConfig
 *
 * @author gming001
 * @create 2020-12-28 11:18
 **/
@Configuration
@EnableWebSecurity
//开启权限注解,默认是关闭的
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private ISecretPropService secretPropService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/**.js", "/**.css", "/**.ico", "/**.woff2", "/**.svg", "/**.html");
//    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 放行 swagger 相关路径
        String[] authWhiteList = {
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
        //对于在header里面增加token等类似情况，放行所有OPTIONS请求。
        return (web) -> web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .mvcMatchers(HttpMethod.POST, "/api/v1/user")
                // 可以直接访问的静态数据或接口
                .antMatchers(authWhiteList);
    }

    // @Bean
    @Deprecated
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers("/index.html","/static/**").permitAll()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/icon/**", "/assets/**").permitAll()
                .antMatchers("/**.js", "/**.css", "/**.ico", "/**.woff2", "/**.svg").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/configuration/**").permitAll()
                .antMatchers("/images").permitAll()
                .antMatchers("/api/v1/**").permitAll()
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/api/v1/token").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http
                //.addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http, SecurityUserService securityUserService, PasswordEncoder passwordEncoder,
            ITokenService tokenService, IUserService userService) throws Exception {
        var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(securityUserService).passwordEncoder(passwordEncoder);
        var authenticationManager = authenticationManagerBuilder.build();
        http
                .authorizeRequests()// 授权
                .mvcMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
                .antMatchers("/index/**").anonymous()// 匿名用户权限
                .antMatchers("/**/*.html").anonymous()// 匿名用户权限
                .antMatchers("/**/*.js").anonymous()// 匿名用户权限
                .antMatchers("/**/*.css").anonymous()// 匿名用户权限
                .antMatchers("/assets/**").anonymous()// 匿名用户权限
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/icon/**", "/assets/**").anonymous()
                .antMatchers("/**.js", "/**.css", "/**.ico", "/**.woff2", "/**.svg").anonymous()
                .antMatchers("/api/v1/token").anonymous()//普通用户权限
                .antMatchers("/api/**").hasRole("USERS")//普通用户权限
                .antMatchers("/api/login").permitAll()
                //其他的需要授权后访问
                .anyRequest().anonymous()
                .and()// 异常
                .exceptionHandling()
//                .accessDeniedHandler(accessDeny)//授权异常处理
//                .authenticationEntryPoint(anonymousAuthenticationEntryPoint)// 认证异常处理
                .and()
                .logout()
                //.logoutSuccessHandler(authenticationLogout)
                .and()
                .addFilterBefore(new JWTLoginFilter("/api/login", authenticationManager, tokenService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new SecurityAuthTokenFilter(authenticationManager, tokenService, userService, this.secretPropService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                // 设置Session的创建策略为：Spring Security不创建HttpSession
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationManager(authenticationManager)
                .csrf().disable();// 关闭 csrf

        return http.build();
    }

    private AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, ex) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        };
    }
}
