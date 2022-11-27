package org.daming.hoteler.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.daming.hoteler.pojo.ApiError;
import org.daming.hoteler.pojo.SecurityUser;
import org.daming.hoteler.pojo.response.ErrorResponse;
import org.daming.hoteler.pojo.response.UserTokenResponse;
import org.daming.hoteler.service.ITokenService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

/**
 * 自定义登录过滤器
 * @author gming001
 * @version 2022-07-28 14:57
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private ITokenService tokenService;

    /**
     * 父类的构造方法
     * @param defaultFilterProcessesUrl 默认需要过滤的 url
     * @param authenticationManager 权限管理器
     */
    public JWTLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, ITokenService tokenService) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
        this.tokenService = tokenService;
    }

    /**
     * 自定义处理 登录认证，这里使用的json body 登录
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        var username = request.getHeader("username");
        var password = request.getHeader("password");
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    /**
     * 登录成功返回 token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth){
        var principal = (SecurityUser)auth.getPrincipal();
        var securityUser = new SecurityUser();
        securityUser.setUsername(principal.getUsername());
        securityUser.setAuthorities(new HashSet<>(principal.getAuthorities()));
        var token = this.tokenService.createToken(principal.getUsername(), principal.getPassword());
        try {
            //登录成功時，返回json格式进行提示
            this.render(request,response, new UserTokenResponse(token));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 失败返回
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String result="";
        // 账号过期
        if (failed instanceof AccountExpiredException) {
            result="账号过期";
        }
        // 密码错误
        else if (failed instanceof BadCredentialsException) {
            result="密码错误";
        }
        // 密码过期
        else if (failed instanceof CredentialsExpiredException) {
            result="密码过期";
        }
        // 账号不可用
        else if (failed instanceof DisabledException) {
            result="账号不可用";
        }
        //账号锁定
        else if (failed instanceof LockedException) {
            result="账号锁定";
        }
        // 用户不存在
        else if (failed instanceof InternalAuthenticationServiceException) {
            result="用户不存在";
        }
        // 其他错误
        else{
            result="未知异常";
        }
        this.render(request, response, new ErrorResponse(new ApiError("ERROR-600001", result)));
    }

    private  void render(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.getWriter().print(new ObjectMapper().writeValueAsString(object));
    }

}
